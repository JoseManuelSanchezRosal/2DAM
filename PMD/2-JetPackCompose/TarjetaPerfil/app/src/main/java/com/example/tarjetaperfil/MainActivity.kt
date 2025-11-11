package com.example.tarjetaperfil


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tarjetaperfil.ui.theme.TarjetaPerfilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ejercicio2()

        }
    }
}

@Preview
@Composable
fun ejercicio2(){
    // 1 COLUMNA PRINCIPAL
    Column (
        modifier = Modifier.fillMaxSize()
            .padding(top =50.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text("Tarjeta de Perfil")
        // 2 FILA PRINCIPAL
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 5.dp) // Simplificado padding start/end
                .padding(top = 10.dp)
                // PARA PONER COLOR DE FONDO DEFINIDOS EN EL XML ES CON COLORRESOURCE:
                .background(colorResource(R.color.soft_green))
                // ESQUINAS REDONDEADAS
                .clip(RoundedCornerShape(10.dp))
                // Asegúrate de que el borde también use la forma redondeada
                .border(3.dp, Color.Black, RoundedCornerShape(10.dp))

        ){
            // COLUMNA CON LA IMAGEN A LA IZQUIERDA
            Column (
                modifier = Modifier.weight(1f)
                    .padding(top = 10.dp)
                    .padding(start = 10.dp)
                    .padding(bottom = 10.dp)
                    //.background(Color.Blue)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mifotillo),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        ,
                    contentScale = ContentScale.Crop
                )
            }
            // COLUMNA A LA DERECHA CON DATOS DE PERFIL Y BOTONES DE INTERACCION
            Column (
                modifier = Modifier.weight(1.5f)
                    .padding(top = 30.dp)
                    .padding(start = 10.dp),
                    //.background(Color.Red),
                horizontalAlignment = Alignment.Start
            ){
                Text("Apellidos:               Montoya Ruano")
                Text("Nombre:                 Juan Pablo")
                Text("Departamento:      Developer")
                Row (
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)


                ){

                    Button(onClick = { /* Acción 1 */ }) {
                        Text("Seguir")
                    }

                    Button(onClick = { /* Acción 2 */ }) {
                        Text("Mensaje")
                    }
                }
            }
        }
        Column (
            modifier = Modifier.fillMaxHeight()
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f)) // Esto empuja el Text hacia abajo
            Text("Jose Manuel Sanchez Rosal")
        }
    }
}