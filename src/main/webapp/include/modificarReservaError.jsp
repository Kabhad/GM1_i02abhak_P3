<!DOCTYPE html>
<html>
<head>
    <title>Error - Modificar Reserva</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>Error al Modificar la Reserva</h1>
        <!-- Mostrar el mensaje de error si est� disponible -->
        <% if (request.getAttribute("error") != null) { %>
            <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } else { %>
            <!-- Mensaje gen�rico en caso de error inesperado -->
            <p style="color: red;">Ha ocurrido un error inesperado. Por favor, int�ntalo de nuevo m�s tarde.</p>
        <% } %>

        <!-- Botones para volver al listado de reservas o al men� principal -->
        <div class="action-buttons">
            <a href="<%= request.getContextPath() %>/client/modificarReserva?filtrarReservas=true" class="btn-secondary">Volver al listado de reservas</a>
            <a href="<%= request.getContextPath() %>/mvc/view/client/clientHome.jsp" class="btn-primary">Volver al Men� Principal</a>
        </div>
    </div>
</body>
</html>
