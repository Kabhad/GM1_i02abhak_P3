<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%
    JugadorDTO jugador = (JugadorDTO) session.getAttribute("jugador");

    if (jugador == null || !jugador.getTipoUsuario().equalsIgnoreCase("administrador")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrador - Inicio</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <h1>Bienvenido, Administrador</h1>
        <p>Hola <strong><%= jugador.getNombreApellidos() %></strong>, has iniciado sesión como administrador.</p>
        <p>Aquí podrás gestionar usuarios y reservas.</p>
        <a href="../controller/LogoutController.jsp">Cerrar Sesión</a>
    </div>
</body>
</html>
