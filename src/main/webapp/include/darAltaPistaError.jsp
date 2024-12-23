<!DOCTYPE html>
<html>
<head>
    <title>Error al Listar Pistas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <h1>Error al Listar las Pistas</h1>
    <div class="error-container">
        <!-- Mostrar el mensaje de error proporcionado -->
        <p>Ha ocurrido un problema mientras intent�bamos recuperar la lista de pistas:</p>
        <p><strong><%= request.getAttribute("error") %></strong></p>
        <p>Por favor, vuelve a intentarlo m�s tarde o contacta con el soporte t�cnico si el problema persiste.</p>
    </div>
    <br>

    <!-- Botones para realizar otras acciones -->
    <a href="${pageContext.request.contextPath}/mvc/view/admin/darAltaPista.jsp" class="btn-secondary">Dar de Alta una Nueva Pista</a>
    <a href="<%= request.getContextPath() %>/admin/listarJugadores" class="btn-secondary">Volver al Men� Principal</a>
</body>
</html>
