<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../view/login.jsp");
        return;
    }

    LocalDate fechaActual = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaActualFormateada = fechaActual.format(formatter);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio del Cliente</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/clientHome.css">
</head>
<body>
    <div class="container">
        <!-- Botón de cerrar sesión -->
        <a href="../../controller/LogoutController.jsp" id="btnCerrarSesion">
            <img src="<%= request.getContextPath() %>/images/logout.png" alt="Cerrar sesión" class="logout-icon">
        </a>

        <!-- Contenido principal -->
        <div class="main-content">
            <h2>Bienvenido, <%= customer.getNombre() %>!</h2>

		    <!-- Contenedor de información -->
		    <div class="user-info">
		        <div class="user-info-card">
		            <div class="user-info-row">
		                <span>Correo:</span>
		                <span>amartinez@example.com</span>
		            </div>
		            <div class="user-info-row">
		                <span>Fecha actual:</span>
		                <span>22/12/2024</span>
		            </div>
		            <div class="user-info-row">
		                <span>Fecha de inscripción:</span>
		                <span>2024-10-12</span>
		            </div>
		            <div class="user-info-row">
		                <span>Próxima reserva:</span>
		                <span>2024-12-24 19:00:00</span>
		            </div>
		        </div>
		    </div>
            <!-- Botones de navegación -->
            <div class="button-container">
                <a href="../../view/modifyUser.jsp">Modificar datos</a>
                <a href="../client/consultarReservas.jsp">Consultar reservas</a>
                <a href="../../view/client/realizarReserva.jsp">Realizar reserva</a>
                <a href="<%= request.getContextPath() %>/client/realizarReservaBono">Realizar reserva con bono</a>
                <a href="../client/buscarPistaDisponible.jsp">Buscar pistas disponibles</a>
                <a href="<%= request.getContextPath() %>/client/modificarReserva?filtrarReservas=true">Modificar reserva</a>
                <a href="<%= request.getContextPath() %>/client/cancelarReserva">Cancelar reserva</a>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        &copy; 2024 Gestión de Pistas de Basket. Todos los derechos reservados.
    </footer>
</body>
</html>

