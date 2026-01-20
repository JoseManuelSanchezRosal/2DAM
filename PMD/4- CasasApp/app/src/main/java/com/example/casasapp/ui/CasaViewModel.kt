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

class CasaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        CasaDatabase::class.java,
        "casas-db"
    ).fallbackToDestructiveMigration().build()

    private val dao = db.casaDao()

    private val _casas = MutableStateFlow<List<Casa>>(emptyList())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // Nuevo estado: true = Alquiler, false = Venta
    private val _filtroAlquiler = MutableStateFlow(false)
    val filtroAlquiler = _filtroAlquiler.asStateFlow()

    // Combinamos: Lista + Búsqueda + Filtro Tipo
    val casasFiltradas = combine(_casas, _searchText, _filtroAlquiler) { lista, texto, soloAlquiler ->
        lista.filter { casa ->
            val coincideTexto = if (texto.isBlank()) true else casa.nombre.contains(texto, ignoreCase = true)
            // Filtramos estrictamente: o es lo que pide el switch, o no lo mostramos
            val coincideTipo = casa.esAlquiler == soloAlquiler
            coincideTexto && coincideTipo
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        viewModelScope.launch {
            dao.obtenerTodas().collect { lista ->
                _casas.value = lista
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onFiltroAlquilerChange(esAlquiler: Boolean) {
        _filtroAlquiler.value = esAlquiler
    }

    private fun copiarImagenAInternalStore(uriString: String): String {
        if (uriString.startsWith("file://")) return uriString
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