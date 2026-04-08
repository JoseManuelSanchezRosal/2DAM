package com.example.ejemplovar

class ClaseAnidadaEInterna {

    private val variableInt = 1

    // Para crear una clase anidada creamos una clase dentro de la clase
    class claseAnidada{

        fun sumar(num1: Int, num2: Int): Int{

            return num1 + num2

            // Si intentamos sumar la variable privada de la clase superior nos dara un fallo
            // return num1 + num2 + variableInt
        }
    } // fin clase anidada

    inner class claseInterna{
        // A esta clase se le denomina clase interna
        fun sumarDos(num1: Int, num2: Int): Int{

            return num1 + num2 + variableInt

        }
    } // Fin clase interna
}