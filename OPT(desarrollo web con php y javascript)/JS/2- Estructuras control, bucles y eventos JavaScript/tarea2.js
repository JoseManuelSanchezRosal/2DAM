// Ejercicio 1
function compararNumero(){
    let input = document.getElementById("entrada"); //Guardamos el elemento INPUT completo en una variable. Esto nos permite acceder y modificar su valor (.value)
    let entrada = input.value //Guardamos en ENTRADA el valor actual que escribio el usuario en el input (esto es un String , no el elemento DOM)

    if(!/^[0-9]*$/.test(entrada)){ //^[0-9] puede sustituirse tambien por \d
        alert("La cadena introducida no es un numero");

        input.value = ""; // Borramos el campo si no es numero
        input.focus(); // Con el .focus() pone el cursor en el campo de texto para que el usuario vuelva a escribir.

        
    }else{
        alert("La cadena introducida es un numero");
    }
}

// Ejercicio 2
function enfocado(input){ // Al enfocar los inputs se les da estilo.
    input.classList.add("enfocado");
}
function desenfocado(input){
    input.classList.remove("enfocado"); // Al desenfocar los inputs vuelven a tener el estilo original
}

// Ejercicio 3
function borrar(){
    const inputs = document.getElementsByClassName("ej2"); // Seleccionamos el elemento por by CLASSName en lugar de by Id
    for (let i = 0; i < inputs.length; i++){
        inputs[i].value = "";
    }
}

// Ejercicio 4
function cambiarColor(){
    let color = document.getElementById("colores").value; //recogemos en la variable COLOR el valor seleccionado en el desplegable
    let divs = document.querySelectorAll(".ej4"); //seleccionamos todos los divs con la clase ej4
    for (let i = 0; i <divs.length; i++){ // y con el bucle les cambiamos el background segun color seleccionado en desplegable.
        divs[i].style.backgroundColor = color; //asignamos a los divs el color de la variable color
    }

}

// Ejercicio 6
// Seleccionamos las imágenes usando las ID's
let billete = document.getElementById("billete");
let moneda = document.getElementById("moneda");

// Inicializamos el orden CSS de las imagenes dentro del contenedor, el orden determina la posicion visual en un contenedor FLEX
billete.style.order = 1; //Billete 1 posición
moneda.style.order = 2;

// Función para intercambiar las posiciones usando order
function intercambiar() {
    let cambiarOrden = billete.style.order; // Guardamos el orden actual del billete en una variable

    // Aquí intercambiamos el orden de la moneda por el del billete y viceversa.
    billete.style.order = moneda.style.order;
    moneda.style.order = cambiarOrden;
}

// Asignamos la función al evento onmouseover de ambas imágenes
billete.onmouseover = intercambiar;
moneda.onmouseover = intercambiar;

// Ejercicio 7
// Función que se ejecuta cuando cambia el valor del input
function crearTabla() {
    // Obtenemos el valor introducido por el usuario en el input con id="numero" 
    let valor = document.getElementById("numero").value;

    // Seleccionamos el contenedor donde se añadirá la tabla
    let contenedor = document.getElementById("tablaContainer");

    // Limpiamos cualquier contenido anterior del contenedor
    // Esto evita que se acumulen tablas al cambiar el valor del input
    contenedor.innerHTML = "";

    // Creamos un elemento <table> nuevo
    let tabla = document.createElement("table");

    //<<<<<<<<<<<<<<<<<ESTO NO LO ENTIENDO MUY BIEN>>>>>>>>>
    // Creamos la cabecera de la tabla (<thead>) //
    let thead = document.createElement("thead"); // Creamos <thead>
    let trHead = document.createElement("tr");   // Creamos una fila <tr> para la cabecera
    let th = document.createElement("th");      // Creamos una celda <th> para el título
    th.textContent = "Números pares";           // Ponemos el título de la columna
    trHead.appendChild(th);                     // Añadimos el th a la fila
    thead.appendChild(trHead);                  // Añadimos la fila al thead
    tabla.appendChild(thead);                    // Añadimos el thead a la tabla

    // Creamos el cuerpo de la tabla (<tbody>)
    let tbody = document.createElement("tbody");   

    // Bucle que recorre todos los números pares desde 0 hasta "valor"
    for (let i = 0; i <= valor; i += 2) {
        let tr = document.createElement("tr");         // Creamos una fila <tr>
        let td = document.createElement("td");         // Creamos una celda <td>
        td.textContent = i;                            // Insertamos el número par en la celda
        tr.appendChild(td);                            // Añadimos la celda a la fila
        tbody.appendChild(tr);                         // Añadimos la fila al cuerpo de la tabla
    }
    // Añadimos la tabla creada con Xfilas al body del HTML
    tabla.appendChild(tbody);

    // Insertamos la tabla completa en el contenedor del HTML
    contenedor.appendChild(tabla);
}

// Ejercicio 8 y 9
function validarCampos(){
    //Obtenemos todos los campos de texto del formulario
    let camposTexto = document.querySelectorAll("#formulario input"); //Aplicado a la id formulario
    let mensajesError = document.querySelectorAll(".mensaje-error"); //Aplicado a los mensajes span
    
    // Recorremos todos los campos input uno por uno
    for (let i = 0; i < camposTexto.length; i++){
        let campoActual = camposTexto[i];

        // Valor del campo sin espacions al principio y final
        let contenidoCampo = campoActual.value.trim();

        // Verificamos si el campo está vacío o contiene números
        let campoVacio = (contenidoCampo === "");
        let contieneNumeros = /\d/.test(contenidoCampo);

        // Ahora si el campo está vacío o contiene algún número, le añadimos una classList (definida en el style) para  ponerle el background-color en red:
        if (campoVacio || contieneNumeros){
            mensajesError[i].textContent = "(Campo de texto requerido)";
            campoActual.classList.add("error"); // Mediante la funcion "classList.add" aplicada a una variable, nos permite añadir una o más clases CSS a ese elemento.

        }else{
            campoActual.classList.remove("error");
            mensajesError[i].textContent = "";
        }
    }
}

// Ejercicio 12
// Seleccionamos todas las imágenes con la clase "imagen"
let imagenes = document.querySelectorAll(".imagen");

// El foreach es una estructura para recorrer todos los elementos de una coleccion (o clase, en este caso) uno a uno para ejecutar un mismo conjunto de instrucciones para cada uno
imagenes.forEach(function(imagen) {
    
    // Cuando el ratón pasa sobre la imagen:
    imagen.onmouseover = function() {
        imagen.style.borderRadius = "50px"; // Función que asigna bordes redondeados a la imagen
    };

    // Cuando el ratón sale de la imagen:
    imagen.onmouseout = function() {
        imagen.style.borderRadius = "0"; // Función para volver al estado original de la imagen (sin bordes).
    };
});