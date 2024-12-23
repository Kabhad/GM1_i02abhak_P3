<!DOCTYPE html>
<html>
<head>
    <title>Error de Registro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <h1>Error al registrarse</h1>
    <!-- Mostrar el mensaje de error proporcionado o un mensaje genérico -->
    <p>
        <% String error = request.getParameter("error"); %>
        <%= error != null ? error : "Ha ocurrido un error desconocido. Por favor, intenta nuevamente." %>
    </p>
    <!-- Botón para volver al formulario de registro -->
    <a href="<%= request.getContextPath() %>/mvc/view/register.jsp">Volver al Registro</a>
</body>
</html>
