<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reservas Finalizadas y Futuras</title>
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
            <th>Descuento (%)</th>
            <th>Detalles</th>
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
                <td><%= String.format("%.2f", reserva.getPrecio()) %> €</td>
                <td><%= String.format("%.0f", reserva.getDescuento() * 100) %>%</td>
                <td>
                    Tipo: <%= reserva.getTipoReserva() %> <br>
                    <% if (reserva.getIdBono() != null) { %>
                        Bono: <%= reserva.getIdBono() %> - Sesión: <%= reserva.getNumeroSesion() %> <br>
                    <% } %>
                    <% if (reserva.getTipoReserva().equals("Familiar")) { %>
                        Adultos: <%= reserva.getNumeroAdultos() %> - Niños: <%= reserva.getNumeroNinos() %>
                    <% } else if (reserva.getTipoReserva().equals("Infantil")) { %>
                        Niños: <%= reserva.getNumeroNinos() %>
                    <% } else if (reserva.getTipoReserva().equals("Adulto")) { %>
                        Adultos: <%= reserva.getNumeroAdultos() %>
                    <% } %>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="7" class="no-reservas">No hay reservas finalizadas en este rango de fechas.</td>
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
            <th>Descuento (%)</th>
            <th>Detalles</th>
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
                <td><%= String.format("%.2f", reserva.getPrecio()) %> €</td>
                <td><%= String.format("%.0f", reserva.getDescuento() * 100) %>%</td>
                <td>
                    Tipo: <%= reserva.getTipoReserva() %> <br>
                    <% if (reserva.getIdBono() != null) { %>
                        Bono: <%= reserva.getIdBono() %> - Sesión: <%= reserva.getNumeroSesion() %> <br>
                    <% } %>
                    <% if (reserva.getTipoReserva().equals("Familiar")) { %>
                        Adultos: <%= reserva.getNumeroAdultos() %> - Niños: <%= reserva.getNumeroNinos() %>
                    <% } else if (reserva.getTipoReserva().equals("Infantil")) { %>
                        Niños: <%= reserva.getNumeroNinos() %>
                    <% } else if (reserva.getTipoReserva().equals("Adulto")) { %>
                        Adultos: <%= reserva.getNumeroAdultos() %>
                    <% } %>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="7" class="no-reservas">No hay reservas futuras en este rango de fechas.</td>
            </tr>
        <%
            }
        %>
    </table>

    <!-- Botones de navegación -->
    <div class="button-container">
        <a href="../mvc/view/client/consultarReservas.jsp" class="btn-secondary">Hacer Otra Búsqueda</a>
        <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    </div>

</body>
</html>
