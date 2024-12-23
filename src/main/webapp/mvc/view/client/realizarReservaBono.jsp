<!DOCTYPE html>
<html>
<head>
    <title>Realizar Reserva con Bono</title>
    <!-- Vincula las hojas de estilos para esta p�gina -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/realizarReservaBono.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/realizarReserva.css">
    <!-- Enlace al script de validaci�n -->
    <script src="${pageContext.request.contextPath}/js/realizarReservaValidation.js"></script>
    <script>
        /**
         * Funci�n para actualizar las pistas disponibles en funci�n del tipo de reserva,
         * fecha/hora seleccionada y duraci�n.
         * Si se cumplen las condiciones, env�a autom�ticamente el formulario de filtro.
         */
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
    <!-- T�tulo principal de la p�gina -->
    <h1>Realizar una Reserva Utilizando Bono</h1>

    <!-- Muestra informaci�n sobre el bono si el atributo "tieneBono" est� presente -->
    <% 
        Boolean tieneBono = (Boolean) request.getAttribute("tieneBono");
        if (tieneBono != null && tieneBono) { 
    %>
        <div class="informacion-bono">
            <h3>Informaci�n del Bono Asociado</h3>
            <p><strong>ID del Bono:</strong> <%= request.getAttribute("idBono") %></p> <!-- Muestra el ID del bono -->
            <p><strong>Sesiones Disponibles:</strong> <%= request.getAttribute("sesionesRestantes") %></p> <!-- Muestra las sesiones restantes del bono -->
            <p><strong>Fecha de Caducidad:</strong> <%= request.getAttribute("fechaCaducidad") %></p> <!-- Muestra la fecha de caducidad del bono -->
        </div>
    <% 
        } 
    %>

    <!-- Formulario para filtrar las pistas en funci�n de los par�metros seleccionados -->
    <form id="filtroFormulario" action="${pageContext.request.contextPath}/client/realizarReservaBono" method="get">
        <label for="tipoReserva">Tipo de Reserva:</label><br>
        <!-- Selector para elegir el tipo de reserva -->
        <select id="tipoReserva" name="tipoReserva" onchange="actualizarPistas()" required>
            <option value="" disabled ${empty param.tipoReserva ? 'selected' : ''}>Selecciona un tipo</option>
            <option value="adulto" ${param.tipoReserva == 'adulto' ? 'selected' : ''}>Adulto</option>
            <option value="infantil" ${param.tipoReserva == 'infantil' ? 'selected' : ''}>Infantil</option>
            <option value="familiar" ${param.tipoReserva == 'familiar' ? 'selected' : ''}>Familiar</option>
        </select><br><br>

        <label for="fechaHoraFiltro">Fecha y Hora:</label><br>
        <!-- Campo para seleccionar la fecha y hora de la reserva -->
        <input type="datetime-local" id="fechaHoraFiltro" name="fechaHora" value="${param.fechaHora}" onchange="actualizarPistas()" required><br><br>

        <label for="duracionFiltro">Duraci�n:</label><br>
        <!-- Selector para elegir la duraci�n de la reserva -->
        <select id="duracionFiltro" name="duracion" onchange="actualizarPistas()" required>
            <option value="60" ${param.duracion == '60' ? 'selected' : ''}>1 Hora</option>
            <option value="90" ${param.duracion == '90' ? 'selected' : ''}>1 Hora y 30 Minutos</option>
            <option value="120" ${param.duracion == '120' ? 'selected' : ''}>2 Horas</option>
        </select><br><br>
    </form>

    <!-- Formulario principal para enviar los datos de la reserva -->
    <form action="${pageContext.request.contextPath}/client/realizarReservaBono" method="post">
        <label for="idPista">Pista Disponible:</label><br>
        <!-- Selector din�mico con las opciones de pistas disponibles -->
        <select id="idPista" name="idPista" required>
            <option value="" disabled selected>Selecciona una pista</option>
            ${opcionesPistas} <!-- Inserta las opciones de pistas generadas din�micamente -->
        </select><br><br>

        <!-- Campo oculto para enviar la fecha y hora seleccionadas -->
        <input type="hidden" id="fechaHora" name="fechaHora" value="${param.fechaHora}" />

        <!-- Campo oculto para enviar la duraci�n seleccionada -->
        <input type="hidden" id="duracion" name="duracion" value="${param.duracion}" />

        <label for="numeroAdultos">N�mero de Adultos:</label><br>
        <!-- Campo para ingresar el n�mero de adultos en la reserva -->
        <input type="number" id="numeroAdultos" name="numeroAdultos" min="0" required><br><br>

        <label for="numeroNinos">N�mero de Ni�os:</label><br>
        <!-- Campo para ingresar el n�mero de ni�os en la reserva -->
        <input type="number" id="numeroNinos" name="numeroNinos" min="0" required><br><br>

        <!-- Bot�n para enviar el formulario -->
        <button type="submit" class="btn-primary">Realizar Reserva con Bono</button>
    </form>

    <!-- Mensaje de confirmaci�n, si est� presente -->
    <% String mensaje = (String) request.getAttribute("mensaje"); %>
    <% if (mensaje != null) { %>
        <p class="confirmacion-mensaje"><%= mensaje %></p>
    <% } %>

    <!-- Campo oculto repetido para fecha y hora (por seguridad) -->
    <input type="hidden" id="fechaHora" name="fechaHora" value="${param.fechaHora}" />

    <!-- Campo oculto repetido para duraci�n (por seguridad) -->
    <input type="hidden" id="duracion" name="duracion" value="${param.duracion}" />

    <!-- Bot�n para regresar al men� principal -->
    <br>
    <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Men� Principal</a>
</body>
</html>
