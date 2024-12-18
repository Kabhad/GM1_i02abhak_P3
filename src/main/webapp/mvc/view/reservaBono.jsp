<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Realizar Reserva con Bono</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/realizarReserva.css">
    <script src="${pageContext.request.contextPath}/js/realizarReservaValidation.js" defer></script>
</head>
<body>
    <h1>Realizar una Reserva Utilizando Bono</h1>

    <!-- Recuperar variables -->
    <%
        Boolean tieneBono = (Boolean) request.getAttribute("tieneBono");
        String mensaje = (String) request.getAttribute("mensaje");
        String idBono = (request.getAttribute("idBono") != null) ? request.getAttribute("idBono").toString() : "";
        String sesionesRestantes = (request.getAttribute("sesionesRestantes") != null) ? request.getAttribute("sesionesRestantes").toString() : "";
        String fechaCaducidad = (request.getAttribute("fechaCaducidad") != null) ? request.getAttribute("fechaCaducidad").toString() : "";
    %>

    <!-- Mostrar mensaje si existe -->
    <% if (mensaje != null) { %>
        <p style="color: red;"><%= mensaje %></p>
    <% } %>

    <!-- Verificar si existe un bono -->
    <% if (tieneBono == null || !tieneBono) { %>
        <!-- Mensaje si no tiene bono -->
        <p>No tienes un bono activo actualmente.</p>
        <p>¿Deseas crear un bono nuevo?</p>

        <!-- Formulario para crear un bono -->
        <form action="${pageContext.request.contextPath}/client/realizarReservaBono" method="post">
            <input type="hidden" name="crearBono" value="true">
            <button type="submit" class="btn-primary">Sí, crear un bono</button>
        </form>

        <br>
        <a href="../view/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    <% } else { %>
        <!-- Formulario principal para realizar la reserva -->
        <form action="${pageContext.request.contextPath}/client/realizarReservaBono" method="post">
            <!-- Información del Bono -->
            <h3>Información de tu Bono</h3>
            <ul>
                <li><strong>ID del Bono:</strong> <%= idBono %></li>
                <li><strong>Sesiones Restantes:</strong> <%= sesionesRestantes %></li>
                <li><strong>Fecha de Caducidad:</strong> <%= fechaCaducidad %></li>
            </ul>
            <br>

            <!-- Selección de pista -->
            <label for="idPista">Pista Disponible:</label><br>
            <select id="idPista" name="idPista" required>
                <option value="" disabled selected>Selecciona una pista</option>
                <%= request.getAttribute("opcionesPistas") != null ? request.getAttribute("opcionesPistas") : "" %>
            </select><br><br>

            <!-- Fecha y hora -->
            <label for="fechaHora">Fecha y Hora:</label><br>
            <input type="datetime-local" id="fechaHora" name="fechaHora" required><br><br>

            <!-- Duración -->
            <label for="duracion">Duración:</label><br>
            <select id="duracion" name="duracion" required>
                <option value="60">1 Hora</option>
                <option value="90">1 Hora y 30 Minutos</option>
                <option value="120">2 Horas</option>
            </select><br><br>

            <!-- Número de adultos -->
            <label for="numeroAdultos">Número de Adultos:</label><br>
            <input type="number" id="numeroAdultos" name="numeroAdultos" min="0" required><br><br>

            <!-- Número de niños -->
            <label for="numeroNinos">Número de Niños:</label><br>
            <input type="number" id="numeroNinos" name="numeroNinos" min="0" required><br><br>

            <!-- Botón de envío -->
            <button type="submit" class="btn-primary">Realizar Reserva con Bono</button>
        </form>

        <!-- Botón para volver al menú principal -->
        <br>
        <a href="../view/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    <% } %>
</body>
</html>
