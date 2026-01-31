const dni = '21232'
fetch(`buscar_empleado.php?dni=${dni}`)
  .then((response) => {
    if (!response.ok) {
      throw new Error('La solicitud no fue exitosa')
    }
    return response.json()
  })
  .then((data) => {
    console.log('Información del empleado:', data)
  })
  .catch((error) => {
    console.log('Error en la solicitud:', error)
  })
