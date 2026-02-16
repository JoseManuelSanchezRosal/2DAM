async function saludar(nombre, apellido) {
    // await PAUSA la ejecución aquí hasta que la promesa se resuelva
    // (Simulamos asincronía envolviendo el prompt en una promesa)
    const nombre1 = await new Promise(resolve => {
        resolve(prompt('Introduce tu nombre')); 
    });

    // Esta línea NO se ejecuta hasta que el usuario responda al prompt anterior
    const apellidos1 = await new Promise(resolve => {
        resolve(prompt('Introduce tu apellido'));
    });

    // Solo se ejecuta cuando ambas promesas han terminado
    console.log(`Hola ${nombre1} ${apellidos1}`)
}

saludar();