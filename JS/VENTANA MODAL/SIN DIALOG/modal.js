// Obtener los elementos
var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var span = document.getElementsByClassName("close")[0];

// Cuando se hace clic en el botón, se muestra el modal
btn.addEventListener('click',function() {
  modal.style.display = "block";
})

// Cuando se hace clic en la "x", se cierra el modal
span.addEventListener('click',function() {
  modal.style.display = "none";
})

