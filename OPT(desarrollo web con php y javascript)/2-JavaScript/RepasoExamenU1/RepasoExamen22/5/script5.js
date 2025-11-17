const $coche = document.getElementById('coche');
const $coches = document.getElementById('coches');
const $guardar = document.getElementById('guardar');
const $borrar = document.getElementById('borrar');

let coches = ['Mercedes', 'BMW', 'Ferrari', 'Citroen', 'Ford'];

// Aunque los arrays u objetos se creen con constantes, estos se pueden mutar
const cochesConstante = [];
const objetoConstante = {};
cochesConstante.push('Coche');
objetoConstante['Nuevo Elemento'] = 1;

// Con las constantes lo que no se puede hacer, es reasignar el valor
cochesConstante = 5; // X
cochesConstante = []; // X
objetoConstante = {}; // X

function updateState() {
    $coches.textContent = coches.join(', ');
    $coche.value = '';
}

$guardar.addEventListener('click', () => {
    // Esto es lo mismo que poner $coche.value
    const { value } = $coche;
    if (!value.trim()) {
        return;
    }
    coches.push($coche.value);
    updateState();
});

$borrar.addEventListener('click', () => {
    const { value } = $coche;
    if (!value.trim()) {
        return;
    }
    coches.splice(coches.indexOf($coche.value), 1);
    /* coches = coches.filter(coche => coche !== value); */
    updateState();
});

updateState();