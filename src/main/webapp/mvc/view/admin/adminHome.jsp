<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%@ page import="java.util.List" %>
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
