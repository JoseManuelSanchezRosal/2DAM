// Ejercicio 1
function crearTabla() {
    document.getElementById("tabla").innerHTML = "<tr><td>hola</td></tr>     <tr><td>adios</td></tr>";
}

// Ejercicio 2
function estilosTabla(){
    let tabla1 = document.getElementById("tabla");// Almacenamos el ID del elemento que queremos modificar en una variable.
    tabla1.style.fontSize = "12px";
    tabla1.style.color = "blue";
    let estilo = "font-size: 18px; color: blue;";
}

// Ejercicio 3
function hacerSuma(){
    let a = parseInt(document.getElementById("A").value);// Para coger el valor de un CAMPO de TEXTO, hay que parsear a ENTERO con parseint
    let b = parseInt(document.getElementById("B").value);// Para coger el valor del elemento hay que poner .value
    alert("La suma de los campos de texto es: " + (a + b));

}

// Ejercicio 4
function mostrarTabla(){
    let a = parseInt(document.getElementById("A").value);// Para coger el valor de un CAMPO de TEXTO, hay que parsear a ENTERO con parseint
    let b = parseInt(document.getElementById("B").value);// Para coger el valor del elemento hay que poner .value
    let resultados = a+b;
    const tabla2 = document.getElementById("tabla2").innerHTML = "<tr><td class='fila'>" + a + "</td></tr> <tr><td class='fila'> "+ b + "</td></tr></tr> <tr><td class='fila'> "+ resultados + "</td></tr>";
}

// Ejercicio 5
const boton = document.getElementById("changeWH");
const imagen = document.getElementById("relax");
let original = false; //Vamos a hacer click para alternar entre tamano original y el modificado.

boton.addEventListener("click", function() {
    if (!original){
        imagen.style.width = "100px";
        imagen.style.height = "100px"
        original = true;
    }else{
         imagen.style.width = "600px";
        imagen.style.height = "600px"
        original = false;

    }
});

// Ejercicio 6
const boton6 = document.getElementById("redimensionar");
const imagen6 = document.getElementById("relax");

boton6.addEventListener("click", () => {
// Obtener los valores de los inputs
let nuevoAncho = document.getElementById("ancho").value;
let nuevoAlto = document.getElementById("alto").value;

    
imagen6.style.width = nuevoAncho + "px";
imagen6.style.height = nuevoAlto + "px";
});