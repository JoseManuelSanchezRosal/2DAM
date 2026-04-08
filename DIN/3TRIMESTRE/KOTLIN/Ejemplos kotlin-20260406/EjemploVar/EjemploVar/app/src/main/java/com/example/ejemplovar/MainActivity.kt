package com.example.ejemplovar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //variables()
        //variablesNulas()
        //sentenciasCondicionales()
        //CondicionalSwitch()
        //verArrays()
        //mapasValores()
        //bucles()
        //imprimirCadena("Hola clase")
        //realizarsuma()
        //ejemploClases()
        //clasesLimitadas()
        //clasesAnidadasEInternas()
        //herenciaDeClases()
        //encapsuladoDeDatos()
        funcionesLambdas()


    }
    // Esto es un comentario

    /*
    * Esto tambien es
    * un comentario de
    * varias líneas */

    private fun variables(){
        // Variables
        var esVariable = "Hola mundo" // Esta variable puede modificarse durante su ejecución

        val constantePi = 3.1416 // Esta variable es una constante y no cambiara durante su ejecución

        println(esVariable)
        println(constantePi)

        // Si intentamos introducir un entero en la variable esVariable, se producirá un error
        // X esVariable = 1
    }

    private fun tiposVariables(){

        // String
        var cadena = "Esto es una cadena"

        // Numero entero
        var entero = 3

        // Double
        var tipoDouble = 4.321

        // Booleano
        var verdadoMentira = false

        // Variable con tipo declarado
        //var esNumero: Int

        // Variables enteras Byte, Short, Int y Long

        var byteNumber: Byte // Almacena valores desde [-129,127]
        var shortNumber: Short // Almacena valores desde [-32768,32767]
        var intNumber: Int // Almacena valores para enteros de 32 bits [-2.147.483.648*2^-31,2.147.483.647*((2^31)-1)]
        var longNumber: Long // Almacena valores para enteros de 64 bits

        // Variables con decimales doubles y float

        var doubleNumber: Double // numero decimal de 64 bits
        var floatNumber: Float // numero decimal de 32 bits

        floatNumber = 1.123f //El tipo de dato float acaba con una f al final

        val booleano = false // Solo puede tomar de valor true o false
    }

    private fun variablesNulas(){
        var ejemplo = "Hola mundo"

        //ejemplo=null //Esto daria un error
        println(ejemplo)

        // Si queremos crear una variable que pueda contener el valor null, necesitamos declarar el tipo de variable e introducir un ? al final
        var ejemploNulo: String? = "Hola de nuevo" // Esta variable puede contener o un String o el valor null
        println(ejemploNulo)

        ejemploNulo=null
        println(ejemploNulo) // Si introducimos !! nos saldrá un error por pantalla

        ejemploNulo="hola"

        // Esta es la forma primitiva de comprobar que la varaible no fuera nula
        if(ejemploNulo != null){
            println("La variable no es nula: ${ejemploNulo!!}") // Los !! nos fuerza a que la variable no sea nula
        }else{
            println("Variable nula")
        }

        // Safe call
        println(ejemploNulo?.length) //Si utilizamos ? mostrara null si nuestra variable en nula y la longitud de la cadena si no lo es

        // Actualmente, tenemos la función let para comprobar que no sea nula
        ejemploNulo?.let {
            println(it) // Si la variable es nula ejecuta esta parte del código
        } ?: run {
            println(ejemploNulo) // Si la variable no es nula ejecuta esta
        }

    }

    private fun operacionesString(){
        var miCadena1 = "Primera cadena"
        var miCadena2 = "Segunda cadena"
        var cadenaDeclarada: String

        val numeroPi = 3.1415
        val radio= 3.82

        // Concatenar cadena

        cadenaDeclarada = miCadena1 + miCadena2
        println(cadenaDeclarada)


        /* Podemos introducir operaciones y otros tipos de variables dentro de una cadena utilizando ${}
        ha esto se le conoce como string templates */
        val cadenaConNumeros = "El radio de la circunferencia es: ${numeroPi*radio}"

        println(cadenaConNumeros)

    }

    private fun operacionesNumeros(){

        // Numero enteros
        val num1 = 8
        val num2 = 2

        val numD = 5.5

        var resultadoEntero: Int = num1 + num2
        var resultado: Double = (num1 + num2).toDouble() // Si los dos numeros son entero es necesario un casting

        println("El resultado es: $resultado")
        println("El resultado entero es: $resultadoEntero")

        resultadoEntero=num1-num2
        resultado = (num1 - num2).toDouble()

        println("El resultado es: $resultado")
        println("El resultado entero es: $resultadoEntero")

        resultado=numD+num1 // Como uno de los numero es double, no es necesario realizar un casting

        println("El resultado es: $resultado")

        resultado+=numD

        println("El resultado es: $resultado")

        resultado++

        println("El resultado es: $resultado")

    }

    private fun sentenciasCondicionales(){
        val num1 = 10

        if (num1 < 20){
            println("$num1 es menor que 20")
        }else{
            println("$num1 es mayor o igual que 20")
        }

        val nota=9

        if(nota >= 9){
            println("Enhorabuena tienes un sobresaliente")
        } else if (nota >= 5){
            println("Enhorabuena has aprobado")
        }else{
            println("Has suspendido")
        }
    }

    private fun CondicionalSwitch(){
        val opcion=3

        // When es el equivalente a switch en otros lenguajes de programación
        when(opcion){
            1 -> {
                println("Has seleccionado la opción 1")
            }
            2 -> {
                println("Has seleccionado la opción 2")
            }
            3 -> {
                println("Has seleccionado la opción 3")
            }
            else -> {
                println("Opción no valida")
            }
        }

        val opcionCadena = "Opcion a"

        when(opcionCadena){
            "Opcion a" -> {
                println("Has seleccionado la opción a")
            }
            "Opcion b" -> {
                println("Has seleccionado la opción b")
            }
            "Opcion c" -> {
                println("Has seleccionado la opción c")
            }
            else -> {
                println("Opción no valida")
            }
        }

        val nota=5

        // Tambien podemos definir un rango de datos
        when(nota){
            10, 9 -> {
                println("Sobresaliente")
            }
            in 5..8 -> {
                println("Aprobado")
            }
            4, 3, 2, 1 -> {
                println("Suspenso")
            }
            else -> {
                println("Nota no valida no valida")
            }
        }
    }

    private fun verArrays(){
        // Crear un array
        // Para crear un array definimos ArrayList<Tipo_variable> = arrayListOf<Tipo_variable>()
        /* Estamos definiendo un array dinámico, extiten tambien los array con un tamaño fijo
        * (Array) pero son mucho mas eficientes con la memoria los array dinamicos*/
        val arrayNumeros: ArrayList<Int> = arrayListOf<Int>()

        val num1 = 1
        val num2 = 2
        val num3 = 3
        val num4 = 4

        // Añadir elementos a un array

        arrayNumeros.add(num1)
        arrayNumeros.add(num2)
        arrayNumeros.add(num3)
        arrayNumeros.add(num4)

        println(arrayNumeros)

        // Forma simplificada

        arrayNumeros.addAll(listOf(5,6))

        println(arrayNumeros)

        // Acceder a una posición del array
        val primera = arrayNumeros[0]


        println("La posicion 0 es $primera")

        // println("La posicion 0 es $arrayNumeros[0]) No funcionaria

        // Acceso 2
        arrayNumeros[1]=7

        // Eliminar un elemento del array

        // Para ello utilizamos la función removeAt(indice)
        arrayNumeros.removeAt(5)

        println(arrayNumeros)

        // Recorrer el array elemento a elemento
        // Utilizamos la funcion forEach y despues utilizamos el iterador (it)
        arrayNumeros.forEach { println(it) }

        //Array estatico
        /* Para crear un array fijo utilizamos Array<tipo_variable>
        * Utilizamos arrayOf para introducir los valores
        */
        val arrayFijo: Array<Int> = arrayOf(1,2,3,4,5)

        println(arrayFijo[2])

        /*Si descomentamos la siguiente línea nos saldrá un error,
        debido a que la lista tiene un tamaño fijo */
        //arrayFijo.add(1)

        // Tambien podemos crear una lista

        val miLista: List<Int>
        miLista = listOf(1,2,3,4,5,6)
        println(miLista)

    }

    private fun mapasValores(){

        /* Estamos creando un mapa de valores, Map<tipo_de_clave, tipo_valor_guarda>, en este caso la clave es de tipo string y guarda un entero
        * En este caso estamos creando un mapa estático, es decir, no se le pueden añadir elementos, se puede sustituir el mapeado
        * pero no añadir valores.
        * */
        var mapaDeValores: Map<String, Int> = mapOf()

        //Sobreescribimos el mapa
        mapaDeValores =  mapOf("Pablo" to 10, "Maria" to 5, "Ernestro" to 6)

        println(mapaDeValores)

        //Perdemos los valores anteriores
        mapaDeValores =  mapOf("Javi" to 10, "Dani" to 5, "Raul" to 6)

        println(mapaDeValores)

        //Creamos un mapa mutable
        var mapaDinamico: MutableMap<String, Int> = mutableMapOf<String, Int>("Javi" to 10, "Dani" to 5, "Raul" to 6)

        println(mapaDinamico)

        //Para añadir un elemento sin perder los anteriores introducimos la clave dentro de un mapa dinamico o mutable
        mapaDinamico["Pablo"]=10

        println(mapaDinamico)

        //Tambien podemos utilizar la función put, aunque este metodo no es tan habitual

        mapaDinamico.put("Maria", 5)

        println(mapaDinamico)

        // Se puede acceder al contenido de un elemento llamando al mapa y a la clave
        println(mapaDinamico["Dani"])

        // Para eliminar un valor solo tenemos que llamar a la funcion remove y indicarle la clave
        mapaDinamico.remove("Raul")

        println(mapaDinamico)

    }

    fun bucles(){
        var arrayNumeros: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6)
        var mapaDinamico: MutableMap<String, Int> = mutableMapOf<String, Int>("Javi" to 10, "Dani" to 5, "Raul" to 6)

        //bucles for para recorrer arrays y mapas

        for(iterador in arrayNumeros){
            println(iterador)
        }

        for(elementoMapa in mapaDinamico){
            println(elementoMapa)
            println(elementoMapa.key+ " " + elementoMapa.value)
        }

        //Se ejecuta desde el 0 al 10, incluido ambos
        for (i in 0..10){
            println("Con .. " + i)
        }

        //Con until se ejecuta del 0 al 9, sin incluir el 10
        for (i in 0 until 10){
            println("Con until " + i)
        }

        //Hasta ahora el incremento ha sido de uno en uno, si queremos modificar el incremento usamos step despues
        for (i in 0..10 step 2){
            println("Con .. $i con paso 2")
        }

        //En decremento de dos en dos, SIEMPRE a continuación del in el valor que toma inicialmente i
        for (i in 10 downTo 0 step 2){
            println("Hacia atras de 2 en 2: $i")
        }

        // Bucles while

        var itWhile = 0

        while (itWhile < 5) {
            println("Iterador del bucle while $itWhile")
            itWhile++
        }

        // Bucles Do...While
        var itDoWhile = 0

        do {
            println("Iterador del bucle Do while $itDoWhile")
            itDoWhile++
        }while (itDoWhile < 5)

    }

    fun imprimirCadena(cadena: String){
        println("La cadena es: $cadena")
    }

    fun sumar(num1: Int, num2: Int) : Int{
        val resultado: Int = num1+num2
        return resultado
    }

    fun realizarsuma(){
        val aux= sumar(6,2)
        println(aux)
    }

    fun ejemploClases(){
        val alumno = claseDePrueba("Dario", 20, arrayListOf("OPT","DIN","BD"), arrayListOf(9,10,9), null)

        println(alumno.nombre)

        alumno.mostrarNotas()
    }

    //Vamos a definir una clase numerada

    enum class Asignaturas (val num: Int){
        DIN(10), OPT(20), PDM (30), AD(40), SGE(50);

        fun descripcion(): String{

            //Tenemos que tener cuidado si utilizamos el return en el when, puesto que nos saltara un error si no ponemos todas las posibles confinaciones
            return when(this){
                DIN -> {
                    "Desarrollo de interfaces"
                }
                OPT -> {
                    "Optativa"
                }
                PDM -> {
                    "Programacion en dispositivos móviles"
                }
                AD -> {
                    "Acceso a datos"
                }
                SGE -> {
                    "Sistemas de gestión empresarial"
                }
                else -> {
                    "Error"
                }
            }            // Fin del when

        }        //Fin de la función

    }   //Fin de la clase enum

    fun clasesLimitadas(){

        var unaAsignatura: Asignaturas = Asignaturas.DIN // Para introducir un valor utilizamos el nombre de la clase enumerada y .

        println(unaAsignatura)

        // Las clases numeradas tienen dos propiedades name y ordinal
        // Devuelve el nombre de la variable
        println(unaAsignatura.name)

        // Indica en que posición se encuentra el valor actual de la variable dentro de la lista
        println(unaAsignatura.ordinal)

        // Llamar a una función de una clase enum
        println(unaAsignatura.descripcion())

        //Acceder a una propiedad creada
        println(unaAsignatura.num)

    }

    fun clasesAnidadasEInternas(){

        //Cuando declaramos la clase anidada no instaciamos la clase superior
        val claseA = ClaseAnidadaEInterna.claseAnidada()

        var resultado = claseA.sumar(2,5)

        println(resultado)

        //Cuando declaramos la clase interna, instaciamos la clase superior
        var claseB = ClaseAnidadaEInterna().claseInterna()

        var resultadoDos = claseB.sumarDos(2,5)

        println(resultadoDos)
    }

    fun herenciaDeClases(){
        val alumno1 = herenciaAlumno("Ruben", 20, "1º ASIR", 3)
        val persona1= herenciaPersona("Maria", 22)
        val persona2= herenciaPersona2("Helena", 23)

        persona1.mostrarPersona()
        alumno1.mostrarPersona()

        persona2.saludo()

        alumno1.asignaturasExistentes()

    }

    fun encapsuladoDeDatos(){
        val persona = AlmacenarDatos("Pedro", 29)
        persona.profesion="Profesor"

        val persona2 = AlmacenarDatos("Pedro", 18)
        persona2.profesion="Estudiante"

        val persona3 = AlmacenarDatos("Pedro", 29)
        persona3.profesion="Profesor"


        //equals

        //Podemos usar la función equals para comprobar si dos objetos de una clase son iguales
        if (persona.equals("Pedro")){ //Esto devolverá false, porque no le estamos introduciendo ni la edad ni la profesión
            println("Son iguales")
        }else{
            println("No son iguales")
        }

        if (persona == persona2){
            println("Son iguales")
        }else{
            println("No son iguales")
        }

        if (persona == persona3){
            println("Son iguales")
        }else{
            println("No son iguales")
        }

        //toString
        println(persona.toString())

        // Copia el objeto, pero le cambio la edad
        val persona4 = persona.copy(edad=19)

        // Componente N
        val (nombre, edad) = persona

        println("El nombre es $nombre y su edad es: $edad")

    }

    fun funcionesLambdas(){
        val vectorDeEnteros = arrayListOf<Int>(1,2,3,4,5,6,7,8,9)

        /*
        // Esta es la forma mas simple de utilizar una lambda
        var numerosPares = vectorDeEnteros.filter {
            it%2==0
        }
        */

        //Otra forma de utilizar funciones lambda
        var numerosPares = vectorDeEnteros.filter { miIt ->

            if(miIt==9){
                return@filter true
            }
            miIt%2==0
        }

        println(numerosPares)

        //Definir una funcion
        val sumar = fun (num1: Int, num2: Int): Int = num1 + num2

        var resultado=sumar(4,5)
        println("El resultado es: $resultado")

        resultado=operacionSumaLambda(4,8,sumar)

        println("El resultado es: $resultado")

        //Definir una funcion lambda en vez de pasarla por el argumento
        resultado=operacionSumaLambda(4,8){a,b -> b - a}

        println("El resultado es: $resultado")

        funcionAsincrona("Manuel"){
            println(it)
        }

        println("Esta sentencia esta después de la llamada asíncrona, pero saldrá antes porque la ejecución no se para")

    }

    // En esta funcion definimos dos parámetros de entrada y el nombre de una función, lo que estamos empleando es una Callback
    fun operacionSumaLambda(num1: Int, num2: Int, func:(Int, Int) -> Int) :Int {
        return func(num1,num2)
    }

    //Estamos creando una funcion asíncrona
    fun funcionAsincrona(nombre: String, hola: (String) -> Unit){
        val saludo = "Hola $nombre"
        //He creado un hilo en kotlin
        thread {
            Thread.sleep(2000)
            hola(saludo)
        }
    }

}