<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Buscar Pistas Disponibles</title>


     <link rel="stylesheet" href="<%= request.getContextPath() %>/css/buscarPistaDisponible.css">
    <script src="${pageContext.request.contextPath}/js/buscarPistaValidation.js" defer></script>
</head>
<body>
    <h1>Buscar Pistas Disponibles</h1>
    <form id="buscarPistasForm" action="${pageContext.request.contextPath}/client/buscarPistaDisponible" method="GET">
        <label for="tamano">Tamaño de la pista:</label>
        <select name="tamano" id="tamano">
            <option value="">Cualquiera</option>
            <option value="MINIBASKET">Minibasket</option>
            <option value="ADULTOS">Adultos</option>
            <option value="_3VS3">3 vs 3</option>
        </select>
        <br><br>
        
        <label for="exterior">¿Exterior?</label>
        <select name="exterior" id="exterior">
            <option value="">Cualquiera</option>
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select>
        <br><br>
        
	<label for="fecha">Fecha de búsqueda:</label>
	<input type="date" name="fecha" id="fecha" required><br><br>

        
        <button type="submit">Buscar Pistas</button>
    </form>
    
    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="../client/clientHome.jsp" class="nav-button">Volver al Menú Principal</a>
    </div>
    
</body>
</html>
