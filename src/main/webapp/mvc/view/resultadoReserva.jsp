<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reserva Realizada</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/resultadoReserva.css">
</head>
<body>
    <h1>Reserva Realizada</h1>
    <%
        ReservaBean reserva = (ReservaBean) request.getAttribute("reserva");
        if (reserva != null) {
    %>
        <div class="resumen">
            <p><strong>ID de Reserva:</strong> <%= reserva.getIdReserva() %></p>
            <p><strong>Fecha y Hora:</strong> <%= reserva.getFechaHora() %></p>
            <p><strong>Duración:</strong> <%= reserva.getDuracionMinutos() %> minutos</p>
            <p><strong>Pista:</strong> Pista <%= reserva.getIdPista() %></p>
            <p><strong>Adultos:</strong> <%= reserva.getNumeroAdultos() %></p>
            <p><strong>Niños:</strong> <%= reserva.getNumeroNinos() %></p>
            <p><strong>Precio:</strong> <%= String.format("%.2f", reserva.getPrecio()) %> €</p>
            <p><strong>Descuento Aplicado:</strong> <%= (int)(reserva.getDescuento() * 100) %> %</p>
        </div>
    <%
        } else {
    %>
        <p>Ocurrió un error al procesar tu reserva. Inténtalo nuevamente.</p>
    <%
        }
    %>
    <a href="${pageContext.request.contextPath}/client/realizarReserva" class="btn-secondary">Nueva Reserva</a>
    <a href="${pageContext.request.contextPath}/client/home" class="btn-secondary">Menú Principal</a>
</body>
</html>
