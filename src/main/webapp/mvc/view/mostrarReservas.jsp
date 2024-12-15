<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reservas Finalizadas y Futuras</title>
    <!-- Enlace al CSS separado -->
    <link rel="stylesheet" href="../../css/mostrarReservas.css">
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
            <th>Descuento</th>
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
                <td><%= reserva.getPrecio() %> €</td>
                <td><%= reserva.getDescuento() %> €</td>
                <td>
                    <c:if test="${not empty reserva.idBono}">
                        <b>Bono:</b> <%= reserva.getIdBono() %> <br/>
                        <b>Sesión:</b> <%= reserva.getNumeroSesion() %> <br/>
                    </c:if>
                    <c:if test="${not empty reserva.numeroAdultos}">
                        <b>Familiares:</b> Adultos: <%= reserva.getNumeroAdultos() %>, Niños: <%= reserva.getNumeroNinos() %> <br/>
                    </c:if>
                    <c:if test="${empty reserva.numeroAdultos}">
                        <b>Infantil:</b> Niños: <%= reserva.getNumeroNinos() %> <br/>
                    </c:if>
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
            <th>Descuento</th>
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
                <td><%= reserva.getPrecio() %> €</td>
                <td><%= reserva.getDescuento() %> €</td>
                <td>
                    <c:if test="${not empty reserva.idBono}">
                        <b>Bono:</b> <%= reserva.getIdBono() %> <br/>
                        <b>Sesión:</b> <%= reserva.getNumeroSesion() %> <br/>
                    </c:if>
                    <c:if test="${not empty reserva.numeroAdultos}">
                        <b>Familiares:</b> Adultos: <%= reserva.getNumeroAdultos() %>, Niños: <%= reserva.getNumeroNinos() %> <br/>
                    </c:if>
                    <c:if test="${empty reserva.numeroAdultos}">
                        <b>Infantil:</b> Niños: <%= reserva.getNumeroNinos() %> <br/>
                    </c:if>
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
        <a href="../mvc/view/consultarReservas.jsp" class="nav-button">Hacer Otra Búsqueda</a>
        <a href="../mvc/view/clientHome.jsp" class="nav-button">Volver al Menú Principal</a>
    </div>
</body>
</html>
