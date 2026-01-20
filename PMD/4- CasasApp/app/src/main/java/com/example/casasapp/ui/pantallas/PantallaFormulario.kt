package com.example.casasapp.ui.pantallas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.casasapp.ui.CasaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormulario(navController: NavController, viewModel: CasaViewModel, idCasaEditar: Int = 0) {
    // --- COLORES CORPORATIVOS ---
    val azulLogo = Color(0xFF2C82C9)
    val naranjaLogo = Color(0xFFFD8D3C)

    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var listaImagenes by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var precioText by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    // Estado del Switch (Por defecto false = Venta)
    var esAlquiler by remember { mutableStateOf(false) }

    // --- MAGIA AQUÍ: El color cambia según el switch ---
    val colorTema = if (esAlquiler) azulLogo else naranjaLogo

    val opcionesExtras = listOf("Piscina", "Garaje", "Jardín", "Wifi", "Aire Acond.", "Trastero")
    var extrasSeleccionados by remember { mutableStateOf(setOf<String>()) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(idCasaEditar) {
        if (idCasaEditar != 0) {
            val casa = viewModel.obtenerCasa(idCasaEditar)
            casa?.let {
                nombre = it.nombre
                descripcion = it.descripcion
                listaImagenes = it.imagenes.map { uri -> Uri.parse(uri) }
                precioText = it.precio.toString()
                direccion = it.direccion
                esAlquiler = it.esAlquiler
                extrasSeleccionados = it.extras.toSet()
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        listaImagenes = listaImagenes + uris
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (idCasaEditar == 0) "Publicar Inmueble" else "Editar Inmueble",
                        color = colorTema, // Título cambia de color
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF5F7F8)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // TARJETA DE TIPO Y PRECIO
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // SWITCH TIPO
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = if (esAlquiler) "ALQUILER" else "VENTA",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            color = colorTema // Texto del switch cambia
                        )
                        Switch(
                            checked = esAlquiler,
                            onCheckedChange = { esAlquiler = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = azulLogo,
                                checkedTrackColor = azulLogo.copy(alpha = 0.2f),
                                uncheckedThumbColor = naranjaLogo,
                                uncheckedTrackColor = naranjaLogo.copy(alpha = 0.2f)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = precioText,
                        onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) precioText = it },
                        label = { Text("Precio (€)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        // Colores dinámicos del TextField
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorTema,
                            focusedLabelColor = colorTema,
                            cursorColor = colorTema,
                            unfocusedBorderColor = colorTema.copy(alpha = 0.5f), // Borde suave del color tema
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TARJETA DE DATOS
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Función local para simplificar los colores de los TextFields
                    val textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorTema,
                        focusedLabelColor = colorTema,
                        cursorColor = colorTema,
                        unfocusedBorderColor = colorTema.copy(alpha = 0.5f)
                    )

                    OutlinedTextField(
                        value = nombre, onValueChange = { nombre = it },
                        label = { Text("Título del anuncio") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = direccion, onValueChange = { direccion = it },
                        label = { Text("Dirección") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = descripcion, onValueChange = { descripcion = it },
                        label = { Text("Descripción detallada") },
                        modifier = Modifier.fillMaxWidth(), minLines = 3,
                        colors = textFieldColors
                    )
                }
            }

            // CARACTERÍSTICAS
            Text(
                "Características:",
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                color = colorTema, // Título de sección cambia
                fontWeight = FontWeight.Bold
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(opcionesExtras) { extra ->
                    val isSelected = extrasSeleccionados.contains(extra)
                    FilterChip(
                        selected = isSelected,
                        onClick = { extrasSeleccionados = if (isSelected) extrasSeleccionados - extra else extrasSeleccionados + extra },
                        label = { Text(extra) },
                        leadingIcon = if (isSelected) { { Icon(Icons.Default.Check, null) } } else null,
                        // Colores del Chip dinámicos
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorTema.copy(alpha = 0.1f),
                            selectedLabelColor = colorTema,
                            selectedLeadingIconColor = colorTema
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // FOTOGRAFÍAS
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Fotografías",
                    modifier = Modifier.weight(1f),
                    color = colorTema,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { launcher.launch("image/*") }) {
                    Icon(Icons.Default.Add, null, tint = colorTema)
                    Spacer(Modifier.width(4.dp))
                    Text("Añadir", color = colorTema)
                }
            }

            LazyRow(modifier = Modifier.height(120.dp).padding(vertical = 8.dp)) {
                items(listaImagenes) { uri ->
                    Card(
                        modifier = Modifier.size(120.dp).padding(end = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        AsyncImage(
                            model = uri, contentDescription = null,
                            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // BOTÓN GUARDAR
            Button(
                onClick = {
                    val precioDouble = precioText.toDoubleOrNull()
                    if (nombre.isNotBlank() && precioDouble != null) {
                        viewModel.guardarCasa(
                            id = idCasaEditar,
                            nombre = nombre,
                            descripcion = descripcion,
                            imagenes = listaImagenes.map { it.toString() },
                            precio = precioDouble,
                            esAlquiler = esAlquiler,
                            direccion = direccion,
                            extras = extrasSeleccionados.toList()
                        )
                        navController.popBackStack()
                    } else {
                        isError = true
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorTema), // El botón cambia de fondo
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("GUARDAR ANUNCIO", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            if (isError) Text("Falta el título o el precio", color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
    }
}