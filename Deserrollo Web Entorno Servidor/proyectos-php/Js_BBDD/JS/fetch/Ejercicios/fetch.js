const boton = document.getElementById('pulsar')
boton.addEventListener('click', () => {
  // EJEMPLO FECH -0
  //Leer archivo de texto
  const contenido = document.getElementById('contenido')
  fetch('Texto_fetch2.txt')
    .then((response) => {
      if (!response.ok) {
        throw new Error('La solicitud no fue exitosa')
      }
      return response.text()
    })
    .then((data) => {
      console.log('Información del empleado:', data)
    })
    .catch((error) => {
      console.log('Error en la solicitud:', error)
    })
})
