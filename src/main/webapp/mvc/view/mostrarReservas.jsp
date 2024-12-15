<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reservas Finalizadas y Futuras</title>
    <!-- Enlace al CSS separado -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mostrarReservas.css">
</head>
<body>
    <h2>Reservas Finalizadas</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Duración</th>
            <th>Pista</th>
            <th>Precio</th>
        </tr>
        <%
            List<ReservaBean> reservasFinalizadas = (List<ReservaBean>) request.getAttribute("reservasFinalizadas");
            if (reservasFinalizadas != null && !reservasFinalizadas.isEmpty()) {
                for (ReservaBean reserva : reservasFinalizadas) {
        %>
            <tr>
                <td><%= reserva.getIdReserva() %></td>
                <td><%= reserva.getFechaHora() %></td>
                <td><%= reserva.getDuracionMinutos() %> minutos</td>
                <td><%= reserva.getIdPista() %></td>
                <td><%= reserva.getPrecio() %> €</td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="5" class="no-reservas">No hay reservas finalizadas en este rango de fechas.</td>
            </tr>
        <%
            }
        %>
    </table>

    <h2>Reservas Futuras</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Duración</th>
            <th>Pista</th>
            <th>Precio</th>
        </tr>
        <%
            List<ReservaBean> reservasFuturas = (List<ReservaBean>) request.getAttribute("reservasFuturas");
            if (reservasFuturas != null && !reservasFuturas.isEmpty()) {
                for (ReservaBean reserva : reservasFuturas) {
        %>
            <tr>
                <td><%= reserva.getIdReserva() %></td>
                <td><%= reserva.getFechaHora() %></td>
                <td><%= reserva.getDuracionMinutos() %> minutos</td>
                <td><%= reserva.getIdPista() %></td>
                <td><%= reserva.getPrecio() %> €</td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="5" class="no-reservas">No hay reservas futuras en este rango de fechas.</td>
            </tr>
        <%
            }
        %>
    </table>

	
	<!-- Botones de navegación -->
	<div class="button-container">
	    <a href="../mvc/view/consultarReservas.jsp" class="nav-button">Hacer Otra Búsqueda</a>
	    <a href="../mvc/view/clientHome.jsp" class="nav-button">Volver al Menú Principal</a>
	</div>


</body>
</html>
