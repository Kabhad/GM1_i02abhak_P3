<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="../css/error.css">
</head>
<body>
    <h1>Error</h1>
    <p>Se ha producido un error durante la ejecución de la operación.</p>
    <p>${requestScope.error}</p>
    
    <a href="../mvc/view/client/consultarReservas.jsp" class="btn-secondary">Hacer Otra Búsqueda</a>
    <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
</body>
</html>
