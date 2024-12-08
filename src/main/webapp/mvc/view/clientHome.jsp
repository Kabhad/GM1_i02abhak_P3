<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%
    JugadorDTO jugador = (JugadorDTO) session.getAttribute("jugador");

    if (jugador == null || !jugador.getTipoUsuario().equalsIgnoreCase("cliente")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cliente - Inicio</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <h1>Bienvenido, Cliente</h1>
        <p>Hola <strong><%= jugador.getNombreApellidos() %></strong>, bienvenido a tu área personal.</p>
        <p>Aquí podrás realizar reservas y ver tu historial.</p>
        <a href="../controller/LogoutController.jsp">Cerrar Sesión</a>
    </div>
</body>
</html>
