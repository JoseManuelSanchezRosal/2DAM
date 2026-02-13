function saludar(nombre) {
    new Promise(resolve => {
        setTimeout(() => {
            // Gracias a las "closures" de JS, la función de adentro recuerda la variable 'nombre'
            // aunque pasen 2 segundos.
            console.log(`Hola ${nombre}`);
            resolve(); // Finaliza la promesa
        }, 2000);
    });
}

saludar('José'); // Imprime "Hola José" a los 2 segundos