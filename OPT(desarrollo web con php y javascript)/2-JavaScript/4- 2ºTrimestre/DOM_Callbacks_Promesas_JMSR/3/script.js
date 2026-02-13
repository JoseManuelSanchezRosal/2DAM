const $contenido = document.getElementById('contenido'); // Donde irán las celdas
const $datos = document.getElementById('datos'); // El input
const $generar = document.getElementById('generar'); // El botón

$generar.addEventListener('click', () => {
    const { value } = $datos;
    if (!value.trim()) {
        return;
    }

    dibujar(value); // Llamamos a la función auxiliar
    $datos.value = '';
});

function dibujar(texto) {
    // .split(' ') rompe el texto por espacios y crea un Array. Ej: "10 true hola" -> ["10", "true", "hola"]
    const elementos = texto.split(' ');

    const tr = document.createElement('tr'); // Crea una fila de tabla
    
    for (const elemento of elementos) {
        const td = document.createElement('td'); // Crea una celda
        
        // Lógica de tipos:
        if (!isNaN(elemento)) { 
            // isNaN (is Not a Number). Si NO es NaN, es un número.
            td.classList.add('number'); // Clase CSS para números
        } else if (['true', 'false'].includes(elemento)) {
            // Verifica si el texto es exactamente "true" o "false"
            td.classList.add('bool'); // Clase CSS para booleanos
        } else {
            // Cualquier otra cosa es texto
            td.classList.add('string'); // Clase CSS para strings
        }
        
        td.textContent = elemento;
        tr.appendChild(td); // Añade la celda a la fila
    }
    $contenido.appendChild(tr); // Añade la fila a la tabla
}