const inputTarea = document.getElementById('tarea')
const btnAgregarInput = document.querySelector('.btn')
let lista = document.createElement('ul')
btnAgregarInput.addEventListener('click', () => {
  const textoTarea = inputTarea.value
  let dato = document.querySelector('#listafav')

  // Validamos que no esté vacío
  if (textoTarea === '') {
    alert('Por favor, escribe una tarea.')
    return
  }

  // Crear Li y agregar
  const li = document.createElement('li')
  li.textContent = textoTarea

  //ejercicio4

  //evento eliminar

  li.addEventListener('click', () => {
    let dato = document.querySelector('#listaodi')

    dato.appendChild(li)
    li.remove()
  })

  dato.appendChild(li)

  // Limpiar el input
  inputTarea.value = ''
})

// Ejercicio 5 Corregido

const marcarNum = document.getElementById('completada')
// Seleccionamos el contenedor padre (asumiendo que es un UL o OL)
const listaOdi = document.getElementById('listaodi')
const btnMarcar = document.querySelector('.btn2')

btnMarcar.addEventListener('click', () => {
  // 1. Obtenemos el número del input
  const marcoNum = parseInt(marcarNum.value)

  // 2. Comprobamos si el número es válido (para evitar errores)
  if (isNaN(marcoNum) || marcoNum <= 0) {
    alert('Por favor, introduce un número válido.')
    return
  }

  // 3. Seleccionamos DIRECTAMENTE el elemento específico
  // Usamos template literals `${}` para insertar el número en el selector CSS
  const elemento = document.querySelector(`#listaodi li:nth-child(${marcoNum})`)

  // 4. Si el elemento existe, le cambiamos el color
  if (elemento) {
    elemento.style.backgroundColor = 'red'
  } else {
    alert('No existe un elemento con ese número en la lista.')
  }
})
