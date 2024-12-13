<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Reservas</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <h1>Reservas del Usuario</h1>

    <h2>Reservas Futuras</h2>
    <c:if test="${empty reservasFuturas}">
        <p>No tienes reservas futuras.</p>
    </c:if>
    <table>
        <thead>
            <tr>
                <th>ID Reserva</th>
                <th>Fecha</th>
                <th>Duración</th>
                <th>Precio</th>
                <th>Descuento</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="reserva" items="${reservasFuturas}">
                <tr>
                    <td>${reserva.idReserva}</td>
                    <td>${reserva.fechaHora}</td>
                    <td>${reserva.duracionMinutos}</td>
                    <td>${reserva.precio}</td>
                    <td>${reserva.descuento}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h2>Reservas Finalizadas</h2>
    <c:if test="${empty reservasFinalizadas}">
        <p>No tienes reservas finalizadas.</p>
    </c:if>
    <table>
        <thead>
            <tr>
                <th>ID Reserva</th>
                <th>Fecha</th>
                <th>Duración</th>
                <th>Precio</th>
                <th>Descuento</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="reserva" items="${reservasFinalizadas}">
                <tr>
                    <td>${reserva.idReserva}</td>
                    <td>${reserva.fechaHora}</td>
                    <td>${reserva.duracionMinutos}</td>
                    <td>${reserva.precio}</td>
                    <td>${reserva.descuento}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
