<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Buscar Pistas Disponibles</title>
    <!-- Enlace al script para validar el formulario -->
    <script src="<%= request.getContextPath() %>/js/buscarPistaValidation.js" defer></script>
    <!-- Enlace al archivo de estilos -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/buscarPistaDisponible.css">
</head>
<body>
    <h1>Buscar Pistas Disponibles</h1>
    
    <!-- Mostrar un mensaje de error si existe -->
    <c:if test="${not empty error}">
        <div class="error-message">
            <p style="color: red;">${error}</p>
        </div>
    </c:if>
    
    <!-- Formulario para buscar pistas -->
    <form id="buscarPistasForm" action="${pageContext.request.contextPath}/client/buscarPistaDisponible" method="GET">
        <!-- Selección del tamaño de la pista -->
        <label for="tamano">Tamaño de la pista:</label>
        <select name="tamano" id="tamano">
            <option value="">Cualquiera</option>
            <option value="MINIBASKET">Minibasket</option>
            <option value="ADULTOS">Adultos</option>
            <option value="_3VS3">3 vs 3</option>
        </select>
        <br><br>
        
        <!-- Selección de si la pista es exterior -->
        <label for="exterior">¿Exterior?</label>
        <select name="exterior" id="exterior">
            <option value="">Cualquiera</option>
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select>
        <br><br>
        
        <!-- Campo para seleccionar la fecha y hora de búsqueda -->
        <label for="fechaHora">Fecha y Hora de búsqueda:</label>
        <input type="datetime-local" name="fechaHora" id="fechaHora" required>
        <br><br>
        
        <!-- Selección de la duración -->
        <label for="duracionMin">Duración (minutos):</label>
        <select id="duracionMin" name="duracionMin" required>
            <option value="60">60 minutos (1 hora)</option>
            <option value="90">90 minutos (1 hora y 30 minutos)</option>
            <option value="120">120 minutos (2 horas)</option>
        </select>
        <br><br>
        
        <!-- Botón para enviar el formulario -->
        <button type="submit">Buscar Pistas</button>
    </form>
    
    <!-- Enlace para volver al menú principal -->
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/mvc/view/client/clientHome.jsp" class="nav-button">Volver al Menú Principal</a>
    </div>
</body>
</html>
