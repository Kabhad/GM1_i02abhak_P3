<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error de Registro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <h1>Error al registrarse</h1>
    <p>
        <% String error = request.getParameter("error"); %>
        <%= error != null ? error : "Ha ocurrido un error desconocido. Por favor, intenta nuevamente." %>
    </p>
    <a href="<%= request.getContextPath() %>/mvc/view/register.jsp">Volver al Registro</a>
</body>
</html>
