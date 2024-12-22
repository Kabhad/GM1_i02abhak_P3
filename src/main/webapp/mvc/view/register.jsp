<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
    <link rel="stylesheet" href="../../css/register.css">
	<script src="<%= request.getContextPath() %>/js/registerValidation.js" defer></script>


</head>
<body>
    <div class="container">
        <h1>Registro de Usuario</h1>
        <% 
            String errorMessage = request.getParameter("error");
            if (errorMessage != null) { 
        %>
            <p class="error"><%= errorMessage %></p>
        <% } %>
        <form id="registerForm" action="../controller/RegisterController.jsp" method="post" onsubmit="return validateRegister()">
            <div class="form-group">
                <label for="nombre">Nombre Completo:</label>
                <input type="text" id="nombre" name="nombre" placeholder="Ingresa tu nombre" required>
            </div>
            <div class="form-group">
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
            </div>
            <div class="form-group">
                <label for="correo">Correo Electrónico:</label>
                <input type="email" id="correo" name="correo" placeholder="Ingresa tu correo" required>
            </div>
            <div class="form-group">
                <label for="contrasena">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" placeholder="Crea una contraseña" required>
            </div>
            <div class="form-group">
                <button type="submit">Registrar</button>
            </div>
        </form>
        <p>¿Ya tienes una cuenta? <a href="login.jsp">Inicia sesión aquí</a>.</p>
    </div>
</body>
</html>
