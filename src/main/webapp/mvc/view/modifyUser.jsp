<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../view/login.jsp");
        return;
    }
%>
<h2>Modificar Datos</h2>
<form action="../controller/ModifyUserController.jsp" method="post" onsubmit="return validateModifyForm();">
    <label>Nombre:</label>
    <input type="text" name="nombre" value="<%= customer.getNombre() %>" required><br>
    <label>Contraseña:</label>
    <input type="password" name="contrasena" required><br>
    <button type="submit">Guardar Cambios</button>
</form>
