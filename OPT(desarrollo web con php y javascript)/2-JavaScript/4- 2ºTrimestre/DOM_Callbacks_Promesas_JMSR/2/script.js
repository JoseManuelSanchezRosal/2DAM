const $form = document.querySelector('form');
const $resultados = document.getElementById('resultados');

let formulario = {
    titulo: '',
    cuerpo: '',
    autor: ''
}

$form.addEventListener('submit', e => {
    // IMPORTANTE: Previene que la página se recargue (comportamiento por defecto de los forms)
    e.preventDefault();
    
    // FormData crea un objeto iterador con todos los inputs que tengan el atributo 'name' dentro del form
    const formData = new FormData($form);
    
    // Object.fromEntries transforma ese iterador (pares clave-valor) directamente en un Objeto JS
    const data = Object.fromEntries(formData.entries());
    
    // Spread operator (...data): Copia las propiedades de 'data' dentro de 'formulario'
    formulario = {
        ...data
    }
    
    // Resetea visualmente los campos del formulario HTML
    $form.reset();

    // Muestra el resultado
    const p = document.createElement('p');
    // JSON.stringify convierte el Objeto JS en un String para poder imprimirlo en pantalla
    p.textContent = JSON.stringify(formulario);
    $resultados.appendChild(p);
});