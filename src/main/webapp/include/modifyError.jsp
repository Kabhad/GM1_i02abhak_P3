<!DOCTYPE html>
<html lang="es">
<html>
<head>
    <meta charset="UTF-8">
    <title>Error al modificar datos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <h2 style="color: red;">Error al modificar datos</h2>
    <!-- Mostrar el mensaje de error proporcionado -->
    <p><%= request.getParameter("message") %></p>
    <!-- Botón para volver al formulario de modificación -->
    <a href="../mvc/view/modifyUser.jsp">Volver a intentar</a>
</body>
</html>
