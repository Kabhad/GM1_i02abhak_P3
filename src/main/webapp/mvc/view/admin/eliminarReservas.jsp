<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eliminar Reservas Futuras</title>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/eliminarReservas.css">
    <!-- Enlace al script de validación -->
    <script src="<%= request.getContextPath() %>/js/eliminarReservaValidation.js"></script>
</head>
<body>
    <h1>Eliminar Reservas Futuras</h1>

    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/admin/listarJugadores" class="btn-secondary">Volver al Menú Principal</a>
    </div>

    <!-- Mostrar mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <div id="mensajeExito"><%= request.getAttribute("mensaje") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div id="mensajeError"><%= request.getAttribute("error") %></div>
    <% } %>

    <!-- Tabla para listar las reservas futuras -->
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
            // Obtener la lista de reservas futuras desde el atributo de la solicitud
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
                <!-- Formulario para eliminar la reserva -->
                <form method="post" action="<%= request.getContextPath() %>/admin/eliminarReserva">
                    <input type="hidden" name="idReserva" value="<%= reserva.getIdReserva() %>">
                    <button type="submit" class="btn-eliminar">
                        <img src="<%= request.getContextPath() %>/images/trash.png" alt="Eliminar" class="trash-icon"/>
                    </button>
                </form>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <!-- Mensaje si no hay reservas futuras -->
        <tr>
            <td colspan="6">No hay reservas futuras disponibles.</td>
        </tr>
        <%
            }
        %>
    </table>

    <!-- Modal de confirmación para la eliminación -->
    <div id="modalConfirmacion" class="modal">
        <div class="modal-content">
            <h2>Confirmación</h2>
            <p>¿Estás seguro de que quieres eliminar esta reserva?</p>
            <button id="btn-confirmar" class="btn-confirmar">Sí, Eliminar</button>
            <button id="btn-cancelar" class="btn-cancelar">Cancelar</button>
        </div>
    </div>
</body>
</html>
