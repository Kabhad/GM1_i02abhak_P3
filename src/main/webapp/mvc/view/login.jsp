<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Configuración básica de la página -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Enlace al archivo CSS para los estilos -->
    <link rel="stylesheet" href="../../css/login.css">
    <!-- Inclusión del archivo JavaScript para validar el formulario de inicio de sesión -->
    <script src="<%= request.getContextPath() %>/js/loginValidation.js" defer></script>
</head>
<body>
    <div class="container">
        <!-- Título de la página -->
        <h1>Inicio de Sesión</h1>
        <!-- Mostrar mensajes de error o éxito si existen -->
        <% 
            String errorMessage = (String) request.getAttribute("error");
            String successMessage = (String) request.getAttribute("successMessage");
        %>
        <% if (errorMessage != null) { %>
            <!-- Mensaje de error que se muestra en rojo -->
            <p class="error"><%= errorMessage %></p>
        <% } else if (successMessage != null) { %>
            <!-- Mensaje de éxito que se muestra en verde -->
            <p class="success"><%= successMessage %></p>
        <% } %>
        <!-- Formulario para iniciar sesión -->
        <form action="../controller/LoginController.jsp" method="post" onsubmit="return validateLogin()">
            <!-- Campo para ingresar el correo electrónico -->
            <div class="form-group">
                <label for="correo">Correo Electrónico:</label>
                <input type="email" id="correo" name="correo" required>
            </div>
            <!-- Campo para ingresar la contraseña -->
            <div class="form-group">
                <label for="contrasena">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" required>
            </div>
            <!-- Botón para enviar el formulario -->
            <div class="form-group">
                <button type="submit">Iniciar Sesión</button>
            </div>
        </form>
        <!-- Enlace para redirigir a la página de registro -->
        <p>¿No tienes una cuenta? <a href="<%= request.getContextPath() %>/mvc/view/register.jsp">Regístrate aquí</a>.</p>
    </div>
</body>
</html>
