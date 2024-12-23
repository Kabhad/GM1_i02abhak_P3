<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Reservas</title>
    <!-- Enlace al script de validación del formulario -->
    <script src="<%= request.getContextPath() %>/js/consultarReservasValidation.js" defer></script>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/consultarReservas.css">
</head>
<body>
    <h1>Consulta de Reservas</h1>
    
    <!-- Formulario para consultar reservas -->
    <form action="${pageContext.request.contextPath}/client/consultarReserva" method="post" onsubmit="return validarFechas()">
        <!-- Campo para seleccionar la fecha de inicio -->
        <label for="fechaInicio">Fecha Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" required>
        
        <!-- Campo para seleccionar la fecha de fin -->
        <label for="fechaFin">Fecha Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" required>
        
        <!-- Botón para enviar el formulario -->
        <button type="submit">Consultar Reservas</button>
    </form>
    
    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="../client/clientHome.jsp" class="nav-button">Volver al Menú Principal</a>
    </div>
</body>
</html>
