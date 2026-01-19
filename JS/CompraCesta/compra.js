let compra = [
  { nombre: 'mesa', src: 'mesa.jpg', precio: 100 },
  { nombre: 'silla', src: 'silla.jpg', precio: 10 },
  { nombre: 'planta', src: 'planta.jpg', precio: 30 },
]
const body = document.querySelector('body')
const h1Titulo = document.createElement('H1')
h1Titulo.textContent = 'Cesta de la compra'
body.appendChild(h1Titulo)

// recorremos por posición que ocupa en el array
for (let articulo in compra) {
  const div = document.createElement('div')
  const img = document.createElement('img')
  img.src = compra[articulo].src
  img.width = 200

  const precio = document.createElement('label')
  precio.textContent = compra[articulo].precio + ' $'

  const botones = document.createElement('button')
  botones.value = articulo
  botones.textContent = 'Comprar'
  const saltoLinea = document.createElement('br')
  const saltoLinea2 = document.createElement('br')
  const saltoLinea3 = document.createElement('br')
  div.appendChild(img)
  div.appendChild(saltoLinea2)
  div.appendChild(precio)
  div.appendChild(saltoLinea)
  div.appendChild(botones)

  body.appendChild(div)
  body.appendChild(saltoLinea3)
}
const todosBotones = document.querySelectorAll('button')

todosBotones.forEach((miBoton) => {
  miBoton.addEventListener('click', () => {
    //Obtenemos el índice (0, 1 o 2)
    const valoBotones = miBoton.value

    // Verificamos si es un botón de compra (evitamos los botones Eliminar/Pagar)
    if (valoBotones >= 0 && valoBotones < compra.length) {
      // Creamos un ID único para buscar la fila, ej: "fila-0"
      const idFila = 'fila-' + valoBotones
      const filaExistente = document.getElementById(idFila)

      if (filaExistente) {
        // --- SI YA EXISTE: ACTUALIZAMOS ---
        // Buscamos las celdas (td) dentro de esa fila
        const celdas = filaExistente.querySelectorAll('td')

        // Leemos la cantidad actual de la primera celda y la convertimos a número
        let cantidadActual = parseInt(celdas[0].textContent)
        cantidadActual = cantidadActual + 1

        // Escribimos los nuevos valores
        celdas[0].textContent = cantidadActual
        celdas[3].textContent = compra[valoBotones].precio * cantidadActual
      } else {
        // --- SI NO EXISTE: CREAMOS LA FILA ---
        const trTabla = document.createElement('tr')
        trTabla.id = idFila // ¡Importante! Le ponemos el ID para encontrarla luego

        const tdUni = document.createElement('td')
        const tdArt = document.createElement('td')
        const tdPrecio = document.createElement('td')
        const tdTotal = document.createElement('td')

        tdUni.textContent = 1
        tdArt.textContent = compra[valoBotones].nombre
        tdPrecio.textContent = compra[valoBotones].precio
        tdTotal.textContent = compra[valoBotones].precio // 1 * precio

        tablaUnidades.appendChild(trTabla)
        trTabla.appendChild(tdUni)
        trTabla.appendChild(tdArt)
        trTabla.appendChild(tdPrecio)
        trTabla.appendChild(tdTotal)
      }
    }
  })
})

//creamos la acciones de los botones

const h1Titulo2 = document.createElement('H1')
h1Titulo2.textContent = 'Carrito de compras'
body.appendChild(h1Titulo2)

const tablaUnidades = document.createElement('table')
const trTabla = document.createElement('tr')
const tdUni = document.createElement('td')
const tdArt = document.createElement('td')
const tdPrecio = document.createElement('td')
const tdTotal = document.createElement('td')

tdUni.textContent = 'unidades'
tdArt.textContent = 'Articulo'
tdPrecio.textContent = 'precio'
tdTotal.textContent = 'total'

tablaUnidades.appendChild(trTabla)
trTabla.appendChild(tdUni)
trTabla.appendChild(tdArt)
trTabla.appendChild(tdPrecio)
trTabla.appendChild(tdTotal)

body.appendChild(tablaUnidades)
const espacio = document.createElement('br')
body.appendChild(espacio)
const btnEliminar = document.createElement('button')
btnEliminar.value = 5
btnEliminar.textContent = 'Elimnar'
body.appendChild(btnEliminar)
const btnPagar = document.createElement('button')
btnPagar.value = 6
btnPagar.textContent = 'Pagar'
body.appendChild(btnPagar)

// Obtener todos los botones de eliminar del carrito
const botonesBorrar = document.querySelectorAll('button')
botonesBorrar.forEach((boton) => {
  if (boton.value == 5) {
    boton.addEventListener('click', (e) => {
      // Obtener el id del producto a eliminar desde el atributo data-id del botón
      const filasProductos = tablaUnidades.querySelectorAll('tr[id^="fila-"]')
      filasProductos.forEach((fila) => fila.remove())
    })
  }
})
