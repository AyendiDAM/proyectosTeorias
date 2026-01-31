let divBotonesAprender = document.querySelectorAll('#aprender button')
let parrafoAprender = document.querySelector('#aprender p')
let divAprender = document.querySelector('#aprender')
let comprobar = document.querySelector('#comprobar')

divBotonesAprender.forEach((miBoton) => {
  miBoton.addEventListener('click', () => {
    parrafoAprender.innerHTML = ''
    let j = miBoton.value
    for (let i = 0; i <= 12; i++) {
      const tabla = document.createElement('p')
      let multiplicar = i * j
      tabla.innerHTML = j + ' x ' + i + '= <strong>' + multiplicar + '</strong>'
      console.log(tabla)
      parrafoAprender.appendChild(tabla)
    }
  })
})

let divBotonesRepasar = document.querySelectorAll('#repasar button')
let parrafoRepasar = document.querySelector('#repasar p')
let divRepasar = document.querySelector('#repasar')

divBotonesRepasar.forEach((miBoton) => {
  miBoton.addEventListener('click', () => {
    parrafoRepasar.innerHTML = ''
    let fallos = 0
    let j = miBoton.value
    for (let i = 0; i <= 12; i++) {
      const contenedorFila = document.createElement('div')
      const tabla = document.createElement('p')

      let multiplicar = i * j
      tabla.innerHTML = j + ' x ' + i
      const resultadoSegunUsuario = document.createElement('input')
      resultadoSegunUsuario.type = 'number'
      resultadoSegunUsuario.setAttribute('data-numeroSolucion', multiplicar) //obtengo el resultado y creo el atributo como quiero
      contenedorFila.appendChild(tabla)
      contenedorFila.appendChild(resultadoSegunUsuario)
      parrafoRepasar.appendChild(contenedorFila)
    }
    comprobar.hidden = null
  })
})

comprobar.addEventListener('click', () => {
  let fallos = 0
  let todosImput = document.querySelectorAll('input')
  todosImput.forEach((input) => {
    if (parseInt(input.value) !== parseInt(input.getAttribute('data-numeroSolucion'))) {
      fallos++
      input.style.borderColor = 'red'
    } else {
      input.style.borderColor = 'green'
    }
  })

  alert(`Tienes ${fallos} fallos.`)
})
