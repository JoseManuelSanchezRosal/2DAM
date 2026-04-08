package com.example.ejemplovar

open class herenciaPersona (var nombre: String, var edad: Int) {

    // si abrimos una función podemos modificarla en la clase heredada
    open fun mostrarPersona(){
        println("Nombre: $nombre, edad: $edad")
    }

}

open class herenciaPersona2 (var nombre: String, var edad: Int): claseAbstracta() {

    // si abrimos una función podemos modificarla en la clase heredada
    open fun mostrarPersona(){
        println("Nombre: $nombre, edad: $edad")
    }

    override fun saludo() {
        println("Bienvenido/a $nombre")
    }
}