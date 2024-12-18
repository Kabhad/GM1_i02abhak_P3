<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error al Cancelar Reserva</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cancelarReservaError.css">
</head>
<body>
    <h1>Error al Cancelar Reserva</h1>

    <!-- Mostrar mensaje de error -->
    <c:if test="${not empty error}">
        <p style="color: red; font-weight: bold;">${error}</p>
    </c:if>

    <!-- Mensaje si no se proporciona un mensaje de error -->
    <c:if test="${empty error}">
        <p>Ocurrió un error al intentar cancelar la reserva. Por favor, inténtalo de nuevo más tarde.</p>
    </c:if>

    <!-- Botón para regresar -->
    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/mvc/view/clientHome.jsp">Volver al Menú Principal</a>
    </div>
</body>
</html>
