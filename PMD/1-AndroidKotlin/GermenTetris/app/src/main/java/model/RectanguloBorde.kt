package model

import android.graphics.Color

class RectanguloBorde(
    color: Int,
    ancho: Int,
    alto: Int,
    var bordeColor: Int = Color.BLACK):Rectangulo(color, ancho, alto){


    fun cambiarColorBorde(nuevoColorBorde: Int){
        bordeColor = nuevoColorBorde
    }
}