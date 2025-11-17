const $num1 = document.getElementById('num1');
const $num2 = document.getElementById('num2');
const $tipoOperacion = document.getElementById('tipo-operacion');
const $resultado = document.getElementById('resultado');

const OPERACIONES = {
    SUMA: 'Suma',
    RESTA: 'Resta',
    MULTIPLICACION: 'Multiplicación',
    DIVISION: 'División'
}

// Crea un script que reciba dos números y en función de un texto sume, reste, multiplique o divida, en el caso de la división tiene que decir el cociente y el resto de la operación.
/**
 * 
 * @param {OPERACIONES} operacion 
 * @param {*} num1 
 * @param {*} num2 
 */
function operacion(operacion, num1, num2) {
    if (operacion === OPERACIONES.SUMA) {
        return num1 + num2;
    } else if (operacion === OPERACIONES.RESTA) {
        return num1 - num2;
    } else if (operacion === OPERACIONES.MULTIPLICACION) {
        return num1 * num2;
    } else if (operacion === OPERACIONES.DIVISION) {
        return `Cociente: ${num1 / num2}. Resto: ${num1 % num2}`;
    }
}

// Object destructuring (coger propiedades de un objeto directamente)
$tipoOperacion.addEventListener('keyup', ({ key }) => {
    if (key !== 'Enter') {
        return;
    }
    $resultado.textContent = operacion($tipoOperacion.value, parseInt($num1.value), parseInt($num2.value));
});