<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dar De Alta Nuevo Material</title>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/darAltaMaterial.css">
    <!-- Enlace al script de validación -->
    <script src="<%= request.getContextPath() %>/js/darAltaMaterialValidation.js" defer></script>
</head>
<body>
    <h1>Dar de Alta un Nuevo Material</h1>

    <!-- Formulario para registrar un nuevo material -->
    <form action="<%= request.getContextPath() %>/admin/darAltaMaterial" method="post">
        <label for="idMaterial">ID del Material:</label>
        <input type="number" id="idMaterial" name="idMaterial" required><br><br>

        <label for="tipo">Tipo de Material:</label>
        <select id="tipo" name="tipo" required>
            <option value="PELOTAS">Pelotas</option>
            <option value="CANASTAS">Canastas</option>
            <option value="CONOS">Conos</option>
        </select><br><br>

        <label for="usoExterior">¿Es para uso exterior?</label>
        <select id="usoExterior" name="usoExterior" required>
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select><br><br>

        <label for="estado">Estado del Material:</label>
        <select id="estado" name="estado" required>
            <option value="DISPONIBLE">Disponible</option>
            <option value="RESERVADO">Reservado</option>
            <option value="MAL_ESTADO">En Mal Estado</option>
        </select><br><br>

        <button type="submit">Crear Material</button>
    </form>
    
    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/admin/listarJugadores" class="btn-secondary">Volver al Menú Principal</a>
    </div>

</body>
</html>
