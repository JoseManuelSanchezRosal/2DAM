package com.example.casasapp.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.casasapp.data.Casa
import com.example.casasapp.data.CasaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

// CLAVE: Heredamos de AndroidViewModel para tener acceso al Contexto (Application),
// necesario para crear la Base de Datos y copiar archivos.
class CasaViewModel(application: Application) : AndroidViewModel(application) {

    // Inicialización de la base de datos Room
    private val db = Room.databaseBuilder(
        application,
        CasaDatabase::class.java,
        "casas-db"
    ).fallbackToDestructiveMigration().build()

    private val dao = db.casaDao()

    // Estados reactivos
    private val _casas = MutableStateFlow<List<Casa>>(emptyList())
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _filtroAlquiler = MutableStateFlow(false)
    val filtroAlquiler = _filtroAlquiler.asStateFlow()

    // CLAVE: 'combine' une los 3 flujos (lista completa, texto búsqueda, filtro tipo).
    // Si cambia CUALQUIERA de ellos, este bloque se recalcula automáticamente.
    // Esto es lo que permite el filtrado en tiempo real.
    val casasFiltradas = combine(_casas, _searchText, _filtroAlquiler) { lista, texto, soloAlquiler ->
        lista.filter { casa ->
            val coincideTexto = if (texto.isBlank()) true else casa.nombre.contains(texto, ignoreCase = true)
            val coincideTipo = casa.esAlquiler == soloAlquiler
            coincideTexto && coincideTipo
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        // Al iniciar, nos suscribimos a la base de datos
        viewModelScope.launch {
            dao.obtenerTodas().collect { lista ->
                _casas.value = lista
            }
        }
    }

    fun onSearchTextChange(text: String) { _searchText.value = text }
    fun onFiltroAlquilerChange(esAlquiler: Boolean) { _filtroAlquiler.value = esAlquiler }

    // CLAVE: Copia de seguridad de imágenes.
    // Las URIs de la galería son temporales. Aquí copiamos el archivo físico a la carpeta
    // privada de la app (filesDir) para que la foto no se pierda nunca.
    private fun copiarImagenAInternalStore(uriString: String): String {
        if (uriString.startsWith("file://")) return uriString // Ya es nuestra
        return try {
            val contentResolver = getApplication<Application>().contentResolver
            val uri = Uri.parse(uriString)
            val nombreArchivo = "img_${UUID.randomUUID()}.jpg"
            val archivoDestino = File(getApplication<Application>().filesDir, nombreArchivo)
            contentResolver.openInputStream(uri)?.use { inputStream ->
                archivoDestino.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            archivoDestino.toURI().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun guardarCasa(
        id: Int = 0,
        nombre: String,
        descripcion: String,
        imagenes: List<String>,
        precio: Double,
        esAlquiler: Boolean,
        direccion: String,
        extras: List<String>
    ) {
        // CLAVE: Dispatchers.IO mueve el trabajo pesado (copiar fotos) a un hilo secundario.
        viewModelScope.launch(Dispatchers.IO) {
            val imagenesPermanentes = imagenes.map { uri ->
                copiarImagenAInternalStore(uri)
            }.filter { it.isNotEmpty() }

            val nuevaCasa = Casa(
                id = id,
                nombre = nombre,
                descripcion = descripcion,
                imagenes = imagenesPermanentes,
                precio = precio,
                esAlquiler = esAlquiler,
                direccion = direccion,
                extras = extras
            )
            dao.insertar(nuevaCasa)
        }
    }

    fun borrarCasa(casa: Casa) {
        viewModelScope.launch(Dispatchers.IO) {
            // También borramos los archivos físicos para no dejar basura en el móvil
            casa.imagenes.forEach { uriString ->
                try {
                    val uri = Uri.parse(uriString)
                    val file = File(uri.path ?: "")
                    if (file.exists()) file.delete()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            dao.borrar(casa)
        }
    }

    suspend fun obtenerCasa(id: Int): Casa? {
        return dao.obtenerPorId(id)
    }
}