<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../view/login.jsp");
        return;
    }
%>
<h1>Bienvenido, <%= customer.getNombre() %>!</h1>
<p>Correo: <%= customer.getCorreo() %></p>
<p>Fecha de inscripci�n: <%= customer.getFechaInscripcion() %></p>
<p>Pr�xima reserva: <%= customer.getFechaProximaReserva() %></p>
<a href="../controller/logoutController.jsp">Cerrar sesi�n</a>
<a href="../view/modifyUser.jsp">Modificar datos</a>
