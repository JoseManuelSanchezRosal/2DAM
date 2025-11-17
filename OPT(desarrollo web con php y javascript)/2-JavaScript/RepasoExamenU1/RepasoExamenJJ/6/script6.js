const $text1 = document.getElementById('text1');
const $text2 = document.getElementById('text2');
const $borrar = document.getElementById('borrar');

$text1.addEventListener('input', ({ target }) => {
    const { value } = target;
    const number = parseInt(value);

    if (isNaN(number)) {
        return;
    }

    $text2.value = number;
});

$borrar.addEventListener('click', () => {
    $text1.value = '';
    $text2.value = '';
});