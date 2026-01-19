let list = document.querySelector('.todo-list')
list.addEventListener('click', (e) => {
  console.log(e.target)
  console.log(e.currentTarget)
})
