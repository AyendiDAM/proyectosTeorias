<%-- 
 Formulario para dar de alta candidato
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alta de alumnos</title>
</head>
<body bgcolor="cyan">
    <center>
     <form action="../GestionCandidatoServlet">
            <h1>Dar de alta partidos politicos</h1>

            <label for="nombre">Nombre del partido politico:</label><br>
            <input type="text" id="nombre" name="nombre" placeholder="Ingrese su nombre" required><br><br>

            <label for="apellidos">apellidos</label><br>
            <input type="text" id="apellidos" name="apellidos" placeholder="Ingrese los apellidos" required><br><br>
            
               <label for="numeroLista">Numero lista</label><br>
               <input type="number" id="numLista" name="numLista" placeholder="numLista" required><br><br>
               
               <label for="idPartido">idPartido</label><br>
               <input type="number" id="idPartido" name="idPartido" placeholder="idPartido" required><br><br>
            
        


            <input type="submit" name="partidoAlta" value="Dar de alta partido">
        </form>
    </center>
</body>
</html>