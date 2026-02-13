// Seleccionamos los elementos del DOM (Document Object Model) por su ID
const $tareas = document.getElementById('tareas'); // El contenedor <ul> o <div > donde irán las tareas
const $inputTarea = document.getElementById('input-tarea'); // El campo de texto
const $aniadirTarea = document.getElementById('aniadir-tarea'); // Botón de añadir
const $editarTarea = document.getElementById('editar-tarea'); // Botón de editar
let selectedValue; // Variable global para guardar temporalmente qué tarea estamos editando

// --- EVENTO AÑADIR ---
$aniadirTarea.addEventListener('click', () => {
    // Desestructuración: extraemos la propiedad 'value' de $inputTarea
    const { value } = $inputTarea; 
    
    // Validación: .trim() elimina espacios en blanco al inicio y final. 
    // Si está vacío, muestra alerta y termina la función (return).
    if (!value.trim()) {
        alert('La tarea está vacía');
        $inputTarea.value = '';
        return;
    }

    // Comprobación de duplicados:
    // Seleccionamos todos los <li> que ya existen dentro de #tareas
    const lis = document.querySelectorAll('#tareas li');
    for (const li of lis) {
        // Buscamos el <span> dentro de cada <li> para ver su texto
        if (li.querySelector('span').textContent === value) {
            alert('Esta tarea ya existe en la lista');
            $inputTarea.value = '';
            return; // Detenemos si encontramos duplicado
        }
    }
    
    // Creación de elementos: Creamos etiquetas HTML en memoria (aún no están en la pantalla)
    const li = document.createElement('li');
    const span = document.createElement('span');
    const buttonDelete = document.createElement('button');

    li.classList.add('tarea-row'); // Añadimos una clase CSS para estilizar
    span.textContent = value; // El texto de la tarea va en el span

    // Evento al hacer clic en el TEXTO de la tarea (para prepararla para edición)
    span.addEventListener('click', ({ target }) => {
        const { textContent } = target;
        $inputTarea.value = textContent; // Pone el texto en el input
        selectedValue = textContent; // GUARDA el valor original en la variable global para saber cuál editar luego
    });

    // Evento al hacer clic en el botón BORRAR
    buttonDelete.addEventListener('click', () => {
        li.remove(); // Elimina este <li> específico del DOM
    });
    buttonDelete.textContent = 'X';

    // Montaje: Metemos el span y el botón dentro del li
    li.appendChild(span);
    li.appendChild(buttonDelete);
    
    // Inserción: Metemos el li completo en la lista principal
    $tareas.appendChild(li);
    
    // Limpiamos el input
    $inputTarea.value = '';
});

// --- EVENTO EDITAR ---
$editarTarea.addEventListener('click', () => {
    const { value } = $inputTarea; // El nuevo valor que escribió el usuario
    
    if (!value.trim()) {
        alert('La tarea está vacía');
        $inputTarea.value = '';
        return;
    }

    // Recorremos todas las tareas para encontrar la que coincide con 'selectedValue'
    const lis = document.querySelectorAll('#tareas li');
    for (const li of lis) {
        const span = li.querySelector('span');
        // Si el texto del span coincide con lo que guardamos al hacer click en la tarea...
        if (span.textContent === selectedValue) {
            span.textContent = value; // ...actualizamos el texto con el nuevo valor
        }
    }
    $inputTarea.value = ''; // Limpiar input
});