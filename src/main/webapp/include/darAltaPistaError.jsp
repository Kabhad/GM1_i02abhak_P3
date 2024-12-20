<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error al Listar Pistas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listarPistasError.css">
</head>
<body>
    <h1>Error al Listar las Pistas</h1>
    <div class="error-container">
        <p>Ha ocurrido un problema mientras intentábamos recuperar la lista de pistas:</p>
        <p><strong><%= request.getAttribute("error") %></strong></p>
        <p>Por favor, vuelve a intentarlo más tarde o contacta con el soporte técnico si el problema persiste.</p>
    </div>
    <br>
    <a href="${pageContext.request.contextPath}/mvc/view/admin/darAltaPista.jsp" class="btn-secondary">Dar de Alta una Nueva Pista</a>
    <a href="${pageContext.request.contextPath}/mvc/view/admin/adminHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
</body>
</html>
