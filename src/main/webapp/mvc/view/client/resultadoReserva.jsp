<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uco.pw.display.javabean.ReservaBean" %> <!-- Importa la clase ReservaBean para acceder a los datos de la reserva -->
<!DOCTYPE html>
<html>
<head>
    <title>Reserva Realizada</title>
    <!-- Vincula la hoja de estilos específica para la vista de resultado de reserva -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/resultadoReserva.css">
</head>
<body>
    <!-- Título principal de la página -->
    <h1>Reserva Realizada</h1>
    <%
        // Obtiene el objeto "reserva" desde los atributos de la solicitud
        ReservaBean reserva = (ReservaBean) request.getAttribute("reserva");
        // Verifica si la reserva no es nula antes de mostrar los datos
        if (reserva != null) {
    %>
        <!-- Sección para mostrar un resumen de la reserva realizada -->
        <div class="resumen">
            <p><strong>ID de Reserva:</strong> <%= reserva.getIdReserva() %></p> <!-- Muestra el ID de la reserva -->
            <p><strong>Fecha y Hora:</strong> <%= reserva.getFechaHora() %></p> <!-- Muestra la fecha y hora de la reserva -->
            <p><strong>Duración:</strong> <%= reserva.getDuracionMinutos() %> minutos</p> <!-- Muestra la duración en minutos -->
            <p><strong>Pista:</strong> Pista <%= reserva.getIdPista() %></p> <!-- Muestra el ID de la pista reservada -->
            <p><strong>Adultos:</strong> <%= reserva.getNumeroAdultos() %></p> <!-- Muestra el número de adultos en la reserva -->
            <p><strong>Niños:</strong> <%= reserva.getNumeroNinos() %></p> <!-- Muestra el número de niños en la reserva -->
            <p><strong>Precio:</strong> <%= String.format("%.2f", reserva.getPrecio()) %> €</p> <!-- Muestra el precio total de la reserva -->
            <p><strong>Descuento Aplicado:</strong> <%= (int)(reserva.getDescuento() * 100) %> %</p> <!-- Muestra el descuento aplicado como porcentaje -->

            <% 
            // Verifica si la reserva tiene un bono asociado
            if (reserva.getIdBono() != null) { 
            %>
                <!-- Sección específica para mostrar detalles del bono asociado -->
                <h3>Información del Bono Asociado</h3>
                <p><strong>ID del Bono:</strong> <%= reserva.getIdBono() %></p> <!-- Muestra el ID del bono -->
                <p><strong>Sesión Utilizada:</strong> <%= reserva.getNumeroSesion() %></p> <!-- Muestra la sesión del bono utilizada -->
            <% } else { %>
                <!-- Mensaje alternativo si el bono ha sido completamente utilizado -->
                <h3>Bono Completamente Utilizado</h3>
                <p>El bono ha sido completamente utilizado con esta reserva.</p>
            <% } %>
        </div>
    <%
        } else { 
        // Mensaje alternativo en caso de que no se haya podido procesar la reserva
    %>
        <p>Ocurrió un error al procesar tu reserva. Inténtalo nuevamente.</p>
    <%
        }
    %>
    <!-- Sección de botones para navegar a otras acciones -->
    <div class="botones">
        <!-- Enlace para realizar otra reserva estándar -->
        <a href="../mvc/view/client/realizarReserva.jsp" class="btn-secondary">Hacer Otra Reserva</a>
        <!-- Enlace para realizar otra reserva utilizando bono -->
        <a href="<%= request.getContextPath() %>/client/realizarReservaBono" class="btn-secondary">Hacer Otra Reserva de Bono</a>
        <!-- Enlace para volver al menú principal -->
        <a href="../mvc/view/client/clientHome.jsp" class="btn-secondary">Volver al Menú Principal</a>
    </div>
</body>
</html>
