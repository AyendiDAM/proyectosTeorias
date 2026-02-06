<%-- 
    Document   : salirAplicacion
    Created on : 2 dic 2025, 8:51:59
    Author     : ayend
--%>
<%--recogemos las cookies--%>
<%!String fulanito;%>
<%!Cookie[] cookies;%>


<% cookies=request.getCookies();
for(int i=0;i<cookies.length;i++){
    Cookie una=cookies[i];
    if(una.getName().equalsIgnoreCase("nombre_despedida")){
        fulanito=una.getValue();
        una.setMaxAge(-60*3);
    }
 
}


%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ejemplo cookies</title>
    </head>
    <body bgcolor="cyan">
    <center>
        <h1>Gracias por su voto <%=fulanito%>, hasta la pr&oacute;xima</h1>
        </center>
    </body>
</html>
