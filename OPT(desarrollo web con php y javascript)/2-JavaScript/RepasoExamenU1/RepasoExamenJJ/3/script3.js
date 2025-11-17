const $resultado = document.getElementById('resultado');
const $tirar = document.getElementById('tirar');

$tirar.addEventListener('click', () => {
    $resultado.textContent = Math.floor(Math.random() * 6 + 1);
});