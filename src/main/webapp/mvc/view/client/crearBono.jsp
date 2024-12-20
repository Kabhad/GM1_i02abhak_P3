<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Bono</title>
</head>
<body>
    <h1>Crear Bono</h1>
    <p>No tienes un bono válido para realizar esta reserva.</p>
    <p>¿Deseas crear un bono nuevo?</p>
    <form action="realizarReservaBono" method="post">
        <input type="hidden" name="idJugador" value="${param.idJugador}">
        <input type="hidden" name="idPista" value="${param.idPista}">
        <input type="hidden" name="fechaHora" value="${param.fechaHora}">
        <input type="hidden" name="duracionMinutos" value="${param.duracionMinutos}">
        <input type="hidden" name="numeroAdultos" value="${param.numeroAdultos}">
        <input type="hidden" name="numeroNinos" value="${param.numeroNinos}">
        <input type="hidden" name="crearBono" value="true">
        <button type="submit">Sí, crear un bono</button>
    </form>
    <form action="reserva.jsp" method="get">
        <button type="submit">No, volver</button>
    </form>
</body>
</html>
