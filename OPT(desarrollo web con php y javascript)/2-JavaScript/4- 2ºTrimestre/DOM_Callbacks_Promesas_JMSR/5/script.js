const $input = document.getElementById('input');
const $resultado = document.getElementById('resultado');

// Evento 'input': se dispara cada vez que escribes una letra
$input.addEventListener('input', ({ target }) => {
    // Llamamos a 'funcion' pasando el texto y una CALLBACK (la arrow function)
    $resultado.textContent = funcion(target.value, text => {
        // Regex: ^[0-9]{1,}$ -> Solo números desde el inicio al fin
        if (/^[0-9]{1,}$/.test(text)) {
            return 'Es numérico';
        } 
        // Regex: Contiene letras Y contiene números (alfanumérico)
        else if (/[a-zA-Z0-9]{1,}/.test(text) && /[0-9]{1,}/.test(text)) {
            return 'Es alfanumérico'; 
        } 
        // Si no, asumimos alfabético (esto podría fallar con símbolos, pero es la lógica del ej)
        else {
            return 'Es alfabético';
        }
    });
});

// Esta función recibe el texto y la función que definimos arriba (callback)
function funcion(texto, callback) {
    return callback(texto); // Ejecuta la función que le pasaron
}