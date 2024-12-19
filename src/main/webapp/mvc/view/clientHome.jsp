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
<p>Fecha de inscripción: <%= customer.getFechaInscripcion() %></p>
<p>Próxima reserva: <%= customer.getFechaProximaReserva() %></p>

<nav>
    <ul>
        <li><a href="../controller/LogoutController.jsp">Cerrar sesión</a></li>
        <li><a href="../view/modifyUser.jsp">Modificar datos</a></li>
        <li><a href="../view/consultarReservas.jsp">Consultar reservas</a></li>
        <li><a href="../view/realizarReserva.jsp">Realizar reserva</a></li>
        <li><a href="<%= request.getContextPath() %>/client/realizarReservaBono">Realizar reserva con bono</a></li>
        <li><a href="../view/buscarPistaDisponible.jsp" class="btn">Buscar Pistas Disponibles</a></li>
        <li><a href="<%= request.getContextPath() %>/client/cancelarReserva">Cancelar reserva</a></li>

        
    </ul>
</nav>
