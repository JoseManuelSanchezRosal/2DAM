// Función que crea una tabla según los valores introducidos por el usuario
function crearTabla() {
  // Obtener el número de filas y columnas desde los inputs
  let filas = parseInt(document.getElementById("filas").value);
  let columnas = parseInt(document.getElementById("columnas").value);

  // Crear el código HTML de la tabla en una cadena
  let html = "<table>";

  // Bucle para crear las filas
  for (let i = 0; i < filas; i++) {
    // Asignar color según si la fila es par o impar
    let claseFila = (i % 2 === 0) ? "fila-par" : "fila-impar";
    html += `<tr class="${claseFila}">`;

    // Bucle para crear las columnas
    for (let j = 0; j < columnas; j++) {
      html += "<td></td>"; // Cada celda vacía
    }

    html += "</tr>";
  }

  html += "</table>";

  // Mostrar la tabla en el div con id="tabla"
  document.getElementById("tabla").innerHTML = html;
}

// Ejercicio 11
function cambiarEstilos() {
    // Seleccionamos toda la tabla que hay en el contenedor con id=tabla
    let tablaGenerada = document.querySelector("#tabla table");
    let filas = tablaGenerada.rows; // Obtener todas las filas de la tabla

    // Recorrer cada fila de la tabla
    for (let i = 0; i < filas.length; i++) {
        let celdas = filas[i].cells; // Recorremos las filas

        // Recorrer cada columna de cada fila
        for (let j = 0; j < celdas.length; j++) {
            let celda = celdas[j];

            // Asignar a cada celda la clase según si la columna es par o impar
            if (j % 2 === 0) {
                celda.classList.add("columna-par");
            } else {
                celda.classList.add("columna-impar");
            }
        }
    }
}