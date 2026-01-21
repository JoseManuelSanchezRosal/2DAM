package com.example.casasapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.casasapp.data.Casa
import com.example.casasapp.ui.CasaViewModel
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PantallaDetalle(id: Int, viewModel: CasaViewModel, navController: NavController) {
    // --- COLORES CORPORATIVOS ---
    val azulLogo = Color(0xFF2C82C9)
    val naranjaLogo = Color(0xFFFD8D3C)

    var casa by remember { mutableStateOf<Casa?>(null) }

    // CLAVE: LaunchedEffect se ejecuta una sola vez al entrar.
    // Llama al ViewModel para pedir los datos de la casa específica por ID.
    LaunchedEffect(id) {
        casa = viewModel.obtenerCasa(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Acción volver atrás
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = azulLogo)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        // CLAVE: BottomBar flotante con el precio y botón de contacto.
        // Mejora mucho la UX al tener la acción principal siempre visible.
        bottomBar = {
            casa?.let { c ->
                Surface(
                    shadowElevation = 16.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .navigationBarsPadding(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Precio total", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            Text(
                                // CLAVE: Lógica visual condicional (Color Azul o Naranja según sea alquiler o venta)
                                text = "${c.precio} €${if (c.esAlquiler) "/mes" else ""}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (c.esAlquiler) azulLogo else naranjaLogo
                            )
                        }
                        Button(
                            onClick = { /* Acción futura */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (c.esAlquiler) azulLogo else naranjaLogo
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("CONTACTAR", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { padding ->
        casa?.let { c ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()) // CLAVE: Permite scroll si la descripción es larga
            ) {
                // Carrusel de imágenes superior
                LazyRow(modifier = Modifier.height(300.dp)) {
                    items(c.imagenes) { imgUri ->
                        AsyncImage(
                            model = imgUri,
                            contentDescription = null,
                            modifier = Modifier.width(400.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Contenido principal en tarjeta blanca solapada (-20.dp offset)
                Column(
                    modifier = Modifier
                        .offset(y = (-20).dp)
                        .background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .padding(24.dp)
                ) {
                    // Etiquetas de tipo (Venta/Alquiler)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = if (c.esAlquiler) azulLogo.copy(alpha = 0.1f) else naranjaLogo.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = if (c.esAlquiler) "ALQUILER" else "VENTA",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (c.esAlquiler) azulLogo else naranjaLogo,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = c.nombre, style = MaterialTheme.typography.headlineSmall, color = azulLogo, fontWeight = FontWeight.Bold)
                    Text(text = c.direccion, style = MaterialTheme.typography.bodyLarge, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))

                    HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), color = Color.LightGray.copy(alpha = 0.5f))

                    if (c.extras.isNotEmpty()) {
                        Text(text = "Características", style = MaterialTheme.typography.titleMedium, color = azulLogo, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))
                        // CLAVE: FlowRow organiza los chips de características automáticamente en varias líneas
                        // evitando el scroll horizontal.
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            c.extras.forEach { extra ->
                                Surface(
                                    color = azulLogo.copy(alpha = 0.05f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(text = extra, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), color = azulLogo)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Descripción", style = MaterialTheme.typography.titleMedium, color = azulLogo, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = c.descripcion, style = MaterialTheme.typography.bodyMedium, lineHeight = 24.sp, color = Color.DarkGray)

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}