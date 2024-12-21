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
<h2>Panel de Administración</h2>
<h3>Lista de Clientes</h3>
<pre><%= listaClientes %></pre>
<a href="../../controller/LogoutController.jsp">Cerrar sesión</a>
<a href="../../view/modifyUser.jsp">Modificar datos</a>
<a href="<%= request.getContextPath() %>/admin/asociarMaterialAPista">Asociar Material A Pista</a>
<a href="<%= request.getContextPath() %>/admin/modificarEstadoMaterial">Modificar Estado Material</a>
<a href="<%= request.getContextPath() %>/admin/modificarEstadoPista">Modificar Estado Pista</a>
<a href="../admin/darAltaMaterial.jsp" class="btn-secondary">Dar de Alta Material</a>
<a href="../admin/darAltaPista.jsp" class="btn-secondary">Dar de Alta Pista</a>
<a href="<%= request.getContextPath() %>/admin/eliminarReserva">Eliminar reservas</a>
