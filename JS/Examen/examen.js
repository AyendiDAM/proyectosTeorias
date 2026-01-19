/*
Se dispondrá de una web, con el siguiente formulario, y la funcionalidad detallada a continuación:
 
1.	Botón Agregar: se pulsará cuando se hayan introducido los datos de un usuario en las cajas de
 texto, y es es instante:
1.1.	(1,25 puntos) Se comprobará que el dni introducido tiene el formato de nueve dígitos
 seguidos de un guión y una letra. En caso contrario, 
se informará de un error en el dni mediante un alert, y se vaciará la caja dni.
1.2.	En caso de que el formato del dni sea correcto: se tendrá un array dnis,
 donde se irá guardando cada dni  introducido en la caja de texto, 
siempre que no esté ya introducido:
1.2.1.	 (0,5 puntos) Si el dni ya estaba introducido en el array dnis: avisará de ello con un alert,
 y vaciará las cajas del formulario. 
1.2.2.	Si no está el dni: 
 )	(0,25 puntos) Insertará el dni en el array dnis y mostrará por consola (console.log) 
    el array formado.
 )	(1 punto) A partir del array anterior, obtendrá un nuevo array modificando el anterior, 
    formado por cada uno de los dnis sin guión (dígitos seguidos de letra).
     Se valorará la eficiencia en la solución. Mostrar por consola el array resultante.
 )	(1 punto) Insertará la edad en un array edades. A partir del array dnis y 
    el array edades se obtendrá un nuevo array llamado mixto que contenga los datos 
    de los dos arrays anteriores sin duplicados. Se valorará la eficiencia en la solución.
 )	(1,5 puntos) A partir de los datos introducidos en los inputs, se creará un div,
     como se ve en la siguiente imagen, con los datos de ese usuario.
     A ese div se le asignará la clase caja-resaltada, pasada en el examen. Además, 
    si el usuario introducido es menor de edad, se pondrá el fondo del div a rojo. 
    Al crear el div se vaciarán los inputs.

2.	(1,5 puntos) Botón Ocultar/Mostrar: permitirá seleccionar un div insertado de forma aleatoria, 
y alternar su visibilidad (ocultarlo si está visible y viceversa)
3.	(1,5 puntos) Botón Desmarcar: quitará el fondo a todas los divs creados.
4.	(1,5 puntos) Cuando se hace click sobre un div creado, se eliminará.


Notas importantes:
•	El método cadena.charCodeAt(0) devuelve el código ASCII del primer carácter de la variable cadena.
 Los códigos ascii de las letras A a la Z van del 65 al 90.
•	No se puede utilizar Internet y por tanto ChatGPT o cualquier otra inteligencia artificial.
 Con el código escrito en tu proyecto,
 tienes toda la información para poder añadir las nuevas funcionalidades.
*/
let btnAgregar=document.querySelector("#")
