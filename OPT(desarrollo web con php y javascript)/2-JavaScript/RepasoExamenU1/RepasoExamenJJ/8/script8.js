const $parrafo = document.getElementById('parrafo');
const $resultado = document.getElementById('resultado');

setTimeout(() => {
    $resultado.textContent = $parrafo.value;
}, 10000);