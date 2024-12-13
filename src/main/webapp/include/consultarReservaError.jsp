<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <h1>Error</h1>
    <p>Se ha producido un error durante la ejecución de la operación.</p>
    <p>${requestScope.error}</p>
    <a href="<c:url value='/index.jsp' />">Volver al inicio</a>
</body>
</html>
