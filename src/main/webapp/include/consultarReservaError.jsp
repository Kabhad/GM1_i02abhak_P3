<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="../css/error.css">
</head>
<body>
    <h1>Error</h1>

    <!-- Mostrar el mensaje de error proporcionado -->
    <p>Se ha producido un error durante la ejecuci�n de la operaci�n.</p>
    <p>${requestScope.error}</p>
    
    <!-- Botones para realizar otra b�squeda o volver al men� principal -->
    <a href="../mvc/view/client/consultarReservas.jsp" class="btn-secondary">Hacer Otra B�squeda</a>
    <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Men� Principal</a>
</body>
</html>
