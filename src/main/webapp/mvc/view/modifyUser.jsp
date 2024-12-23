<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    // Verifica si hay un usuario autenticado en la sesión.
    // Si no hay un usuario autenticado, redirige a la página de inicio de sesión.
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../view/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <!-- Configuración básica de la página -->
    <meta charset="UTF-8">
    <title>Modificar Datos</title>
    <!-- Enlace al archivo CSS para los estilos -->
    <link rel="stylesheet" href="../../css/modifyUser.css">
    <!-- Inclusión del archivo JavaScript para validar el formulario -->
    <script src="../../js/modifyValidation.js"></script>
</head>
<body>
    <!-- Título de la página -->
    <h2>Modificar Datos</h2>
    <div class="form-container">
        <!-- Formulario para modificar los datos del usuario -->
        <form action="../controller/ModifyUserController.jsp" method="post" onsubmit="return validateModifyForm();">
            <!-- Campo para modificar el nombre del usuario -->
            <label>Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="<%= customer.getNombre() %>" required>
            
            <!-- Campo para modificar la contraseña del usuario -->
            <label>Contraseña:</label>
            <input type="password" id="contrasena" name="contrasena" required>
            
            <div class="button-container">
                <!-- Botón para guardar los cambios -->
                <button type="submit">Guardar Cambios</button>
                
                <!-- Botón para volver al menú principal, dependiendo del tipo de usuario -->
                <button type="button" class="volver" onclick="window.location.href='<%= customer.getTipoUsuario().equalsIgnoreCase("administrador") ? request.getContextPath() + "/admin/listarJugadores" : "../view/client/clientHome.jsp" %>'; ">
                    Volver al Menú
                </button>
            </div>
        </form>
    </div>
</body>
</html>
