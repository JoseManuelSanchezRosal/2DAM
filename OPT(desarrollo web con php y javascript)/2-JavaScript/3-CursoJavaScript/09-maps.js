// Map (o diccionario
// (Clave , Valor)

// Declaración

let myMap = new Map()

console.log(myMap)

// Inicialiación

myMap = new Map([
    ["name", "Brais"],
    ["email", "braismoure@mouredev.com"],
    ["age", 37]
])

console.log(myMap)

// Métodos y propiedades

// set (vale para agregar si no existe la clave, si la clave existe la modifica)

myMap.set("alias", "mouredev")
myMap.set("name", "Brais Moure")

console.log(myMap)

// get

console.log(myMap.get("name"))
console.log(myMap.get("surname"))

// has

console.log(myMap.has("surname"))
console.log(myMap.has("age"))

// delete

myMap.delete("email")

console.log(myMap)

// keys, values y entries

console.log(myMap.keys()) // Devuelve las claves
console.log(myMap.values())  // Devuelve los valores
console.log(myMap.entries())  // Todas las claves y valores del mapa

// size

console.log(myMap.size)

// clear

myMap.clear()

console.log(myMap)