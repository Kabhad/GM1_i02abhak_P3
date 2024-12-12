<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.data.dao.ReservasDAO" %>
<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>

<%
    // Obtener parámetros del formulario
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");

    // Instanciar DAOs
    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);
    ReservasDAO reservasDAO = new ReservasDAO(application);

    // Autenticar jugador
    JugadorDTO jugador = jugadoresDAO.autenticarJugador(correo, contrasena);

    if (jugador != null && jugador.isCuentaActiva()) {
        // Crear CustomerBean
        String proximaReserva = null;
        if ("cliente".equals(jugador.getTipoUsuario())) {
            // Solo los clientes tienen próxima reserva
            proximaReserva = reservasDAO.obtenerProximaReserva(jugador.getCorreoElectronico());
        }

        CustomerBean customer = new CustomerBean(
            jugador.getNombreApellidos(),
            jugador.getCorreoElectronico(),
            jugador.getTipoUsuario(),
            jugador.getFechaInscripcion() != null ? jugador.getFechaInscripcion().toString() : "No disponible",
            proximaReserva != null ? proximaReserva : "No disponible"
        );

        // Guardar CustomerBean en la sesión
        session.setAttribute("customer", customer);

        // Redirigir según el tipo de usuario
        if ("administrador".equals(jugador.getTipoUsuario())) {
            response.sendRedirect("../view/adminHome.jsp");
        } else {
            response.sendRedirect("../view/clientHome.jsp");
        }
    } else {
        // Redirigir al login con mensaje de error
        request.setAttribute("error", "Credenciales inválidas. Intente nuevamente.");
        request.getRequestDispatcher("../view/login.jsp").forward(request, response);
    }
%>
