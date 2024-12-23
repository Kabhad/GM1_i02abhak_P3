<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Realizar Reserva</title>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/realizarReserva.css">
    <!-- Enlace al script de validación -->
    <script src="${pageContext.request.contextPath}/js/realizarReservaValidation.js"></script>
</head>
<body>
    <h1>Realizar una Nueva Reserva</h1>

    <!-- Formulario para filtrar las pistas -->
    <form id="filtroFormulario" action="${pageContext.request.contextPath}/client/realizarReserva" method="get">
        <!-- Selección del tipo de reserva -->
        <label for="tipoReserva">Tipo de Reserva:</label><br>
        <select id="tipoReserva" name="tipoReserva" onchange="actualizarPistas()" required>
            <option value="" disabled ${empty param.tipoReserva ? 'selected' : ''}>Selecciona un tipo</option>
            <option value="adulto" ${param.tipoReserva == 'adulto' ? 'selected' : ''}>Adulto</option>
            <option value="infantil" ${param.tipoReserva == 'infantil' ? 'selected' : ''}>Infantil</option>
            <option value="familiar" ${param.tipoReserva == 'familiar' ? 'selected' : ''}>Familiar</option>
        </select><br><br>

        <!-- Selección de fecha y hora -->
        <label for="fechaHoraFiltro">Fecha y Hora:</label><br>
        <input type="datetime-local" id="fechaHoraFiltro" name="fechaHora" value="${param.fechaHora}" onchange="actualizarPistas()" required><br><br>

        <!-- Selección de duración -->
        <label for="duracionFiltro">Duración:</label><br>
        <select id="duracionFiltro" name="duracion" onchange="actualizarPistas()" required>
            <option value="60" ${param.duracion == '60' ? 'selected' : ''}>1 Hora</option>
            <option value="90" ${param.duracion == '90' ? 'selected' : ''}>1 Hora y 30 Minutos</option>
            <option value="120" ${param.duracion == '120' ? 'selected' : ''}>2 Horas</option>
        </select><br><br>
    </form>

    <!-- Formulario principal para enviar la reserva -->
    <form action="${pageContext.request.contextPath}/client/realizarReserva" method="post" onsubmit="return validarFormulario();">
        <!-- Selección de pista -->
        <label for="idPista">Pista Disponible:</label><br>
        <select id="idPista" name="idPista" required>
            <option value="" disabled selected>Selecciona una pista</option>
            ${opcionesPistas}
        </select><br><br>

        <!-- Fecha y hora (ya seleccionada en el filtro) -->
        <input type="hidden" id="fechaHora" name="fechaHora" value="${param.fechaHora}" />

        <!-- Duración -->
        <input type="hidden" id="duracion" name="duracion" value="${param.duracion}" />

        <!-- Número de adultos -->
        <label for="numeroAdultos">Número de Adultos:</label><br>
        <input type="number" id="numeroAdultos" name="numeroAdultos" min="0" required><br><br>

        <!-- Número de niños -->
        <label for="numeroNinos">Número de Niños:</label><br>
        <input type="number" id="numeroNinos" name="numeroNinos" min="0" required><br><br>

        <!-- Botón de envío -->
        <button type="submit" class="btn-primary">Realizar Reserva</button>
    </form>

    <!-- Botón para volver al menú principal -->
    <br>
    <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
</body>
</html>
