<%-- 
 Formulario para Modificar el estado de las elecciones
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
     <form action="../GestionModificarEleccionServlet">
            <h1>Dar de alta partidos politicos</h1>

            <label for="nombre">Nombre de la eleccion</label><br>
            <input type="text" id="nombre" name="nombre" placeholder="Ingrese su nombre" required><br><br>
            
                 <label for="nombreNuevo">nombreNuevo</label><br>
            <input type="text" id="nombreNuevo" name="nombreNuevo" placeholder="Ingrese su nombre" required><br><br>

           
            
        


            <input type="submit" name="partidoAlta" value="Dar de alta partido">
        </form>
    </center>
</body>
</html>