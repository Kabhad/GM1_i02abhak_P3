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
<h1>Bienvenido, <%= customer.getNombre() %>!</h1>
<p>Correo: <%= customer.getCorreo() %></p>
<p>Fecha actual: <%= fechaActualFormateada %></p>
<p>Fecha de inscripci�n: <%= customer.getFechaInscripcion() %></p>
<p>Pr�xima reserva: <%= customer.getFechaProximaReserva() %></p>
<a href="../controller/LogoutController.jsp">Cerrar sesi�n</a>
<a href="../view/modifyUser.jsp">Modificar datos</a>
