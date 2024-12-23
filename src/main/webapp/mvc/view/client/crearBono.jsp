<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Bono</title>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/crearBono.css">
</head>
<body>
    <h1>Crear Bono</h1>
    <!-- Mensaje informando que no existe un bono válido -->
    <p>No tienes un bono válido para realizar esta reserva.</p>
    <p>¿Deseas crear un bono nuevo?</p>
    
    <!-- Formulario para crear un bono -->
    <form action="${pageContext.request.contextPath}/client/realizarReservaBono" method="post">
        <!-- ID del jugador enviado de manera oculta -->
        <input type="hidden" name="idJugador" value="${param.idJugador}">
        <!-- Indicador de que se desea crear un bono -->
        <input type="hidden" name="crearBono" value="true">
        <!-- Botón para confirmar la creación del bono -->
        <button type="submit">Sí, crear un bono</button>
    </form>
    
    <!-- Botón para volver al menú principal -->
    <a href="${pageContext.request.contextPath}/mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
</body>
</html>
