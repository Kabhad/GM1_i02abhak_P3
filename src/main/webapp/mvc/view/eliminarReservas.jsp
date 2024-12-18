<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Eliminar Reservas</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f4f4f4;
        }

        img {
            width: 20px;
            height: 20px;
        }

        .success {
            color: green;
            font-weight: bold;
        }

        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h2>Reservas Futuras</h2>

    <!-- Mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <p class="success"><%= request.getAttribute("mensaje") %></p>
    <% } else if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <!-- Tabla de reservas -->
    <table>
        <thead>
            <tr>
                <th>Fecha y Hora</th>
                <th>Duración (min)</th>
                <th>Pista</th>
                <th>Precio (€)</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="reserva" items="${reservasFuturas}">
                <tr>
                    <td>${reserva.fechaHora}</td>
                    <td>${reserva.duracionMinutos}</td>
                    <td>${reserva.idPista}</td>
                    <td>${reserva.precio}</td>
                    <td>
                        <form action="/admin/eliminarReserva" method="POST">
                            <input type="hidden" name="idReserva" value="${reserva.idReserva}">
                            <button type="submit" onclick="return confirm('¿Estás seguro de que deseas eliminar esta reserva?');">
                                <img src="/resources/img/trash.png" alt="Eliminar">
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
