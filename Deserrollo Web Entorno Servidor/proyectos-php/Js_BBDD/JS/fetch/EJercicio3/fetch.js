let dni = document.getElementById('dni').value

let datosEmpleado = { dni: dni }

const requestOptions = {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(datosEmpleado), // Enviar el DNI como JSON en el cuerpo de la solicitud
}
fetch('buscar_empleado.php', requestOptions)
  .then((response) => {
    if (!response.ok) {
      throw new Error('La solicitud no fue exitosa')
    }
    return response.json() ///OJO!! JSON.parse(response) no funcionaría, ya que no genera una nueva
    promesa
  })
  .then((data) => {
    if (data.mensaje) {
      console.log(data.mensaje)
    } else {
      console.log('Nombre del empleado:', data.Nombre)
    }
  })
  .catch((error) => {
    console.log('Error en la solicitud:', error)
  })
