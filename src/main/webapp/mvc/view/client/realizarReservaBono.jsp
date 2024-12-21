<!DOCTYPE html>
<html>
<head>
    <title>Realizar Reserva con Bono</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/realizarReservaBono.css">
    <script>
        function actualizarPistas() {
            const tipoReserva = document.getElementById("tipoReserva").value;
            const fechaHora = document.getElementById("fechaHoraFiltro").value;
            const duracion = document.getElementById("duracionFiltro").value;

            if (tipoReserva && fechaHora && duracion) {
                document.getElementById("filtroFormulario").submit();
            }
        }
    </script>
</head>
<body>
    <h1>Realizar una Reserva Utilizando Bono</h1>

    <!-- Informaci�n del bono -->
    <% 
        Boolean tieneBono = (Boolean) request.getAttribute("tieneBono");
        if (tieneBono != null && tieneBono) { 
    %>
        <div class="informacion-bono">
            <h3>Informaci�n del Bono Asociado</h3>
            <p><strong>ID del Bono:</strong> <%= request.getAttribute("idBono") %></p>
            <p><strong>Sesiones Disponibles:</strong> <%= request.getAttribute("sesionesRestantes") %></p>
            <p><strong>Fecha de Caducidad:</strong> <%= request.getAttribute("fechaCaducidad") %></p>
        </div>
    <% 
        } 
    %>

    <!-- Formulario para filtrar las pistas -->
    <form id="filtroFormulario" action="${pageContext.request.contextPath}/client/realizarReservaBono" method="get">
        <label for="tipoReserva">Tipo de Reserva:</label><br>
        <select id="tipoReserva" name="tipoReserva" onchange="actualizarPistas()" required>
            <option value="" disabled ${empty param.tipoReserva ? 'selected' : ''}>Selecciona un tipo</option>
            <option value="adulto" ${param.tipoReserva == 'adulto' ? 'selected' : ''}>Adulto</option>
            <option value="infantil" ${param.tipoReserva == 'infantil' ? 'selected' : ''}>Infantil</option>
            <option value="familiar" ${param.tipoReserva == 'familiar' ? 'selected' : ''}>Familiar</option>
        </select><br><br>

        <label for="fechaHoraFiltro">Fecha y Hora:</label><br>
        <input type="datetime-local" id="fechaHoraFiltro" name="fechaHora" value="${param.fechaHora}" onchange="actualizarPistas()" required><br><br>

        <label for="duracionFiltro">Duraci�n:</label><br>
        <select id="duracionFiltro" name="duracion" onchange="actualizarPistas()" required>
            <option value="60" ${param.duracion == '60' ? 'selected' : ''}>1 Hora</option>
            <option value="90" ${param.duracion == '90' ? 'selected' : ''}>1 Hora y 30 Minutos</option>
            <option value="120" ${param.duracion == '120' ? 'selected' : ''}>2 Horas</option>
        </select><br><br>
    </form>

    <!-- Formulario principal para enviar la reserva -->
    <form action="${pageContext.request.contextPath}/client/realizarReservaBono" method="post">
        <label for="idPista">Pista Disponible:</label><br>
        <select id="idPista" name="idPista" required>
            <option value="" disabled selected>Selecciona una pista</option>
            ${opcionesPistas}
        </select><br><br>

        <!-- Fecha y hora (ya seleccionada en el filtro) -->
        <input type="hidden" id="fechaHora" name="fechaHora" value="${param.fechaHora}" />

        <!-- Duraci�n -->
        <input type="hidden" id="duracion" name="duracion" value="${param.duracion}" />

        <!-- N�mero de adultos -->
        <label for="numeroAdultos">N�mero de Adultos:</label><br>
        <input type="number" id="numeroAdultos" name="numeroAdultos" min="0" required><br><br>

        <!-- N�mero de ni�os -->
        <label for="numeroNinos">N�mero de Ni�os:</label><br>
        <input type="number" id="numeroNinos" name="numeroNinos" min="0" required><br><br>

        <!-- Bot�n de env�o -->
        <button type="submit" class="btn-primary">Realizar Reserva con Bono</button>
    </form>

    <br>
    <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Men� Principal</a>
</body>
</html>
