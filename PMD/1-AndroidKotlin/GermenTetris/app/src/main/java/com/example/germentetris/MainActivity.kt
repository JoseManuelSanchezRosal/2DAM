
package com.example.germentetris


import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View               // Clase base para todos los elementos visuales
import android.widget.Button           // Clase para usar botones en la interfaz
import androidx.activity.enableEdgeToEdge   // Permite ocupar toda la pantalla (modo inmersivo)
import androidx.appcompat.app.AppCompatActivity   // Clase base para actividades con soporte moderno
import androidx.core.content.ContextCompat       // Permite acceder a recursos (colores, etc.) de forma segura
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import model.Rectangulo                 // Importa la clase Rectangulo creada por el programador
import model.RectanguloBorde            // Importamos la clase RectanguloBorde
import kotlin.random.Random

/* =============================================================
CLASE PRINCIPAL: MainActivity
-------------------------------------------------------------
Esta clase representa la pantalla principal de la aplicación.
Gestiona la interacción entre el usuario (botones) y el rectángulo
mostrado en la interfaz.
=============================================================*/
class MainActivity : AppCompatActivity() {

    /*
    MÉTODE onCreate()

    Es el punto de entrada de la actividad. Se ejecuta cuando
    la pantalla se crea por primera vez.

    Aquí se cargan los componentes visuales (layout), se crean
    las referencias a los botones y al rectángulo, y se define
    la lógica de los eventos al pulsar los botones.
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llamamos al métode padre para completar la inicialización
        super.onCreate(savedInstanceState)

        // Permite que la app use toda la pantalla, incluso bajo la barra de estado
        enableEdgeToEdge()

        // Carga el archivo XML que define la interfaz (activity_main.xml)
        setContentView(R.layout.activity_main)

        /*
        // Inicialización de elementos visuales y objetos
        */

        // Se obtiene la vista (View) que representa el rectángulo
        val rectanguloView: View = findViewById(R.id.rectangulo)
        rectanguloView.post {
            // Creamos variables constantes para X, Y, ancho y alto para que nuestro rectangulo no cambie la posicion al cambiar el color o el tamaño
            val inicialX = rectanguloView.x.toInt()
            val inicialY = rectanguloView.y.toInt()
            val inicialWidth = rectanguloView.width
            val inicialHeigh = rectanguloView.height

            /*Se crea una instancia del modelo Rectangulo con:
            - Color rojo obtenido de los recursos
            - Tamaño inicial 100x100 píxeles*/
            /*val rectangulo: Rectangulo = Rectangulo(
                ContextCompat.getColor(this, R.color.red),  // Color inicial
                inicialWidth,                                        // Ancho inicial
                inicialHeigh                                         // Alto inicial

            ).apply {
                x = inicialX
                y = inicialY
            }*/

            // Cambiamos la instancia del rectangulo por la de rectangulo con borde
            val rectangulo = RectanguloBorde(ContextCompat.getColor(this, R.color.red), inicialWidth, inicialHeigh).apply{
                x=inicialX
                y=inicialY
                bordeColor = ContextCompat.getColor(this@MainActivity, R.color.black)
            }

            /*=====================================================
            Referencias a los botones definidos en el layout XML
            =====================================================*/
            val buttonAbajo: Button = findViewById(R.id.buttonAbajo)
            val buttonArriba: Button = findViewById(R.id.buttonArriba)
            val buttonIzquierda: Button = findViewById(R.id.buttonIzquierda)
            val buttonDerecha: Button = findViewById(R.id.buttonDerecha)
            val buttonColor: Button = findViewById(R.id.buttonColor)
            val buttonTamano: Button = findViewById(R.id.buttonTamano)
            val buttonCambioCBorde: Button = findViewById(R.id.buttonCambioColorBorde)


            /*=====================================================
            Definición de eventos de clic para cada botón
            Cada bloque establece una acción sobre el rectángulo
            y luego actualiza la vista en pantalla.
            =====================================================*/

            // Mueve el rectángulo hacia ARRIBA
            buttonArriba.setOnClickListener {
                rectangulo.moveUp()                              // Modifica su coordenada Y
                actualizaVista(rectangulo, rectanguloView)        // Refresca la vista
            }
            // Mueve el rectángulo hacia ABAJO
            buttonAbajo.setOnClickListener {
                rectangulo.moveDown()                            // Incrementa su coordenada Y
                actualizaVista(rectangulo, rectanguloView)
            }
            // Mueve el rectángulo hacia la IZQUIERDA
            buttonIzquierda.setOnClickListener {
                rectangulo.moveLeft()                            // Decrementa su coordenada X
                actualizaVista(rectangulo, rectanguloView)
            }
            // Mueve el rectángulo hacia la DERECHA
            buttonDerecha.setOnClickListener {
                rectangulo.moveRigth()                           // Incrementa su coordenada X
                actualizaVista(rectangulo, rectanguloView)
            }
            // Cambia el TAMAÑO del rectángulo a 300x300
            buttonTamano.setOnClickListener {
                // Definimos los límites de ancho y alto
                val anchoMin = 100
                val anchoMax = 400
                val altoMin = 100
                val altoMax = 400

                // Generamos valores aleatorios dentro de esos límites
                val nuevoAncho = (anchoMin..anchoMax).random()
                val nuevoAlto = (altoMin..altoMax).random()

                // Aplicamos el cambio
                rectangulo.cambioTamano(nuevoAncho, nuevoAlto)
                actualizaVista(rectangulo, rectanguloView)
            }

            // Cambia el COLOR del rectángulo a rojo
            buttonColor.setOnClickListener {
                rectangulo.cambioColor()
                actualizaVista(rectangulo, rectanguloView)
            }

            // Aqui estaba el fallo del porque no cambiaba el color del borde
            buttonCambioCBorde.setOnClickListener {
                rectangulo.cambiarColorBorde(Color.rgb(
                    Random.nextInt(256),
                    Random.nextInt(256),
                    Random.nextInt(256)
                ))
                actualizaVista(rectangulo, rectanguloView)
            }
        }
    }

    /*=========================================================
       MÉTODe actualizaVista()
       ---------------------------------------------------------
       Función auxiliar que sincroniza el estado del objeto
       Rectangulo con la vista visual mostrada en pantalla.

       Parámetros:
           - rectangulo: objeto de tipo Rectangulo que contiene las
           propiedades (color, tamaño, posición).
           - rectanguloView: vista (View) que se muestra en la interfaz.
       =========================================================*/
    fun actualizaVista(rectangulo: RectanguloBorde, rectanguloView: View) {
        // ACTUALIZAR TAMAÑO
        // Se ajustan los parámetros de ancho y alto de la vista
        // al tamaño actual del objeto Rectangulo.*/
        rectanguloView.layoutParams.width = rectangulo.ancho
        rectanguloView.layoutParams.height = rectangulo.alto

        val drawable = GradientDrawable()
        drawable.setColor(rectangulo.color)

        drawable.setStroke(10, rectangulo.bordeColor)





        /* -----------------------------------------------------
        ACTUALIZAR COLOR
        -----------------------------------------------------
        Cambia el color de fondo del rectángulo visible.*/

        //rectanguloView.setBackgroundColor(rectangulo.color)
        rectanguloView.background = drawable

        /* -----------------------------------------------------
        ACTUALIZAR POSICIÓN
        -----------------------------------------------------
        Establece la posición (coordenadas X e Y) del rectángulo
        dentro del contenedor. Se convierte a Float porque las
        vistas en Android manejan coordenadas en coma flotante.*/
        rectanguloView.x = rectangulo.x.toFloat()
        rectanguloView.y = rectangulo.y.toFloat()

        /* -----------------------------------------------------
        SOLICITAR REDIBUJADO
        -----------------------------------------------------
        Indica al sistema que el layout ha cambiado y necesita
        ser redibujado para mostrar los nuevos valore*/
        rectanguloView.requestLayout()
    }
}