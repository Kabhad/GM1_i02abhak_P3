<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.MaterialBean" %>
<%@ page import="es.uco.pw.display.javabean.PistaBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Asociar Materiales a Pistas</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/asociarMateriales.css">
</head>
<body>
    <h1>Asociar Materiales a Pistas</h1>
    
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

    <!-- Tabla de materiales -->
    <table border="1">
        <thead>
            <tr>
                <th>ID Material</th>
                <th>Tipo</th>
                <th>Uso Exterior</th>
                <th>Pista Actual</th>
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
				<td><%= material.getEstado().equals("DISPONIBLE") && material.getIdPista() > 0 ? material.getIdPista() : "Sin asignar" %></td>
				<td>
				    <form method="post" action="<%= request.getContextPath() %>/admin/asociarMaterialAPista">
				        <input type="hidden" name="idMaterial" value="<%= material.getId() %>">
				        <select name="nombrePista" required>
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
				        <button type="submit" class="btn-guardar">Asociar</button>
				    </form>
				</td>

            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="6">No hay materiales disponibles para asociar.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
</body>
</html>
