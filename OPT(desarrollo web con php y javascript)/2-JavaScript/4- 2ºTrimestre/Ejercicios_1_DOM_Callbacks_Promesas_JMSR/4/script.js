const $num1 = document.getElementById('num1');
const $num2 = document.getElementById('num2');
const $operacion = document.getElementById('operacion'); // Select con valores: suma, resta, etc.
const $calcular = document.getElementById('calcular');
const $resultados = document.getElementById('resultados');

// Objeto "Diccionario" de funciones
const operaciones = {
    suma: (num1, num2) => num1 + num2,
    resta: (num1, num2) => num1 - num2,
    producto: (num1, num2) => num1 * num2,
    division: (num1, num2) => num1 / num2
}

$calcular.addEventListener('click', () => {
    const { value: value1 } = $num1; // Renombramos value a value1
    const { value: value2 } = $num2;
    
    if (!value1.trim() || !value2.trim()) {
        return;
    }

    // Buscamos la función correcta usando el valor del select (ej: operaciones['suma'])
    const operacion = operaciones[$operacion.value];
    
    // Crear elementos de la tabla
    const tr = document.createElement('tr');
    const num1Td = document.createElement('td');
    const num2Td = document.createElement('td');
    const operacionTd = document.createElement('td');
    const resultadoTd = document.createElement('td');

    // Rellenar datos
    num1Td.textContent = value1;
    num2Td.textContent = value2;
    operacionTd.textContent = $operacion.value;
    
    // EJECUCIÓN: Llamamos a la función recuperada pasándole los números convertidos a enteros
    resultadoTd.textContent = operacion(parseInt(value1), parseInt(value2));

    // Añadir a la tabla
    tr.appendChild(num1Td);
    tr.appendChild(num2Td);
    tr.appendChild(operacionTd);
    tr.appendChild(resultadoTd);
    $resultados.appendChild(tr);

    // Resetear
    $num1.value = '';
    $num2.value = '';
    $operacion.value = 'suma';
});