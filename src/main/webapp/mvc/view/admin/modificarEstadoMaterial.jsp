<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.MaterialBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modificar Estado de Materiales</title>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/listarMateriales.css">
    <!-- Enlace al script de validación -->
    <script src="<%= request.getContextPath() %>/js/modificarEstadoMaterialValidation.js" defer></script>
</head>
<body>
    <h1>Modificar Estado de Materiales</h1>
    
    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/admin/listarJugadores" class="btn-secondary">Volver al Menú Principal</a>
    </div>

    <!-- Mostrar mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <p id="mensajeExito" style="color: green;"><%= request.getAttribute("mensaje") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p id="mensajeError" style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <!-- Tabla para mostrar los materiales disponibles -->
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tipo</th>
                <th>Uso Exterior</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
        <%
            // Recuperar la lista de materiales desde el request
            List<MaterialBean> materiales = (List<MaterialBean>) request.getAttribute("materiales");
            if (materiales != null && !materiales.isEmpty()) {
                for (MaterialBean material : materiales) {
        %>
            <tr>
                <td><%= material.getId() %></td>
                <td><%= material.getTipo() %></td>
                <td><%= material.isUsoExterior() ? "Sí" : "No" %></td>
                <td><%= material.getEstado() %></td>
                <td>
                    <!-- Formulario para actualizar el estado del material -->
                    <form method="post" action="<%= request.getContextPath() %>/admin/modificarEstadoMaterial">
                        <input type="hidden" name="idMaterial" value="<%= material.getId() %>">
                        <select name="nuevoEstado" required>
                            <option value="DISPONIBLE" <%= "DISPONIBLE".equals(material.getEstado()) ? "selected" : "" %>>Disponible</option>
                            <option value="RESERVADO" <%= "RESERVADO".equals(material.getEstado()) ? "selected" : "" %>>Reservado</option>
                            <option value="MAL_ESTADO" <%= "MAL_ESTADO".equals(material.getEstado()) ? "selected" : "" %>>Mal Estado</option>
                        </select>
                        <button type="submit" class="btn-primary">Guardar</button>
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <!-- Mensaje si no hay materiales registrados -->
            <tr>
                <td colspan="5">No hay materiales registrados.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

</body>
</html>
