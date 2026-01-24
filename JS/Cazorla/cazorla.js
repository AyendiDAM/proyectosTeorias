let botonesSession = [
  { propiedad: 'p', src: 'mesa.jpg', precio: 100 },
  { propiedad: 'h5', segundaP: 'p', tercerP: 'h5', cuertaP: 'p', quintaP: 'h5', sextaP: 'p' },
  { nombre: 'planta', src: 'planta.jpg', precio: 30 },
]

const todosBotones = document.querySelectorAll('button')

todosBotones.forEach((miBoton) => {
  miBoton.addEventListener('click', () => {
    let main = document.querySelector('#main')
    if (miBoton.value == 1) {
      main.innerHTML = '<strong>Importante</strong>' // Se lee "Importante" (en negrita)
    }
  })
})
