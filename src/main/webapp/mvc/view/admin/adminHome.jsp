<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
	if (customer == null || !"ADMINISTRADOR".equalsIgnoreCase(customer.getTipoUsuario())) {
	    response.sendRedirect("../view/login.jsp");
	    return;
	}

    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);
    String listaClientes = jugadoresDAO.listarJugadoresConReservas();
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
		<a href="../../controller/LogoutController.jsp" id="btnCerrarSesion">
    		<img src="<%= request.getContextPath() %>/images/logout.png" alt="Cerrar sesión" class="logout-icon">
		</a>



		<!-- Contenido principal -->
		<div class="main-content">
			<h2>Panel de Administración</h2>
			<h3>Lista de Clientes</h3>	
			<!-- Caja para la lista de clientes con scroll -->
			<div class="lista-clientes">
				<pre><%= listaClientes %></pre>
			</div>

			<!-- Botones de navegación debajo de la lista -->
			<div class="button-container">
				<a href="../../view/modifyUser.jsp">Modificar datos</a>
				<a href="<%= request.getContextPath() %>/admin/asociarMaterialAPista">Asociar Material A Pista</a>
				<a href="<%= request.getContextPath() %>/admin/modificarEstadoMaterial">Modificar Estado Material</a>
				<a href="<%= request.getContextPath() %>/admin/modificarEstadoPista">Modificar Estado Pista</a>
				<a href="../admin/darAltaMaterial.jsp">Dar de Alta Material</a>
				<a href="../admin/darAltaPista.jsp">Dar de Alta Pista</a>
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
