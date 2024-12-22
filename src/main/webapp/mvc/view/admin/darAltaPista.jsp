<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dar de Alta una Nueva Pista</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/darAltaPista.css">
</head>
<body>
    <h1>Dar de Alta una Nueva Pista</h1>
    <form action="<%= request.getContextPath() %>/admin/darAltaPista" method="post">
        <label for="nombrePista">Nombre de la Pista:</label>
        <input type="text" id="nombrePista" name="nombrePista" required>
        <br>

        <label for="disponible">Disponible:</label>
        <select id="disponible" name="disponible">
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select>
        <br>

        <label for="exterior">Exterior:</label>
        <select id="exterior" name="exterior">
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select>
        <br>

        <label for="tamanoPista">Tamaño de la Pista:</label>
        <select id="tamanoPista" name="tamanoPista" required>
            <option value="MINIBASKET">Minibasket</option>
            <option value="ADULTOS">Adultos</option>
            <option value="_3VS3">3 vs 3</option>
        </select>
        <br>

        <label for="maxJugadores">Número Máximo de Jugadores:</label>
        <input type="number" id="maxJugadores" name="maxJugadores" min="1" required>
        <br>

        <button type="submit">Crear Pista</button>
    </form>
    
            <!-- Botones de navegación -->
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/admin/listarJugadores" class="btn-secondary">Volver al Menú Principal</a>
    </div>
    
</body>
</html>
