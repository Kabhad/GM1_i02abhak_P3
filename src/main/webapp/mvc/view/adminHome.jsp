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
<a href="../controller/LogoutController.jsp">Cerrar sesión</a>
<a href="../view/modifyUser.jsp">Modificar datos</a>