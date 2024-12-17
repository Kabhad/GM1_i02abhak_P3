<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Buscar Pistas</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buscarPistaError.css">
</head>
<body>
    <div class="error-container">
        <h1>Error al Buscar Pistas Disponibles</h1>
        <p>${error}</p>
        <p>Por favor, inténtalo de nuevo o vuelve al menú principal.</p>
        <a href="${pageContext.request.contextPath}/mvc/view/buscarPistaDisponible.jsp">Volver a Buscar Pistas</a>
    </div>
</body>
</html>
