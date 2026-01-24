let cadena = prompt('Introduce datos')

let array = Array.from(cadena)
console.log(array)
let cadenaSinEspacio = array.filter((elemen) => elemen != ' ')
console.log(cadena)
console.log(cadenaSinEspacio)
let cadena2 = [...cadenaSinEspacio]
console.log('hola' + cadena2)

let sinDuplicar = new Set([...cadenaSinEspacio])
console.log(sinDuplicar)

//pasamos el conjunto a array
let cadenaSinDupli = [...sinDuplicar]

let cadenaConjunto = cadenaSinDupli.join('')
console.log(`cadena sin repetir conjunto` + cadenaConjunto)

let invirtiendiMinuscula = ''
for (let i = 0; i < cadena2.length; i++) {
  let letra = cadena2[i]

  // Regla b: Si es un espacio, lo saltamos (no lo añadimos)
  if (letra === ' ') {
    continue
  } else {
    if (letra === letra.toUpperCase()) {
      invirtiendiMinuscula += letra.toLowerCase()
    } else {
      invirtiendiMinuscula += letra.toUpperCase()
    }
  }
}
console.log(`minuscula a mayuscula: ${invirtiendiMinuscula}`)

///Ejercicio 2

let daosIntroducido = parseInt(prompt('Introduce numeros'))
console.log(daosIntroducido)

let arrayNumero = []
arrayNumero.push(daosIntroducido)
while (daosIntroducido != null) {
  daosIntroducido = prompt('Introduce numeros')
  if (daosIntroducido == null) {
  } else {
    arrayNumero.push(parseInt(daosIntroducido))
  }
}

console.log(arrayNumero)
let arrayPares = []
let num2 = 0
for (let i in arrayNumero) {
  if (arrayNumero[i] > 3 && arrayNumero[i] % 2 == 0) {
    arrayPares.push(arrayNumero[i])
  }
}

console.log(arrayPares)

let cuadrado = []
for (let i in arrayNumero) {
  cuadrado[i] = arrayNumero[i] * arrayNumero[i]
}
console.log(cuadrado)
let sumaTotal = 0
for (let suma in cuadrado) {
  sumaTotal += cuadrado[suma]
}
