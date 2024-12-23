<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dar de Alta una Nueva Pista</title>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/darAltaPista.css">
    <!-- Enlace al script de validación -->
    <script src="<%= request.getContextPath() %>/js/darAltaPistaValidation.js" defer></script>
</head>
<body>
    <h1>Dar de Alta una Nueva Pista</h1>

    <!-- Formulario para registrar una nueva pista -->
    <form action="<%= request.getContextPath() %>/admin/darAltaPista" method="post">
        <!-- Campo para ingresar el nombre de la pista -->
        <label for="nombrePista">Nombre de la Pista:</label>
        <input type="text" id="nombrePista" name="nombrePista" required>
        <br>

        <!-- Campo para seleccionar si la pista está disponible -->
        <label for="disponible">Disponible:</label>
        <select id="disponible" name="disponible">
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select>
        <br>

        <!-- Campo para indicar si la pista es exterior -->
        <label for="exterior">Exterior:</label>
        <select id="exterior" name="exterior">
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select>
        <br>

        <!-- Campo para seleccionar el tamaño de la pista -->
        <label for="tamanoPista">Tamaño de la Pista:</label>
        <select id="tamanoPista" name="tamanoPista" required>
            <option value="MINIBASKET">Minibasket</option>
            <option value="ADULTOS">Adultos</option>
            <option value="_3VS3">3 vs 3</option>
        </select>
        <br>

        <!-- Campo para ingresar el número máximo de jugadores -->
        <label for="maxJugadores">Número Máximo de Jugadores:</label>
        <input type="number" id="maxJugadores" name="maxJugadores" min="1" required>
        <br>

        <!-- Botón para enviar el formulario -->
        <button type="submit">Crear Pista</button>
    </form>
    
    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/admin/listarJugadores" class="btn-secondary">Volver al Menú Principal</a>
    </div>
</body>
</html>
