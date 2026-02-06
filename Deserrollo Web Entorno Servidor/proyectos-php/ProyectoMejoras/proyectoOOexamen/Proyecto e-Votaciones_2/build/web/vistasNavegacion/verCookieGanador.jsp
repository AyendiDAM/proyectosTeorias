<%-- 
    Document   : verCookieGanador
    Created on : 2 dic 2025, 12:34:50
    Author     : ayend
--%><%@page import="java.net.URLDecoder"%>

//estado eleccion cookie
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%--recogemos las cookies--%>
<%!String nombre,nombCodificado;%>
<%!Cookie[] cookies;%>
<% cookies=request.getCookies();
for(int i=0;i<cookies.length;i++){
    Cookie una=cookies[i];
    if(una.getName().equalsIgnoreCase("nombreGanador")){
        nombre=una.getValue();
      nombCodificado=URLDecoder.decode(nombre, "UTF-8");
    }

}

    String accioneAdmin = (String) session.getAttribute("PartidoCreado");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ejemplo cookies</title>
    </head>
    <body bgcolor="cyan">
    <center>
        <h1>Partido ganador cookie<%=nombCodificado%></h1>
        </center>
    </body>
</html>
