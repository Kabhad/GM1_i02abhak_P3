<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.business.pista.PistaDTO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modificar Estado de Pistas</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/modificarEstadoPista.css">
</head>
<body>
    <h1>Modificar Estado de Pistas</h1>
    
    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/mvc/view/admin/adminHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    </div>

    <!-- Mostrar mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <p id="mensajeExito" style="color: green;"><%= request.getAttribute("mensaje") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p id="mensajeError" style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <!-- Tabla de pistas -->
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tamaño</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
        <%
            // Obtener la lista de pistas desde el request
            List<PistaDTO> pistas = (List<PistaDTO>) request.getAttribute("pistas");
            if (pistas != null && !pistas.isEmpty()) {
                for (PistaDTO pista : pistas) {
        %>
            <tr>
                <td><%= pista.getIdPista() %></td>
                <td><%= pista.getPista().name().replace("_", "").toLowerCase() %></td>
                <td><%= pista.isDisponible() ? "Disponible" : "No Disponible" %></td>
                <td>
                    <!-- Formulario para cambiar el estado -->
                    <form method="post" action="<%= request.getContextPath() %>/admin/modificarEstadoPista">
                        <input type="hidden" name="idPista" value="<%= pista.getIdPista() %>">
                        <select name="nuevoEstado" required>
                            <option value="true" <%= pista.isDisponible() ? "selected" : "" %>>Disponible</option>
                            <option value="false" <%= !pista.isDisponible() ? "selected" : "" %>>No Disponible</option>
                        </select>
                        <button type="submit" class="btn-guardar">Guardar</button>
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">No hay pistas registradas.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

</body>
</html>
