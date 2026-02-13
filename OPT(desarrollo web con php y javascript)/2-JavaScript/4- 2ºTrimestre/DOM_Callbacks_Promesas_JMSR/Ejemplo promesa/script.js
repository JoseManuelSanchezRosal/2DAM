// 1. SELECCIÓN DE ELEMENTOS DEL DOM
const $eliminarProducto = document.getElementById('eliminar-producto'); // Botón principal
const $modal = document.querySelector('.modal'); // El fondo oscuro del modal
const $modalContent = document.querySelector('.modal-content'); // La caja blanca del modal
const $confirmar = document.getElementById('confirmar'); // Botón "Sí" dentro del modal
const $cancelar = document.getElementById('cancelar'); // Botón "No" dentro del modal

// 2. EVENTO PRINCIPAL (Hacemos la función 'async' para poder usar 'await')
$eliminarProducto.addEventListener('click', async () => {
    
    // Mostramos el modal añadiendo una clase CSS (por ejemplo, display: block)
    document.body.classList.add('show');

    // --- AQUÍ EMPIEZA LA MAGIA DE LA PROMESA ---
    // Creamos una constante que esperará el resultado de la Promesa.
    // 'await' PAUSA la ejecución de esta función aquí mismo. 
    // No pasará a la siguiente línea hasta que se llame a 'resolve()'.
    const quiereBorrar = await new Promise(resolve => {
        
        // Escuchamos el click en "Confirmar"
        // La opción { once: true } es importante: asegura que el evento se elimine tras usarse una vez
        // para evitar que se acumulen clicks en el futuro.
        $confirmar.addEventListener('click', () => {
            resolve(true); // ¡Despierta! Devuelve TRUE a la variable 'quiereBorrar'
        }, { once: true });

        // Escuchamos el click en "Cancelar"
        $cancelar.addEventListener('click', () => {
            resolve(false); // ¡Despierta! Devuelve FALSE a la variable 'quiereBorrar'
        }, { once: true });
    });
    // --- FIN DE LA ESPERA ---

    // El código se reanuda aquí solo cuando el usuario ha hecho click en uno de los botones.
    
    // Si resolve(false) fue llamado (botón cancelar):
    if (!quiereBorrar) {
        document.body.classList.remove('show'); // Ocultamos modal
        return; // Salimos de la función, no borramos nada
    }
    
    // Si resolve(true) fue llamado (botón confirmar):
    console.log('Producto eliminado'); // Lógica de borrado (petición a BD, etc.)
    document.body.classList.remove('show'); // Ocultamos modal
});

// 3. LÓGICA DE CIERRE DEL MODAL (UX)
// Si hacen click en el fondo oscuro ($modal), cerramos.
$modal.addEventListener('click', () => {
    document.body.classList.remove('show');
});

// Evitamos que el click en la caja blanca ($modalContent) cierre el modal.
// stopPropagation evita que el evento "suba" hasta $modal.
$modalContent.addEventListener('click', e => {
    e.stopPropagation();
});