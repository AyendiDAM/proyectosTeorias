const boton = document.getElementById('pulsar')
boton.addEventListener('click', () => {
  // EJEMPLO FECH -0
  //Leer archivo de texto
  fetch('https://jsonplaceholder.typicode.com/posts/1')
    .then((response) => {
      if (!response.ok) {
        throw new Error('Respuesta de red Ok pero respuesta http no ok')
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
