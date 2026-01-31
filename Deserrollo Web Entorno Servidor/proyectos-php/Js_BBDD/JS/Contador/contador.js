let contadores = [
  { nombre: 'Contador', valor: 0 },
  { nombre: 'Contador', valor: 0 },
]
const body = document.querySelector('body')
for (let contador in contadores) {
  const div = document.createElement('div')
  const botones = document.createElement('button')
  botones.value = contador + ''
  botones.textContent = contadores[contador].nombre
  console.log(`valor contador ${contador}`)
  const saltoLinea = document.createElement('br')
  div.appendChild(botones)
  div.appendChild(saltoLinea)
  div.appendChild(saltoLinea)
  body.appendChild(div)
  botones.addEventListener('click', () => {
    contadores[contador].valor++
    botones.textContent = 'Contador: ' + contadores[contador].valor
  })
}
