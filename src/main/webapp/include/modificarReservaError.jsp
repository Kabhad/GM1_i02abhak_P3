<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - Modificar Reserva</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>Error al Modificar la Reserva</h1>
        <% if (request.getAttribute("error") != null) { %>
            <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } else { %>
            <p style="color: red;">Ha ocurrido un error inesperado. Por favor, inténtalo de nuevo más tarde.</p>
        <% } %>

        <div class="action-buttons">
            <a href="<%= request.getContextPath() %>/client/modificarReserva?filtrarReservas=true" class="btn-secondary">Volver al listado de reservas</a>
            <a href="<%= request.getContextPath() %>/mvc/view/client/clientHome.jsp" class="btn-primary">Volver al Menú Principal</a>
        </div>
    </div>
</body>
</html>
