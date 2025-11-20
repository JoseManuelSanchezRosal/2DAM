const $factorial = document.getElementById('factorial');
const $resultado = document.getElementById('resultado');

$factorial.addEventListener('keyup', ({ key }) => {
    if (key !== 'Enter') {
        return;
    }
    const numFactorial = $factorial.value;
    let factorial = numFactorial;
    for (let i = numFactorial - 1; i > 1; i--) {
        factorial *= i;
    }
    $resultado.textContent = factorial;
    
});