<!DOCTYPE html>
<html>
<head>
    <title>Error al crear el material</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <h1>Error al crear el material</h1>
    <div class="error-container">
        <!-- Mostrar mensaje de error espec�fico -->
        <p>Ha ocurrido un problema mientras intent�bamos crear el material:</p>
        <p><strong><%= request.getAttribute("error") %></strong></p>
        <p>Por favor, vuelve a intentarlo m�s tarde o contacta con el soporte t�cnico si el problema persiste.</p>
    </div>
    <br>
    <!-- Botones para realizar otra acci�n -->
    <a href="${pageContext.request.contextPath}/mvc/view/admin/darAltaMaterial.jsp" class="btn-secondary">Dar de Alta un Nuevo Material</a>
    <a href="<%= request.getContextPath() %>/admin/listarJugadores" class="btn-secondary">Volver al Men� Principal</a>
</body>
</html>
