<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../view/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Modificar Datos</title>
    <link rel="stylesheet" href="../../css/modifyUser.css">
    <script src="../../js/modifyValidation.js"></script>
</head>
<body>
    <h2>Modificar Datos</h2>
    <div class="form-container">
        <form action="../controller/ModifyUserController.jsp" method="post" onsubmit="return validateModifyForm();">
            <label>Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="<%= customer.getNombre() %>" required>
            
            <label>Contraseña:</label>
            <input type="password" id="contrasena" name="contrasena" required>
            
            <div class="button-container">
                <button type="submit">Guardar Cambios</button>
                <button type="button" class="volver" onclick="window.location.href='<%= customer.getTipoUsuario().equalsIgnoreCase("administrador") ? "../view/adminHome.jsp" : "../view/clientHome.jsp" %>';">
                    Volver
                </button>
            </div>
        </form>
    </div>
</body>
</html>
