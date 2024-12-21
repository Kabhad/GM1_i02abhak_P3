<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.MaterialBean" %>
<%@ page import="es.uco.pw.display.javabean.PistaBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Asociar Materiales a Pistas</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/asociarMaterialAPista.css">
</head>
<body>
    <h1>Asociar Materiales a Pistas</h1>
    
    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/mvc/view/admin/adminHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    </div>

    <!-- Mostrar mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <p id="mensajeExito" class="success-message"><%= request.getAttribute("mensaje") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p id="mensajeError" class="error-message"><%= request.getAttribute("error") %></p>
    <% } %>

    <!-- Tabla de materiales -->
    <table>
        <thead>
            <tr>
                <th>ID Material</th>
                <th>Tipo</th>
                <th>Uso Exterior</th>
                <th>Asociar a Nueva Pista</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<MaterialBean> materiales = (List<MaterialBean>) request.getAttribute("materiales");
            List<PistaBean> pistas = (List<PistaBean>) request.getAttribute("pistas");
            
            if (materiales != null && !materiales.isEmpty()) {
                for (MaterialBean material : materiales) {
        %>
            <tr>
                <td><%= material.getId() %></td>
                <td><%= material.getTipo() %></td>
                <td><%= material.isUsoExterior() ? "Sí" : "No" %></td>
                <td>
                    <form method="post" action="<%= request.getContextPath() %>/admin/asociarMaterialAPista" class="form-inline">
                        <input type="hidden" name="idMaterial" value="<%= material.getId() %>">
                        <select name="nombrePista" required class="form-select">
                            <option value="" disabled selected>Selecciona una pista</option>
                            <% 
                                for (PistaBean pista : pistas) {
                                    String seleccionada = (material.getIdPista() == pista.getIdPista()) ? "selected" : "";
                            %>
                            <option value="<%= pista.getNombrePista() %>" <%= seleccionada %>>
                                <%= pista.getNombrePista() %> (<%= pista.isExterior() ? "Exterior" : "Interior" %>)
                            </option>
                            <% } %>
                        </select>
                </td>
                <td>
                        <button type="submit" class="btn-primary">Asociar</button>
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="5">No hay materiales disponibles para asociar.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
</body>
</html>
