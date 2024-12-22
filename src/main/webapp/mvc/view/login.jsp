<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="../../css/login.css">
	<script src="<%= request.getContextPath() %>/js/loginValidation.js" defer></script>
</head>
<body>
    <div class="container">
        <h1>Inicio de Sesión</h1>
        <!-- Mostrar mensaje de error si existe -->
        <% 
            String errorMessage = (String) request.getAttribute("error");
       		String successMessage = (String) request.getAttribute("successMessage");
        %>
        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } else if (successMessage != null) { %>
            <p class="success"><%= successMessage %></p> <!-- Mensaje de éxito -->
        <% } %>
        <form action="../controller/LoginController.jsp" method="post" onsubmit="return validateLogin()">
            <div class="form-group">
                <label for="correo">Correo Electrónico:</label>
                <input type="email" id="correo" name="correo" required>
            </div>
            <div class="form-group">
                <label for="contrasena">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" required>
            </div>
            <div class="form-group">
                <button type="submit">Iniciar Sesión</button>
            </div>
        </form>
		<p>¿No tienes una cuenta? <a href="<%= request.getContextPath() %>/mvc/view/register.jsp">Regístrate aquí</a>.</p>
    </div>
</body>
</html>
