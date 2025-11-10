package com.example.mipracticaconanidamientos


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Paso 1 Eliminamos el contenido de las llaves del setContent
        // Por supuesto, llamamos a nuestra funcion
        setContent {
            ejercicio1()
        }
    }
}

@Preview
@Composable
fun ejercicio1(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Parte 1: título
        Text("Practica de Filas, Columnas y Anidamientos")

        // Parte 2: ROW Principal
        Row(
            modifier = Modifier
                .fillMaxWidth() // Para repartir el ancho de la fila en sus 2 columnas hijas
                .background(Color.Magenta)

                .padding(top = 10.dp)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Columna Izquierda
            Column(
                modifier = Modifier
                    .weight(1f) // Indicamos 1f para que cada columna ocupe 1 fraccion
                    .background(Color.Red),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Columna Izquierda")
                Row {
                    Text("Elemento A  ")
                    Text("Elemento B")
                }
            }

            // Columna Derecha
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Yellow),
                horizontalAlignment = Alignment.CenterHorizontally //Centramos horizontalmente
            )
            {
                Text("Columna Derecha")
                Row {
                    Text("1")
                    Text("2")
                    Text("3")
                }
            }
        }

        // Separacion entre filas
        Spacer(modifier = Modifier.height(30.dp))

        // Parte 3: ROW Inferior
        Row (
            modifier = Modifier
                .background(Color.Green)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween //Indicamos espacio entre hijos
            )
        {
            Text("Opcion 1")
            Text("Opcion 2")
            Text("Opcion 3")
        }

        Column (
            modifier = Modifier.fillMaxHeight()
                .padding(bottom = 20.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Esto empuja el Text hacia abajo
            Text("Jose Manuel Sanchez Rosal")
        }
    }
}