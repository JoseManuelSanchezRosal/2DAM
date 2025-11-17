const $tabla = document.getElementById('tabla');

const timeInit = new Date();

setInterval(() => {
    const tabla = document.createElement('table');
    const seconds = ((new Date() - timeInit) / 1000) / 5;
    for (let i = 1; i <= seconds; i++) {
        const fila = document.createElement('tr');
        const columna = document.createElement('td');
        fila.appendChild(columna);
        tabla.appendChild(fila);
    }
    document.body.appendChild(tabla);
}, 5000);