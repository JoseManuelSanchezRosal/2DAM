package com.example.casasapp.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casasapp.R

@Composable
fun PantallaInicio(navController: NavController) {
    // --- COLORES EXACTOS DEL LOGO ---
    val azulLogo = Color(0xFF2C82C9)
    val naranjaLogo = Color(0xFFFD8D3C)

    // Usamos BOX para poder superponer capas (Fondo + Contenido)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // --- CAPA 1: IMAGEN DE FONDO ---
        // Asegúrate de tener la imagen 'bg_fondo_inicio' en drawable
        Image(
            painter = painterResource(id = R.drawable.bg_fondo_inicio),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.4f), // TRUCO: Opacidad muy baja (40%) para efecto "marca de agua" sutil
            contentScale = ContentScale.Crop // Recorta la imagen para llenar la pantalla
        )

        // --- CAPA 2: CONTENIDO PRINCIPAL (Tu código anterior) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // LOGO
            Image(
                painter = painterResource(id = R.drawable.ic_home_logo),
                contentDescription = "Logo YourHome",
                modifier = Modifier
                    .size(350.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )



            Text(
                text = "Encuentra tu lugar ideal",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
            )

            // BOTONES MENU
            BotonMenuModerno(
                texto = "Explorar Catálogo",
                icono = Icons.Default.List,
                colorFondo = azulLogo,
                onClick = { navController.navigate("galeria") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BotonMenuModerno(
                texto = "Publicar Propiedad",
                icono = Icons.Default.Add,
                colorFondo = azulLogo,
                onClick = { navController.navigate("formulario") }
            )
        }
    }
}

@Composable
fun BotonMenuModerno(
    texto: String,
    icono: ImageVector,
    colorFondo: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icono, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = texto,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}