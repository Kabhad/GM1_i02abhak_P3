<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.MaterialBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Listado de Materiales</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/listarMateriales.css">
</head>
<body>
    <h1>Listado de Materiales</h1>

    <% if (request.getAttribute("mensaje") != null) { %>
        <p style="color: green;"><%= request.getAttribute("mensaje") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Tipo</th>
            <th>Uso Exterior</th>
            <th>Estado</th>
        </tr>
        <%
            List<MaterialBean> materiales = (List<MaterialBean>) request.getAttribute("materiales");
            if (materiales != null && !materiales.isEmpty()) {
                for (MaterialBean material : materiales) {
        %>
            <tr>
                <td><%= material.getId() %></td>
                <td><%= material.getTipo() %></td>
                <td><%= material.isUsoExterior() ? "Sí" : "No" %></td>
                <td><%= material.getEstado() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">No hay materiales registrados.</td>
            </tr>
        <%
            }
        %>
    </table>

    <br>
    
            <!-- Botones de navegación -->
    <div class="button-container">
        <a href="../mvc/view/darAltaMaterial.jsp" class="btn-secondary">Dar de Alta otro Material</a>
        <a href="../mvc/view/adminHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    </div>


</body>
</html>
