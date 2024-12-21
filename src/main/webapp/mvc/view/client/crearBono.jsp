<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Bono</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/crearBono.css">
</head>
<body>
    <h1>Crear Bono</h1>
    <p>No tienes un bono válido para realizar esta reserva.</p>
    <p>¿Deseas crear un bono nuevo?</p>
    <form action="${pageContext.request.contextPath}/client/realizarReservaBono" method="post">
        <input type="hidden" name="idJugador" value="${param.idJugador}">
        <input type="hidden" name="crearBono" value="true">
        <button type="submit">Sí, crear un bono</button>
    </form>
    <!-- Botón para volver al menú principal -->
    <a href="${pageContext.request.contextPath}/mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
</body>
</html>
