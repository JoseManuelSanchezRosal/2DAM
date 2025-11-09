// HAY 7 TIPOS DE DATOS PRIMITIVOS

// 1 String (cadena de texto)
let myName = "Jose Manuel"
let alias = 'megatroll'
let email = `josematroll@gmail.com`

// 2 Number (numeros)
let myAge = 39 // Entero
let myHeight = 1.85 // Decimal

// 3 Boolean (booleanos)
let isStudent = true
let isTeacher = false


// 4 Undefined (indefinida, las hemos declarado pero no la hemos inicializado)
let undefined
// 5 Null (variable definida pero con valor nulo)
let nullValue = null // Objeto tipo null

// 6 Symbol (simbolos, valores unicos, inmutables, identificadores de propiedades)
let mySymbol  = Symbol("mysymbol")

// 7 BigInt (Numeros enteros grandes)
let myBigint = BigInt(786986986987987869689687070987)
let myBigint2 = 7845608957607560956709678309856n

// Mostramos los tipos de datos
console.log(typeof myName)
console.log(typeof myAge)
console.log(typeof isStudent)
console.log(typeof undefined)
console.log(typeof nullValue)
console.log(typeof mySymbol)
console.log(typeof myBigint)
