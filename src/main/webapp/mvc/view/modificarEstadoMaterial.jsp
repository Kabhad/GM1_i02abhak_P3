<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modificar Estado de Pista</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/modificarEstadoPista.css">
    <script src="<%= request.getContextPath() %>/js/validarEstadoPista.js"></script>
</head>
<body>
    <h1>Modificar Estado de Pista</h1>

    <!-- Botón para volver al menú principal -->
    <form action="<%= request.getContextPath() %>/mvc/view/adminHome.jsp" method="get">
        <button type="submit">Volver a Inicio</button>
    </form>

    <!-- Mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <div id="mensajeExito"><%= request.getAttribute("mensaje") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div id="mensajeError"><%= request.getAttribute("error") %></div>
    <% } %>

    <!-- Tabla de pistas -->
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Exterior</th>
                <th>Estado Actual</th>
                <th>Nuevo Estado</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<es.uco.pw.display.javabean.PistaBean> pistas =
                        (List<es.uco.pw.display.javabean.PistaBean>) request.getAttribute("pistas");
                if (pistas != null && !pistas.isEmpty()) {
                    for (es.uco.pw.display.javabean.PistaBean pista : pistas) {
            %>
                <tr>
                    <td><%= pista.getIdPista() %></td>
                    <td><%= pista.getNombrePista() %></td>
                    <td><%= pista.isExterior() ? "Sí" : "No" %></td>
                    <td><%= pista.isDisponible() ? "Disponible" : "No Disponible" %></td>
                    <td>
                        <form method="post" action="<%= request.getContextPath() %>/admin/ModificarEstadoPistaServlet">
                            <input type="hidden" name="idPista" value="<%= pista.getIdPista() %>">
                            <select name="nuevoEstado" required>
                                <option value="true">Disponible</option>
                                <option value="false">No Disponible</option>
                            </select>
                            <button type="submit">Guardar</button>
                        </form>
                    </td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="5">No hay pistas disponibles.</td>
                </tr>
            <% 
                }
            %>
        </tbody>
    </table>
</body>
</html>gj
