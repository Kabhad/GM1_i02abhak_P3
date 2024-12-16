<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pistas Disponibles</title>
</head>
<body>
    <h1>Pistas Disponibles</h1>
    <c:if test="${not empty pistasDisponibles}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Disponible</th>
                    <th>Exterior</th>
                    <th>Tama�o</th>
                    <th>M�x. Jugadores</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="pista" items="${pistasDisponibles}">
                    <tr>
                        <td>${pista.idPista}</td>
                        <td>${pista.nombrePista}</td>
                        <td>${pista.disponible ? 'S�' : 'No'}</td>
                        <td>${pista.exterior ? 'S�' : 'No'}</td>
                        <td>${pista.tamanoPista}</td>
                        <td>${pista.maxJugadores}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty pistasDisponibles}">
        <p>No hay pistas disponibles en este momento.</p>
    </c:if>
</body>
</html>
