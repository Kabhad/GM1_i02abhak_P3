<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pistas Disponibles</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mostrarPistas.css">
</head>
<body>
    <h1>Pistas Disponibles</h1>

    <!-- Mostrar una tabla con las pistas disponibles si existen -->
    <c:if test="${not empty pistasDisponibles}">
        <table border="1" cellspacing="0" cellpadding="5">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Disponible</th>
                    <th>Exterior</th>
                    <th>Tamaño</th>
                    <th>Máx. Jugadores</th>
                </tr>
            </thead>
            <tbody>
                <!-- Iterar sobre cada pista en la lista de pistas disponibles -->
                <c:forEach var="pista" items="${pistasDisponibles}">
                    <tr>
                        <td>${pista.idPista}</td>
                        <td>${pista.nombrePista}</td>
                        <td>${pista.disponible ? 'Sí' : 'No'}</td>
                        <td>${pista.exterior ? 'Sí' : 'No'}</td>
                        <td>${pista.tamanoPista}</td>
                        <td>${pista.maxJugadores}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- Mostrar un mensaje si no hay pistas disponibles -->
    <c:if test="${empty pistasDisponibles}">
        <p>No hay pistas disponibles con los criterios seleccionados.</p>
    </c:if>

    <!-- Botón para volver a buscar pistas -->
    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/mvc/view/client/buscarPistaDisponible.jsp">Volver a Buscar Pistas</a>
    </div>
</body>
</html>
