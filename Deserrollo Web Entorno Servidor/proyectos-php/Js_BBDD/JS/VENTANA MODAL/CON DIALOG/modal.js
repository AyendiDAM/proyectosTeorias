//La diferencia entre .show() y .showModal() es que el primero, al estar abierta la ventana modal, nos va a dejar seguir interactuando con el resto de la web, y la segunda va a bloquear todo el contenido que no sea el que esté dentro de la ventana modal
let mod=document.getElementById("modal");
let b=document.getElementById("boton");
b.addEventListener("click",()=>{ 
  mod.showModal();
}
);

