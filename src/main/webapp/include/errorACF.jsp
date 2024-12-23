<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error de Acceso</title>
    <link rel="stylesheet" href="<%= pageContext.getServletContext().getContextPath() %>/css/error.css">
</head>
<body>
    <h1>Error de Acceso</h1>

    <!-- Mostrar el mensaje de error proporcionado -->
    <p>${error}</p>

    <!-- Botón para volver al inicio -->
    <a href="<%= pageContext.getServletContext().getContextPath() %>/index.jsp">Volver al inicio</a>
</body>
</html>
