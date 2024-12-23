<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Configuración básica de la página -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
    <!-- Enlace al archivo CSS para los estilos -->
    <link rel="stylesheet" href="../../css/register.css">
    <!-- Inclusión del archivo JavaScript para validar el formulario de registro -->
    <script src="<%= request.getContextPath() %>/js/registerValidation.js" defer></script>
</head>
<body>
    <div class="container">
        <!-- Título de la página -->
        <h1>Registro de Usuario</h1>
        <!-- Mostrar mensaje de error si existe un parámetro 'error' en la solicitud -->
        <% 
            String errorMessage = request.getParameter("error");
            if (errorMessage != null) { 
        %>
            <!-- Mensaje de error que se muestra en rojo -->
            <p class="error"><%= errorMessage %></p>
        <% } %>
        <!-- Formulario para registrar un nuevo usuario -->
        <form id="registerForm" action="../controller/RegisterController.jsp" method="post" onsubmit="return validateRegister()">
            <!-- Campo para ingresar el nombre completo -->
            <div class="form-group">
                <label for="nombre">Nombre Completo:</label>
                <input type="text" id="nombre" name="nombre" placeholder="Ingresa tu nombre" required>
            </div>
            <!-- Campo para ingresar la fecha de nacimiento -->
            <div class="form-group">
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
            </div>
            <!-- Campo para ingresar el correo electrónico -->
            <div class="form-group">
                <label for="correo">Correo Electrónico:</label>
                <input type="email" id="correo" name="correo" placeholder="Ingresa tu correo" required>
            </div>
            <!-- Campo para ingresar la contraseña -->
            <div class="form-group">
                <label for="contrasena">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" placeholder="Crea una contraseña" required>
            </div>
            <!-- Botón para enviar el formulario -->
            <div class="form-group">
                <button type="submit">Registrar</button>
            </div>
        </form>
        <!-- Enlace para redirigir a la página de inicio de sesión -->
        <p>¿Ya tienes una cuenta? <a href="login.jsp">Inicia sesión aquí</a>.</p>
    </div>
</body>
</html>
