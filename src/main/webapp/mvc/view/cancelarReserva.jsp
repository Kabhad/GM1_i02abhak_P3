<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cancelar Reservas</title>
</head>
<body>
    <h1>Cancelar Reservas Futuras</h1>

    <!-- Mensajes -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <p style="color: green;"><%= request.getAttribute("mensaje") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <!-- Tabla de reservas -->
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
                    <form method="post" action="<%= request.getContextPath() %>/client/cancelarReserva">
                        <input type="hidden" name="idReserva" value="<%= reserva.getIdReserva() %>">
                        <button type="submit">Cancelar</button>
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="6">No tienes reservas futuras con más de 24 horas de margen.</td>
            </tr>
        <%
            }
        %>
    </table>
</body>
</html>
