<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dar De Alta Nueva Pista</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/darAltaPista.css">
    <script src="<%= request.getContextPath() %>/js/darAltaPistaValidation.js" defer></script>
</head>
<body>
    <h1>Dar de Alta una Nueva Pista</h1>

    <!-- Formulario para crear pista -->
    <form action="<%= request.getContextPath() %>/admin/darAltaPista" method="post">
        <label for="idPista">ID de la Pista:</label>
        <input type="number" id="idPista" name="idPista" required><br><br>

        <label for="nombrePista">Nombre de la Pista:</label>
        <input type="text" id="nombrePista" name="nombrePista" required><br><br>

        <label for="disponible">¿Está Disponible?</label>
        <select id="disponible" name="disponible" required>
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select><br><br>

        <label for="exterior">¿Es Exterior?</label>
        <select id="exterior" name="exterior" required>
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select><br><br>

        <label for="tamanoPista">Tamaño de la Pista:</label>
        <select id="tamanoPista" name="tamanoPista" required>
            <option value="MINIBASKET">Minibasket</option>
            <option value="_3VS3">3vs3</option>
            <option value="ADULTOS">Adultos</option>
        </select>

        <label for="maxJugadores">Máximo de Jugadores:</label>
        <input type="number" id="maxJugadores" name="maxJugadores" required><br><br>

        <button type="submit">Crear Pista</button>
    </form>
    
    <!-- Botones de navegación -->
    <div class="button-container">
        <a href="../view/adminHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    </div>

</body>
</html>
