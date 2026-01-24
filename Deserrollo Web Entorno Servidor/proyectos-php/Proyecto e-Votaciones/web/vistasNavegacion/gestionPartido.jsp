<%-- 
 Formulario para dar de alta partidos políticos
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
     <form action="../GestionPartidoAnadirServlet">
            <h1>Dar de alta partidos politicos</h1>

            <label for="nombre">Nombre del partido politico:</label><br>
            <input type="text" id="nombre" name="nombre" placeholder="Ingrese su nombre" required><br><br>

            <label for="siglas">Siglas</label><br>
            
            
            <input type="text" id="siglas" name="siglas" placeholder="La siglas del partifo" required><br><br>
            <select id="localidad" name="partidoSeleccionado" required>
                <option value="C:/Users/ayend/Desktop/ProgramasJava/Proyecto e-Votaciones/web/estilos/VozPartido.jpg">Vox</option>
                <option value="C:\Users\ayend\Desktop\ProgramasJava\Proyecto e-Votaciones\web\estilos\logoPartidoPsoe.png">Psoe</option>
            </select><br><br>



            <input type="submit" name="partidoAlta" value="Dar de alta partido">
        </form>
    </center>
</body>
</html>