<%-- 
 Formulario para dar de alta elecciones
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
     <form action="../GestionEleccionServlet">
            <h1>Dar de alta partidos politicos</h1>

            <label for="nombre">Nombre del partido politico:</label><br>
            <input type="text" id="nombre" name="nombre" placeholder="Ingrese su nombre" required><br><br>

            <label for="fechaInicio">fechaInicio</label><br>
            <input type="date" id="fechaInicio" name="fechaInicio" placeholder="Ingrese los apellidos" required><br><br>
            
                <label for="fechaFinal">fechaFinal</label><br>
            <input type="date" id="fechaFinal" name="fechaFinal" placeholder="Ingrese los apellidos" required><br><br>
               
                <label for="estado">Estado Eleccion:</label><br>
            <input type="text" id="estado" name="estado" placeholder="Ingrese su nombre" required><br><br>
            
        


            <input type="submit" name="partidoAlta" value="Dar de alta partido">
        </form>
    </center>
</body>
</html>