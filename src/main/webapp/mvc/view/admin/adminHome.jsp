<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../view/login.jsp");
        return;
    }

    LocalDate fechaActual = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaActualFormateada = fechaActual.format(formatter);
    
    // Obtener datos del cliente
    String correo = customer.getCorreo();
    String fechaInscripcion = customer.getFechaInscripcion(); // Suponiendo que es un String
%>
<!DOCTYPE html>
<html lang="en">
<head> 
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Panel de Administración</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminHome.css">
</head>

<body>
	<div class="container">
		<!-- Cerrar sesión (botón en la esquina superior derecha) -->
		<a href="<%= request.getContextPath() %>/mvc/controller/LogoutController.jsp" id="btnCerrarSesion">
		    <img src="<%= request.getContextPath() %>/images/logout.png" alt="Cerrar sesión" class="logout-icon">
		</a>


		<!-- Contenido principal -->
		<div class="main-content">
			<h2>Panel de Administración</h2>
			<h2>¡Bienvenido, <%= customer.getNombre() %>!</h2>
			<!-- Contenedor de información -->
		    <div class="user-info">
		        <div class="user-info-card">
		            <div class="user-info-row">
		                <span>Correo:</span>
		                <span><%= correo %></span>
		            </div>
		            <div class="user-info-row">
		                <span>Fecha actual:</span>
		                <span><%= fechaActualFormateada %></span>
		            </div>
		            <div class="user-info-row">
		                <span>Fecha de inscripción:</span>
		                <span><%= fechaInscripcion != null ? fechaInscripcion : "No disponible" %></span>
		            </div>
		        </div>
		    </div>
			<h3>Lista de Clientes</h3>	
			


			<!-- Tabla para mostrar clientes -->
			<div class="lista-clientes">
				<% 
				   List<CustomerBean> clientes = (List<CustomerBean>) request.getAttribute("clientes");
				   if (clientes != null && !clientes.isEmpty()) { 
				%>
		        <table>
		            <thead>
		                <tr>
		                    <th>Nombre</th>
		                    <th>Fecha de Inscripción</th>
		                    <th>Reservas Completadas</th>
		                </tr>
		            </thead>
		            <tbody>
		                <% for (CustomerBean cliente : clientes) { %>
		                    <tr>
		                        <td><%= cliente.getNombre() %></td>
		                        <td><%= cliente.getFechaInscripcion() != null ? cliente.getFechaInscripcion() : "No inscrito" %></td>
		                        <td><%= cliente.getReservasCompletadas() %></td>
		                    </tr>
		                <% } %>
		            </tbody>
		        </table>
				<% } else { %>
					<p>No hay clientes disponibles para mostrar.</p>
				<% } %>
			</div>

			<!-- Botones de navegación debajo de la lista -->
			<div class="button-container">
				<a href="<%= request.getContextPath() %>/mvc/view/modifyUser.jsp">Modificar datos</a>
				<a href="<%= request.getContextPath() %>/admin/asociarMaterialAPista">Asociar Material A Pista</a>
				<a href="<%= request.getContextPath() %>/admin/modificarEstadoMaterial">Modificar Estado Material</a>
				<a href="<%= request.getContextPath() %>/admin/modificarEstadoPista">Modificar Estado Pista</a>
				<a href="<%= request.getContextPath() %>/mvc/view/admin/darAltaMaterial.jsp">Dar de Alta Material</a>
				<a href="<%= request.getContextPath() %>/mvc/view/admin/darAltaPista.jsp">Dar de Alta Pista</a>
				<a href="<%= request.getContextPath() %>/admin/eliminarReserva">Eliminar reservas</a>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer>
    	&copy; 2024 Gestión de Pistas de Basket. Todos los derechos reservados.
	</footer>
</body>
</html>
