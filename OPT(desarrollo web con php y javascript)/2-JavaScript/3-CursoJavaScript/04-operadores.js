// Operadores

// Operadores aritmeticos
let a = 5
let b = 10
console.log(a + b)
console.log(a - b)
console.log(a * b)
console.log(a / b)

console.log(a % b) // el modulo es el resto de la division
console.log(a ** b) // calculamos el exponente

a++
console.log(a)
b--
console.log(b)

// Operadores de asignacion
let myVariable = 2

myVariable += 2
myVariable -= 2
myVariable *= 2
myVariable /= 2
myVariable **2

// Operadores de comparacion
console.log(a > b)
console.log(a < b)
console.log(a >= b)
console.log(a <= b)
console.log(a == b) // Comparar igualdad por valor
console.log(a === 6) // Comparar igualdad por identidad (valor y tipo)
console.log(a != 6) 


// Trythy values (valores verdaderos)

// Todos los numeros positivos y negativos menos el 0
// Todas las cadenas de texto menos las vacias
// El boolean true

// Falsy values (valores falsos)

// 0
// 0n
// null
// undefined
// NaN
// El boolean false
// Cadenas de texto vacias


// Operadores logicos

// and (&&)
console.log(5 > 10 && 15 > 20)
console.log(5 < 10 && 15 < 20)

// or (||)
console.log(5 > 10 || 20 > 15)

// not (!)
console.log(!(5 > 10 && 15 > 20))


// Operadores ternarios

const isRaining = false
isRaining ? console.log("Esta lloviendo") : console.log("No esta lloviendo")