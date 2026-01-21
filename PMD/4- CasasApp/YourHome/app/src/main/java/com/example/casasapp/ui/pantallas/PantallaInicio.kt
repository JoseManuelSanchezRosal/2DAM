package com.example.casasapp.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Importante para el scroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll     // Importante para el scroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casasapp.R
import java.util.Calendar

@Composable
fun PantallaInicio(navController: NavController) {
    val azulLogo = Color(0xFF2C82C9)

    // 1. ESTADO DEL SCROLL: Necesario para controlar el desplazamiento
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // --- CAPA 1: IMAGEN DE FONDO (Fija) ---
        Image(
            painter = painterResource(id = R.drawable.bg_fondo_inicio),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.4f),
            contentScale = ContentScale.Crop
        )

        // --- CAPA 2: CONTENIDO CON SCROLL ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // <--- MAGIA AQUÍ: Permite hacer scroll si no cabe
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            // Arrangement.Center funciona genial aquí:
            // - Si el móvil está en vertical (cabe todo): Lo centra bonito.
            // - Si está en horizontal (no cabe): Permite hacer scroll desde arriba.
            verticalArrangement = Arrangement.Center
        ) {
            // Logotipo
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

            // Botones
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

            // Espacio extra antes del footer
            Spacer(modifier = Modifier.height(48.dp))

            // --- FOOTER (Copyright) ---
            // Lo hemos movido DENTRO de la columna.
            // Así, en horizontal, está al final del scroll y no molesta encima de los botones.
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            Text(
                text = "© $currentYear José Manuel Sánchez Rosal",
                style = MaterialTheme.typography.labelSmall,
                color = Color.DarkGray
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
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo),
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