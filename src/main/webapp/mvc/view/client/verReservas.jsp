<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>
<%@ page import="es.uco.pw.display.javabean.PistaBean" %>

<!DOCTYPE html>
<html>
<head>
    <title>Modificar Reserva</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modificarReservas.css">
    <script src="${pageContext.request.contextPath}/js/modificarReservaValidation.js"></script>
</head>
<body>
    <h2>Modificar Reservas</h2>

    <!-- Mostrar mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <p style="color: green;"><%= request.getAttribute("mensaje") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Fecha</th>
                <th>Duración</th>
                <th>Pista</th>
                <th>Precio</th>
                <th>Descuento (%)</th>
                <th>Detalles</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
        <% 
            List<ReservaBean> reservasModificables = (List<ReservaBean>) request.getAttribute("reservasModificables");
            if (reservasModificables != null && !reservasModificables.isEmpty()) {
                for (ReservaBean reserva : reservasModificables) {
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
					<td>
					    <form method="post" action="${pageContext.request.contextPath}/client/modificarReserva">
					        <input type="hidden" name="idReserva" value="<%= reserva.getIdReserva() %>">
					        <input type="hidden" name="redirigirFormulario" value="true">
					        <button type="submit">Modificar</button>
					    </form>
					</td>

            </tr>
        <% 
                }
            } else {
        %>
            <tr>
                <td colspan="8" class="no-reservas">No hay reservas disponibles para modificar.</td>
            </tr>
        <% } %>
        </tbody>
    </table>

    <!-- Botones de navegación -->
    <div class="button-container">
        <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    </div>

</body>
</html>
