function mensaje() {
    console.log('Iniciando función'); // 1. Síncrono: Se imprime al instante
    
    // Se crea la promesa. El código dentro del constructor de la promesa se ejecuta inmediatamente.
    let miPromesa = new Promise(resolve => {
        console.log('Estoy dentro de la promesa'); // 2. Síncrono
        
        // setTimeout es Asíncrono. Esto se va a la "Web API" y espera 3 segundos
        setTimeout(() => {
             // 4. Se imprime a los 8 segundos
            resolve(); // Marca la promesa como "Completada" (aunque nadie está escuchando el .then aquí)
        }, 8000);
    });

    miPromesa.then(
        () => { console.log('Salí de la promesa'); }
    );
}

mensaje(); // Llamada inicial
// 3. Si hubiera más código aquí abajo, se ejecutaría antes del setTimeout

// hay que implementar el .then