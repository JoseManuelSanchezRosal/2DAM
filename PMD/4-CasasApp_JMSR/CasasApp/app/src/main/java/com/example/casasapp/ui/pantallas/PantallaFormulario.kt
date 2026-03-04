package com.example.casasapp.ui.pantallas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Add
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PantallaFormulario(navController: NavController, viewModel: CasaViewModel, idCasaEditar: Int = 0) {
    val azulLogo = Color(0xFF2C82C9)
    val naranjaLogo = Color(0xFFFD8D3C)

    // CLAVE: "Toque Maestro". Definimos una forma única (16dp) para todos los componentes.
    val shapeComponentes = RoundedCornerShape(16.dp)

    // CLAVE: Estados locales para guardar lo que escribe el usuario antes de enviarlo a la BD.
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var listaImagenes by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var precioText by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var esAlquiler by remember { mutableStateOf(false) }

    // El color de toda la pantalla cambia dinámicamente si es alquiler o venta
    val colorTema = if (esAlquiler) azulLogo else naranjaLogo

    val opcionesExtras = listOf(
        "Ascensor", "Garaje", "Trastero", "Calefacción", "Aire Acond.",
        "Terraza", "Balcón", "Piscina", "Jardín", "Amueblado",
        "Armarios Emp.", "Exterior", "Acceso PMR", "Admite Mascotas", "Gimnasio"
    )

    var extrasSeleccionados by remember { mutableStateOf(setOf<String>()) }
    var textoNuevoExtra by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    // CLAVE: Si venimos a EDITAR (id != 0), cargamos los datos de la casa existente.
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

    // CLAVE: Selector de múltiples imágenes de la galería del móvil.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        listaImagenes = listaImagenes + uris
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (idCasaEditar == 0) "Publicar Inmueble" else "Editar Inmueble",
                        color = colorTema, fontWeight = FontWeight.Bold
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

            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorTema,
                focusedLabelColor = colorTema,
                cursorColor = colorTema,
                unfocusedBorderColor = colorTema.copy(alpha = 0.5f)
            )

            // 1. TARJETA PRECIO Y TIPO
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = shapeComponentes
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = if (esAlquiler) "ALQUILER" else "VENTA",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            color = colorTema
                        )
                        // Switch para cambio de modo (Alquiler/Venta)
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
                        onValueChange = { if (it.all { char -> char.isDigit() || char == '.' || char == ',' }) precioText = it },
                        label = { Text("Precio (€)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors,
                        shape = shapeComponentes
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. DATOS PRINCIPALES
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = shapeComponentes
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = nombre, onValueChange = { nombre = it },
                        label = { Text("Título del anuncio") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors,
                        shape = shapeComponentes
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = direccion, onValueChange = { direccion = it },
                        label = { Text("Dirección") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors,
                        shape = shapeComponentes
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = descripcion, onValueChange = { descripcion = it },
                        label = { Text("Descripción detallada") },
                        modifier = Modifier.fillMaxWidth(), minLines = 3,
                        colors = textFieldColors,
                        shape = shapeComponentes
                    )
                }
            }

            // 3. CARACTERÍSTICAS
            Text(
                "Características:",
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                color = colorTema, fontWeight = FontWeight.Bold
            )

            // FlowRow para selección de extras
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                opcionesExtras.forEach { extra ->
                    val isSelected = extrasSeleccionados.contains(extra)
                    FilterChip(
                        selected = isSelected,
                        onClick = { extrasSeleccionados = if (isSelected) extrasSeleccionados - extra else extrasSeleccionados + extra },
                        label = { Text(extra) },
                        leadingIcon = if (isSelected) { { Icon(Icons.Default.Check, null) } } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorTema.copy(alpha = 0.1f),
                            selectedLabelColor = colorTema,
                            selectedLeadingIconColor = colorTema
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CAMPO "OTROS"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textoNuevoExtra,
                    onValueChange = { textoNuevoExtra = it },
                    label = { Text("Otro extra...") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors,
                    singleLine = true,
                    shape = shapeComponentes
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilledIconButton(
                    onClick = {
                        if (textoNuevoExtra.isNotBlank()) {
                            val nuevo = textoNuevoExtra.trim().replaceFirstChar { it.uppercase() }
                            extrasSeleccionados = extrasSeleccionados + nuevo
                            textoNuevoExtra = ""
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = colorTema),
                    modifier = Modifier.size(56.dp),
                    shape = shapeComponentes
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir")
                }
            }

            // CHIPS PERSONALIZADOS
            val extrasPersonalizados = extrasSeleccionados.filter { !opcionesExtras.contains(it) }
            if (extrasPersonalizados.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    extrasPersonalizados.forEach { extra ->
                        InputChip(
                            selected = true,
                            onClick = { extrasSeleccionados = extrasSeleccionados - extra },
                            label = { Text(extra) },
                            trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Eliminar") },
                            colors = InputChipDefaults.inputChipColors(
                                selectedContainerColor = colorTema.copy(alpha = 0.05f),
                                selectedLabelColor = colorTema,
                                selectedTrailingIconColor = colorTema
                            ),
                            border = InputChipDefaults.inputChipBorder(
                                borderColor = colorTema.copy(alpha = 0.5f),
                                enabled = true, selected = true
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. FOTOGRAFÍAS
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth(),
                shape = shapeComponentes
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Fotografías",
                            style = MaterialTheme.typography.titleMedium,
                            color = colorTema, fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = { launcher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = colorTema),
                            shape = shapeComponentes,
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Añadir", fontSize = 14.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (listaImagenes.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Sin imágenes seleccionadas", color = Color.Gray, fontSize = 14.sp)
                        }
                    } else {
                        LazyRow(modifier = Modifier.height(120.dp)) {
                            items(listaImagenes) { uri ->
                                Card(
                                    modifier = Modifier.size(120.dp).padding(end = 8.dp),
                                    shape = shapeComponentes,
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    AsyncImage(
                                        model = uri, contentDescription = null,
                                        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // BOTÓN GUARDAR
            Button(
                onClick = {
                    // CLAVE: Corrección de coma por punto para evitar errores numéricos.
                    val precioLimpio = precioText.replace(",", ".")
                    val precioDouble = precioLimpio.toDoubleOrNull()

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
                colors = ButtonDefaults.buttonColors(containerColor = colorTema),
                shape = shapeComponentes
            ) {
                Text("GUARDAR ANUNCIO", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            if (isError) Text("Falta el título o el precio", color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
    }
}