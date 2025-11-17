const $num = document.getElementById('num');
const $numeroAdivinado = document.getElementById('numero-adivinado');

$num.addEventListener('keyup', ({ key }) => {
    if (key !== 'Enter') {
        return;
    }
    const numRandom = Math.floor(Math.random() * 50 + 1);
    console.log(numRandom)
    debugger
    if (numRandom === parseInt($num.value)) {
        $numeroAdivinado.textContent = 'Has adivinado el número';
    } else {
        $numeroAdivinado.textContent = 'Número incorrecto';
    }
});