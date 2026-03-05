const productos = [
    {
        id: 1,
        name: 'Ordenador sobremesa',
        price: 584.3,
        stock: 5
    },
    {
        id: 2,
        name: 'Móvil',
        price: 43.3,
        stock: 510
    },
    {
        id: 3,
        name: 'Reloj Suunto',
        price: 432.5,
        stock: 2
    }
];

function obtenerProducto(id) {
    // Retornamos la promesa para que quien llame a la función pueda usar .then()
    return new Promise((resolve, reject) => {
        // .find busca en el array. Si no encuentra nada, devuelve undefined
        const producto = productos.find(producto => producto.id === id);
        
        if (producto) {
            resolve(producto); // ¡Éxito! Enviamos el objeto producto
        } else {
            reject(new Error('No se ha encontrado el producto')); // ¡Error! Enviamos un error
        }
    });
}

// Consumo de la promesa
obtenerProducto(4) // Buscamos ID 10
    .then(producto => {
        // Se ejecuta SOLO si se llamó a resolve()
        console.log(producto)
    })
    .catch(error => {
        // Se ejecuta SOLO si se llamó a reject()
        console.error(error); // Imprimirá "Error: No se ha encontrado el producto"
    });