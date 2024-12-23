<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cancelar Reservas</title>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cancelarReserva.css">
    <!-- Enlace al script de validación -->
    <script src="<%= request.getContextPath() %>/js/cancelarReservaValidation.js" defer></script>
</head>
<body>
    <h1>Cancelar Reservas Futuras</h1>
    
    <!-- Botón para volver al inicio -->
    <div class="button-container">
	    <form action="<%= request.getContextPath() %>/mvc/view/client/clientHome.jsp" method="get">
	        <button type="submit">Volver a Inicio</button>
	    </form>
	</div>

    <!-- Mostrar mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <p style="color: green;"><%= request.getAttribute("mensaje") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <!-- Tabla para mostrar las reservas -->
    <table border="1">
        <tr>
            <th>ID Reserva</th>
            <th>Fecha y Hora</th>
            <th>Duración (min)</th>
            <th>ID Pista</th>
            <th>Precio</th>
            <th>Acción</th>
        </tr>
        <%
            List<ReservaBean> reservas = (List<ReservaBean>) request.getAttribute("reservas");
            if (reservas != null && !reservas.isEmpty()) {
                for (ReservaBean reserva : reservas) {
        %>
            <tr>
                <td><%= reserva.getIdReserva() %></td>
                <td><%= reserva.getFechaHora() %></td>
                <td><%= reserva.getDuracionMinutos() %></td>
                <td><%= reserva.getIdPista() %></td>
                <td><%= reserva.getPrecio() %></td>
                <td>
                    <!-- Botón para cancelar la reserva -->
                    <form method="post" action="<%= request.getContextPath() %>/client/cancelarReserva">
                        <input type="hidden" name="idReserva" value="<%= reserva.getIdReserva() %>">
                        <button type="button" class="btn-cancelar-reserva">
    						<img src="<%= request.getContextPath() %>/images/cross.png" alt="Cancelar" class="cross-icon"/>
						</button>
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <!-- Mensaje si no hay reservas disponibles -->
            <tr>
                <td colspan="6">No tienes reservas futuras con más de 24 horas de margen.</td>
            </tr>
        <%
            }
        %>
    </table>

    <!-- Modal para confirmar la cancelación -->
    <div id="modalConfirmacion">
        <div id="modalContent">
            <h3>¿Estás seguro de que deseas cancelar esta reserva?</h3>
            <button id="btn-confirmar" class="btn-confirmar">Confirmar</button>
            <button id="btn-cancelar" class="btn-cancelar">Cancelar</button>
        </div>
    </div>
</body>
</html>
