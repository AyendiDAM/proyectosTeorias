class navegarBotones {
  titulo = 'Granada'
  destino = 'Londres'
  dias = 80
  precio = 500
  constructor(or, des, di, pre) {
    this.origen = or
    this.destino = des
    this.dias = di
    this.precio = pre
  }

  mostrar() {
    console.log(`${this.origen} a ${this.destino}`)
    console.log(this.precio)
  }
}

const todosBotones = document.querySelectorAll('button')

todosBotones.forEach((miBoton) => {
  miBoton.addEventListener('click', () => {
    let main = document.querySelector('#main')
    if (miBoton.value == 1) {
      main.innerHTML =
        '<p><a href="https://es.wikipedia.org/wiki/Cazorla">Cazorla es un municipio y localidad española</a> situado en la provincia de Jaén</a>, en la comunidad autónoma de Andalucía. Es conocido por su hermoso entorno natural, formando parte del Parque Natural de las Sierras de Cazorla, Segura y Las Villas, el mayor espacio protegido de España y declarado Reserva de la Biosfera por la UNESCO. La ciudad tiene una población de aproximadamente 6,930 habitantes y es famosa por su castillo de la Yedra, que domina la urdimbre urbana y ofrece vistas panorámicas impresionantes. Cazorla también es un destino turístico popular, destacándose por su cultura, gastronomía y actividades al aire libre en el</p>' // Se lee "Importante" (en negrita)
    }

    if (miBoton.value == 2) {
      main.innerHTML =
        '<p><a href="https://es.wikipedia.org/wiki/Cazorla">Cazorla es un municipio y localidad española</a> situado en la provincia de Jaén</a>, en la comunidad autónoma de Andalucía. Es conocido por su hermoso entorno natural, formando parte del Parque Natural de las Sierras de Cazorla, Segura y Las Villas, el mayor espacio protegido de España y declarado Reserva de la Biosfera por la UNESCO. La ciudad tiene una población de aproximadamente 6,930 habitantes y es famosa por su castillo de la Yedra, que domina la urdimbre urbana y ofrece vistas panorámicas impresionantes. Cazorla también es un destino turístico popular, destacándose por su cultura, gastronomía y actividades al aire libre en el</p>' // Se lee "Importante" (en negrita)
    }
  })
})
