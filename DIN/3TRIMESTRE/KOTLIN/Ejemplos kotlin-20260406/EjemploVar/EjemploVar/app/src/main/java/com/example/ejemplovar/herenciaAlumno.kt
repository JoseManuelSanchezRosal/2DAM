package com.example.ejemplovar

class herenciaAlumno(nombre: String, edad: Int, var curso: String, var notaMedia: Int): herenciaPersona(nombre, edad), asignaturasInterfaz {
    override fun mostrarPersona(){
        // Si queremos llamar a la función original utilizamos super.nombreFuncion()
        //super.mostrarPersona()
        println("Hola alumno $nombre con edad: $edad")
        super.mostrarPersona()

    }

    override fun asignaturasExistentes() {
        println("Tiene muchas asignaturas")
    }

}