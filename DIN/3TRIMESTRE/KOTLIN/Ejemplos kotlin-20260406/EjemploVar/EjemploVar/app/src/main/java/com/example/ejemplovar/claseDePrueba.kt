package com.example.ejemplovar

class claseDePrueba(val nombre: String, val edad: Int, val asignaturas: ArrayList<String>, val notas: ArrayList<Int>, val especializacion: String? = null) {

    fun mostrarNotas(){

        for (i in 0 until asignaturas.count()){
            println("Asignatura ${asignaturas[i]} nota ${notas[i]}")
        }


    }
}