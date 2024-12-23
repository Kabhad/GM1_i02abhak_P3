<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error al Cancelar Reserva</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <h1>Error al Cancelar Reserva</h1>

    <!-- Mostrar el mensaje de error si est� presente -->
    <c:if test="${not empty error}">
        <p style="color: red; font-weight: bold;">${error}</p>
    </c:if>

    <!-- Mostrar un mensaje gen�rico si no se proporciona un mensaje de error -->
    <c:if test="${empty error}">
        <p>Ocurri� un error al intentar cancelar la reserva. Por favor, int�ntalo de nuevo m�s tarde.</p>
    </c:if>

    <!-- Bot�n para regresar al men� principal -->
    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/mvc/view/client/clientHome.jsp">Volver al Men� Principal</a>
    </div>
</body>
</html>
