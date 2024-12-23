<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error al Realizar Reserva</title>
    <!-- Vincula la hoja de estilos CSS específica para los errores -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <!-- Título principal de la página -->
    <h1>Error al Realizar la Reserva</h1>

    <!-- Contenedor principal para mostrar el mensaje de error -->
    <div class="error-container">
        <!-- Mensaje estático indicando que ocurrió un problema -->
        <p>Ha ocurrido un problema mientras procesábamos tu solicitud:</p>
        <!-- Muestra el detalle del error capturado desde el servlet -->
        <p><strong><%= request.getAttribute("error") %></strong></p>
        <!-- Mensaje estático indicando al usuario que puede intentar de nuevo o contactar soporte -->
        <p>Por favor, vuelve a intentarlo o contacta con el soporte técnico si el problema persiste.</p>
    </div>

    <br>
    <!-- Enlaces para que el usuario pueda navegar a otras opciones -->
    <!-- Enlace para realizar otra búsqueda de reservas -->
    <a href="../mvc/view/client/realizarReserva.jsp" class="btn-secondary">Hacer Otra Búsqueda</a>
    <!-- Enlace para volver al menú principal -->
    <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
</body>
</html>
