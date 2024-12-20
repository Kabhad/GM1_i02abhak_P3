
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modificar Estado de Pista</title>
    <script src="../js/validarEstadoPista.js"></script>
    <link rel="stylesheet" href="../../css/modificarEstadoPista.css">
</head>
<body>
    <h1>Modificar Estado de Pista</h1>
    <form action="${pageContext.request.contextPath}/admin/modificarEstadoPista" method="post">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Exterior</th>
                    <th>Estado Actual</th>
                    <th>Nuevo Estado</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="pista" items="${pistas}">
                    <tr>
                        <td>${pista.idPista}</td>
                        <td>${pista.nombrePista}</td>
                        <td>${pista.exterior ? "Sí" : "No"}</td>
                        <td>${pista.disponible ? "Disponible" : "No Disponible"}</td>
                        <td>
                            <select name="nuevoEstado_${pista.idPista}" required>
                                <option value="true">Disponible</option>
                                <option value="false">No Disponible</option>
                            </select>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button type="submit">Guardar Cambios</button>
    </form>

    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="../view/adminHome.jsp" class="nav-button">Volver al Menú Principal</a>
    </div>
</body>
</html>