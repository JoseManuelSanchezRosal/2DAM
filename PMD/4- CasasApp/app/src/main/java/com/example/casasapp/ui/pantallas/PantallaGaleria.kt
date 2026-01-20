package com.example.casasapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.casasapp.data.Casa
import com.example.casasapp.ui.CasaViewModel

@Composable
fun PantallaGaleria(navController: NavController, viewModel: CasaViewModel) {
    val casas by viewModel.casasFiltradas.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val esAlquiler by viewModel.filtroAlquiler.collectAsState()

    var casaABorrar by remember { mutableStateOf<Casa?>(null) }

    // --- COLORES DEL LOGO ---
    val colorVentaFuerte = Color(0xFFFD8D3C) // Naranja Logo
    val colorVentaSuave = Color(0xFFFFF3E0)  // Fondo naranja muy suave

    val colorAlquilerFuerte = Color(0xFF2C82C9) // Azul Logo
    val colorAlquilerSuave = Color(0xFFE1F5FE)  // Fondo azul muy suave

    val colorFondoPantalla = if (esAlquiler) colorAlquilerSuave else colorVentaSuave
    val colorAcentoActual = if (esAlquiler) colorAlquilerFuerte else colorVentaFuerte

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondoPantalla)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. SELECTOR TIPO
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Surface(
                color = Color.Transparent,
                shape = RoundedCornerShape(50),
                modifier = Modifier.clickable { viewModel.onFiltroAlquilerChange(false) }
            ) {
                Text(
                    text = "VENTA",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (!esAlquiler) colorVentaFuerte else Color.Gray,
                    fontWeight = if (!esAlquiler) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Switch(
                checked = esAlquiler,
                onCheckedChange = { viewModel.onFiltroAlquilerChange(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorAlquilerFuerte,
                    checkedTrackColor = Color.White,
                    uncheckedThumbColor = colorVentaFuerte,
                    uncheckedTrackColor = Color.White
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Surface(
                color = Color.Transparent,
                shape = RoundedCornerShape(50),
                modifier = Modifier.clickable { viewModel.onFiltroAlquilerChange(true) }
            ) {
                Text(
                    text = "ALQUILER",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (esAlquiler) colorAlquilerFuerte else Color.Gray,
                    fontWeight = if (esAlquiler) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        // 2. BARRA DE BÚSQUEDA
        OutlinedTextField(
            value = searchText,
            onValueChange = { viewModel.onSearchTextChange(it) },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp),
            placeholder = { Text("Buscar vivienda...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = colorAcentoActual) },
            singleLine = true,
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = colorAcentoActual,
                cursorColor = colorAcentoActual
            )
        )

        // 3. LISTA
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(casas) { casa ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("detalle/${casa.id}") },
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        if (casa.imagenes.isNotEmpty()) {
                            AsyncImage(
                                model = casa.imagenes.first(),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = casa.nombre,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(1f),
                                    color = colorAlquilerFuerte // Usamos azul para títulos
                                )
                                Surface(
                                    color = if (casa.esAlquiler) colorAlquilerFuerte else colorVentaFuerte,
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = "${casa.precio}€${if (casa.esAlquiler) "/mes" else ""}",
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }

                            Text(
                                text = casa.direccion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { navController.navigate("formulario?id=${casa.id}") }) {
                                    Icon(Icons.Default.Edit, "Editar", tint = colorAlquilerFuerte)
                                }

                                IconButton(onClick = { casaABorrar = casa }) {
                                    Icon(Icons.Default.Delete, "Borrar", tint = colorVentaFuerte)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (casaABorrar != null) {
        AlertDialog(
            onDismissRequest = { casaABorrar = null },
            title = { Text("¿Eliminar anuncio?", color = colorAlquilerFuerte) },
            text = { Text("Estás a punto de borrar '${casaABorrar?.nombre}'. Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        casaABorrar?.let { viewModel.borrarCasa(it) }
                        casaABorrar = null
                    }
                ) {
                    Text("BORRAR", color = colorVentaFuerte, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { casaABorrar = null }) {
                    Text("Cancelar", color = colorAlquilerFuerte)
                }
            },
            containerColor = Color.White
        )
    }
}