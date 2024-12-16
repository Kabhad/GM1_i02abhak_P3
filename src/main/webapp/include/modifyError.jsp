<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% response.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="es">
<html>
<head>
    <meta charset="UTF-8">
    <title>Error al modificar datos</title>
</head>
<body>
    <h2 style="color: red;">Error al modificar datos</h2>
    <p><%= request.getParameter("message") %></p>
    <a href="../mvc/view/modifyUser.jsp">Volver a intentar</a>
</body>
</html>
