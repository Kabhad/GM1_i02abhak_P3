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
        if ("CLIENTE".equalsIgnoreCase(jugador.getTipoUsuario())) {
            // Solo los clientes tienen próxima reserva
            proximaReserva = reservasDAO.obtenerProximaReserva(jugador.getCorreoElectronico());
        }

        CustomerBean customer = new CustomerBean(
            jugador.getNombreApellidos(),
            jugador.getCorreoElectronico(),
            jugador.getTipoUsuario().toUpperCase(), // Convertir el rol a mayúsculas
            jugador.getFechaInscripcion() != null ? jugador.getFechaInscripcion().toString() : "No disponible",
            proximaReserva != null ? proximaReserva : "No disponible"
        );

        // Guardar CustomerBean en la sesión
        session.setAttribute("customer", customer);

        // Redirigir según el tipo de usuario
        if ("ADMINISTRADOR".equalsIgnoreCase(customer.getTipoUsuario())) { // Comparar ignorando mayúsculas
            response.sendRedirect(request.getContextPath() + "/admin/listarJugadores");
        } else if ("CLIENTE".equalsIgnoreCase(customer.getTipoUsuario())) { // Comparar ignorando mayúsculas
            response.sendRedirect("../view/client/clientHome.jsp");
        } else {
            // Si el tipo de usuario es desconocido, redirigir al login con un error
            request.setAttribute("error", "Tipo de usuario desconocido.");
            request.getRequestDispatcher("../../include/loginError.jsp").forward(request, response);
        }
    } else {
        // Redirigir al loginError.jsp con mensaje de error
        request.setAttribute("error", "Credenciales inválidas. Por favor, inténtalo nuevamente.");
        request.getRequestDispatcher("../../include/loginError.jsp").forward(request, response);
    }
%>
