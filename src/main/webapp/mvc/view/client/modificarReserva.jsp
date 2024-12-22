<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.PistaBean" %>

<!DOCTYPE html>
<html>
<head>
    <title>Modificar Reserva</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modificarReservas.css">
    <script src="${pageContext.request.contextPath}/js/modificarReservaValidation.js"></script>
</head>
<body>
    <h2>Modificar Reserva</h2>
    
    <div class="button-container">
    <a href="<%= request.getContextPath() %>/client/modificarReserva?filtrarReservas=true" class="btn-secondary">Volver al listado de reservas</a>
    </div>
    
    <!-- Mostrar mensajes de éxito o error -->
    <% if (request.getAttribute("mensaje") != null) { %>
        <p style="color: green;"><%= request.getAttribute("mensaje") %></p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <!-- Formulario para filtrar pistas disponibles -->
	<form id="filtrarPistasForm" action="${pageContext.request.contextPath}/client/modificarReserva" method="get">
	    <input type="hidden" name="filtrarPistas" value="true">
	    <input type="hidden" name="idReserva" value="<%= request.getAttribute("idReserva") %>">
	    
	    <!-- Campo oculto para indicar si la reserva es de bono -->
		<input type="hidden" id="esReservaBono" value="<%= request.getAttribute("esReservaBono") != null && (boolean) request.getAttribute("esReservaBono") ? "true" : "false" %>">
	    
	    <!-- Fecha y hora -->
	    <label for="fechaHoraFiltro">Fecha y Hora:</label><br>
	    <input type="datetime-local" id="fechaHoraFiltro" name="fechaHora" 
	           value="<%= request.getAttribute("fechaHora") != null ? request.getAttribute("fechaHora") : request.getAttribute("fechaHoraOriginal") %>" onchange="actualizarPistas()" required><br><br>
	
	    <!-- Duración -->
	    <label for="duracionFiltro">Duración (minutos):</label><br>
	    <select id="duracionFiltro" name="duracion" onchange="actualizarPistas()" required>
	        <option value="60" <%= "60".equals(request.getAttribute("duracion")) ? "selected" : "" %>>60 minutos</option>
	        <option value="90" <%= "90".equals(request.getAttribute("duracion")) ? "selected" : "" %>>90 minutos</option>
	        <option value="120" <%= "120".equals(request.getAttribute("duracion")) ? "selected" : "" %>>120 minutos</option>
	    </select><br><br>
	</form>


    <!-- Formulario principal para guardar cambios -->
    <form id="modificarReservaForm" action="${pageContext.request.contextPath}/client/modificarReserva" method="post" onsubmit="return validarFormulario();">
        <input type="hidden" name="idReserva" value="<%= request.getAttribute("idReserva") %>">
		<!-- Campo oculto para almacenar la pista original -->
		<input type="hidden" id="idPistaOriginal" name="idPistaOriginal" value="<%= request.getAttribute("idPistaOriginal") %>">
		

        <!-- Fecha y hora -->
        <input type="hidden" id="fechaHora" name="nuevaFechaHora" 
               value="<%= request.getAttribute("fechaHora") != null ? request.getAttribute("fechaHora") : request.getAttribute("fechaHoraOriginal") %>">

        <!-- Duración -->
        <input type="hidden" id="duracion" name="nuevaDuracion" 
               value="<%= request.getAttribute("duracion") %>">

        <!-- Pistas disponibles -->
        <%
            boolean esReservaBono = request.getAttribute("esReservaBono") != null && (boolean) request.getAttribute("esReservaBono");
            List<PistaBean> pistas = (List<PistaBean>) request.getAttribute("pistasDisponibles");
        %>
        
        <% if (!esReservaBono && pistas != null && !pistas.isEmpty()) { %>
            <!-- Campo de selección de nueva pista -->
            <label for="idPistaNueva">Nueva Pista:</label><br>
            <select id="idPistaNueva" name="idPistaNueva" required>
                <% for (PistaBean pista : pistas) { %>
                    <option value="<%= pista.getIdPista() %>">
                        <%= pista.getNombrePista() %> (<%= pista.isExterior() ? "Exterior" : "Interior" %>)
                    </option>
                <% } %>
            </select><br><br>
        <% } else if (esReservaBono) { %>
            <!-- Mensaje para reservas de bono -->
            <p style="color: red;">La reserva de bono no permite cambiar de pista. Se mantendrá la pista original.</p>
            <p><strong>Pista Original:</strong> ID: <%= request.getAttribute("idPistaOriginal") %></p>
        <% } else { %>
            <p style="color: red;">No hay pistas disponibles para los criterios seleccionados.</p>
        <% } %>




        <!-- Número de adultos -->
        <label for="nuevosAdultos">Número de Adultos:</label><br>
        <input type="number" id="nuevosAdultos" name="nuevosAdultos" 
               value="<%= request.getAttribute("numeroAdultos") != null ? request.getAttribute("numeroAdultos") : 0 %>" min="0" required><br><br>

        <!-- Número de niños -->
        <label for="nuevosNinos">Número de Niños:</label><br>
        <input type="number" id="nuevosNinos" name="nuevosNinos" 
               value="<%= request.getAttribute("numeroNinos") != null ? request.getAttribute("numeroNinos") : 0 %>" min="0" required><br><br>

        <!-- Botón para guardar cambios -->
        <button type="submit" class="btn-primary">Guardar Cambios</button>
    </form>
</body>
</html>
