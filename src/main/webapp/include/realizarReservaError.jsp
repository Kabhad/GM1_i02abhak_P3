<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error al Realizar Reserva</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <h1>Error al Realizar la Reserva</h1>
    <div class="error-container">
        <p>Ha ocurrido un problema mientras procesábamos tu solicitud:</p>
        <p><strong><%= request.getAttribute("error") %></strong></p>
        <p>Por favor, vuelve a intentarlo o contacta con el soporte técnico si el problema persiste.</p>
    </div>
    <br>
        <a href="../mvc/view/client/realizarReserva.jsp" class="btn-secondary">Hacer Otra Búsqueda</a>
        <a href="../mvc/view/client/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>

</body>
</html>
