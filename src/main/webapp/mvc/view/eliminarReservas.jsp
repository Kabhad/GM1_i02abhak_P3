<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Eliminar Reservas</title>
    <style>
        table {
            width: 80%;
            margin: auto;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        img {
            cursor: pointer;
            width: 20px;
            height: 20px;
        }
        .message {
            color: green;
            text-align: center;
            font-size: 18px;
            margin: 10px 0;
        }
        .error {
            color: red;
            text-align: center;
            font-size: 18px;
            margin: 10px 0;
        }
    </style>
    <script>
        function confirmarEliminacion(idReserva) {
            const confirmacion = confirm("¿Estás seguro de que deseas eliminar esta reserva?");
            if (confirmacion) {
                document.getElementById("formEliminar" + idReserva).submit();
            }
        }
    </script>
</head>
<body>
    <h2 style="text-align: center;">Gestión de Reservas Futuras</h2>

    <%-- Mostrar mensajes de éxito o error --%>
    <% if (request.getAttribute("mensaje") != null) { %>
        <div class="message"><%= request.getAttribute("mensaje") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <%-- Tabla de reservas futuras --%>
    <table>
        <tr>
            <th>ID Reserva</th>
            <th>Fecha y Hora</th>
            <th>Duración (min)</th>
            <th>Pista</th>
            <th>Precio (€)</th>
            <th>Acción</th>
        </tr>
        <%
            List<ReservaBean> reservas = (List<ReservaBean>) request.getAttribute("reservasFuturas");
            if (reservas != null && !reservas.isEmpty()) {
                for (ReservaBean reserva : reservas) { %>
                    <tr>
                        <td><%= reserva.getIdReserva() %></td>
                        <td><%= reserva.getFechaHora() %></td>
                        <td><%= reserva.getDuracionMinutos() %></td>
                        <td><%= reserva.getIdPista() %></td>
                        <td><%= reserva.getPrecio() %></td>
                        <td>
                            <%-- Formulario oculto para enviar la eliminación --%>
                            <form id="formEliminar<%= reserva.getIdReserva() %>" method="post" action="<%= request.getContextPath() %>/admin/eliminarReserva">
                                <input type="hidden" name="idReserva" value="<%= reserva.getIdReserva() %>">
                                <img src="<%= request.getContextPath() %>/resources/trash.png" alt="Eliminar" title="Eliminar"
                                     onclick="confirmarEliminacion('<%= reserva.getIdReserva() %>')">
                            </form>
                        </td>
                    </tr>
                <% }
            } else { %>
                <tr>
                    <td colspan="6">No hay reservas futuras disponibles.</td>
                </tr>
            <% } %>
    </table>
</body>
</html>
