<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.PistaBean" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listarPistas.css">
    <title>Listado de Pistas</title>
</head>
<body>

<h2>Listado de Pistas</h2>

<!-- Botón de Volver al Menú Principal -->
<a href="<%= request.getContextPath() %>/admin/listarJugadores" class="btn-secondary">Volver al Menú Principal</a>
<a href="../mvc/view/admin/darAltaPista.jsp" class="btn-secondary">Dar de Alta otra Pista</a>

<!-- Inicia la tabla para mostrar las pistas -->
<table>
    <thead>
        <tr>
            <th>ID de la Pista</th>
            <th>Nombre</th>
            <th>Disponible</th>
            <th>Exterior</th>
            <th>Tamaño</th>
            <th>Max Jugadores</th>
        </tr>
    </thead>
    <tbody>
        <!-- Inicia el ciclo for para iterar sobre la lista de pistas -->
        <%
            List<PistaBean> pistas = (List<PistaBean>) request.getAttribute("pistas");
            if (pistas != null && !pistas.isEmpty()) {
                for (PistaBean pista : pistas) {
        %>
            <tr>
                <td><%= pista.getIdPista() %></td>
                <td><%= pista.getNombrePista() %></td>
                <td><%= pista.isDisponible() ? "Sí" : "No" %></td>
                <td><%= pista.isExterior() ? "Sí" : "No" %></td>
                <td><%= pista.getTamanoPista() %></td>
                <td><%= pista.getMaxJugadores() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="6">No hay pistas disponibles.</td>
            </tr>
        <% 
            }
        %>
    </tbody>
</table>


</body>
</html>
