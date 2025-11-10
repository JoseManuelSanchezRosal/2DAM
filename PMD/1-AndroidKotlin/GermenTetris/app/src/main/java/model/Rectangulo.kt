package model

import android.graphics.Color
import androidx.core.graphics.toColor
import kotlin.random.Random


open class Rectangulo(var color:Int, var ancho:Int, var alto:Int) {
    // Coordenadas iniciales. Propiedades
    var x = 400
    var y = 1000

    // Construimos comportamientos
    fun moveUp() {
        y -= 50
    }

    fun moveDown() {
        y += 50
    }

    fun moveLeft() {
        x -= 50
    }

    fun moveRigth() {
        x += 50
    }

    // Cambiamos tamanio
    fun cambioTamano(nuevoAncho: Int, nuevoAlto: Int) {
        ancho = nuevoAncho
        alto = nuevoAlto
    }

    fun cambioColor(){
        color = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    }
}