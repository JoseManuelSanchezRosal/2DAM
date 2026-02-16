function calcular(operacion) {
    const operaciones = {
        '+': (num1, num2) => num1 + num2,
        '-': (num1, num2) => num1 - num2,
        'x': (num1, num2) => num1 * num2,
        '/': (num1, num2) => num1 / num2
    }

    // Object.keys nos da un array con los signos: ['+', '-', 'x', '/']
    for (const signo of Object.keys(operaciones)) {
        
        // Si el string de entrada (ej: "10+20") incluye el signo actual
        if (operacion.includes(signo)) {
            
            // split divide el string usando el signo como separador
            // "10+20".split("+") -> devuelve el array ["10", "20"]
            const [num1, num2] = operacion.split(signo);
            
            // Busca la función en el objeto operaciones y la ejecuta con los números parseados
            return operaciones[signo](parseInt(num1), parseInt(num2));
        }
    }
}
// Ejemplo de uso: 
// console.log(calcular("10+5")); // Devuelve 15