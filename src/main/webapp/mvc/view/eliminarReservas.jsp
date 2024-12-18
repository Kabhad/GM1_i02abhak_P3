<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Eliminar Reservas Futuras</title>
</head>
<body>
    <h1>Eliminar Reservas Futuras</h1>

    <!-- Mensajes de éxito o error -->
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
            List<es.uco.pw.display.javabean.ReservaBean> reservasFuturas =
                    (List<es.uco.pw.display.javabean.ReservaBean>) request.getAttribute("reservasFuturas");
            if (reservasFuturas != null && !reservasFuturas.isEmpty()) {
                for (es.uco.pw.display.javabean.ReservaBean reserva : reservasFuturas) {
        %>
            <tr>
                <td><%= reserva.getIdReserva() %></td>
                <td><%= reserva.getFechaHora() %></td>
                <td><%= reserva.getDuracionMinutos() %></td>
                <td><%= reserva.getIdPista() %></td>
                <td><%= reserva.getPrecio() %></td>
                <td>
                    <form method="post" action="<%= request.getContextPath() %>/admin/eliminarReserva">
                        <input type="hidden" name="idReserva" value="<%= reserva.getIdReserva() %>">
                        <button type="submit">Eliminar</button>
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="6">No hay reservas futuras disponibles.</td>
            </tr>
        <%
            }
        %>
    </table>
</body>
</html>
