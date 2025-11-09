// Loops o bucles

// for

for (let i = 0; i < 5; i++) {
    console.log(`Hola ${i}`)
}

const numbers = [1, 2, 3, 4, 5, 6, 7, 8]

for (let i = 0; i < numbers.length; i++) {
    console.log(`Elemento: ${numbers[i]}`)
}

// while (puede que no se ejecute ninguna vez)

let i = 0
while (i < 5) {
    console.log(`Hola ${i}`)
    i++
}

// Bucle infinito
// while(true) {
// }

// do while (nos aseguramos que se ejecute al menos una vez)

i = 6
do {
    console.log(`Hola ${i}`)
    i++
} while (i < 5)

// for of (sirve para recorrer un elemento iterable (estructura de datos, arrays, maps, sets))

const myArray = [1, 2, 3, 4]

const mySet = new Set(["Brais", "Moure", "mouredev", 37, true, "braismoure@mouredev.com"])

const myMap = new Map([
    ["name", "Brais"],
    ["email", "braismoure@mouredev.com"],
    ["age", 37]
])

const myString = "¡Hola, JavaScript!"

for (let value of myArray) {
    console.log(value)
}

for (let value of mySet) {
    console.log(value)
}

for (let value of myMap) {
    console.log(value)
}

for (let value of myString) {
    console.log(value)
}

// break y continue

for (let i = 0; i < 10; i++) {
    if (i == 5) {
        continue // nos saltamos el imprimir 5
    } else if (i == 7) {
        break // pero si llega al 7 salimos del bucle
    }
    console.log(`Hola ${i}`)
}