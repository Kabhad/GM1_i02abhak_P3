<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error de Acceso</title>
    <link rel="stylesheet" href="<%= pageContext.getServletContext().getContextPath() %>/css/styles.css">
</head>
<body>
    <h1>Error de Acceso</h1>
    <p>${error}</p>
    <a href="<%= pageContext.getServletContext().getContextPath() %>/index.jsp">Volver al inicio</a>
</body>
</html>
