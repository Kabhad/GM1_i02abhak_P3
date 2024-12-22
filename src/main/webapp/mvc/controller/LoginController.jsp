<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.data.dao.ReservasDAO" %>
<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>

<%
    // Obtener par�metros del formulario
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
            // Solo los clientes tienen pr�xima reserva
            proximaReserva = reservasDAO.obtenerProximaReserva(jugador.getCorreoElectronico());
        }

        CustomerBean customer = new CustomerBean(
            jugador.getNombreApellidos(),
            jugador.getCorreoElectronico(),
            jugador.getTipoUsuario().toUpperCase(), // Convertir el rol a may�sculas
            jugador.getFechaInscripcion() != null ? jugador.getFechaInscripcion().toString() : "No disponible",
            proximaReserva != null ? proximaReserva : "No disponible"
        );

        // Guardar CustomerBean en la sesi�n
        session.setAttribute("customer", customer);

        // Redirigir seg�n el tipo de usuario
        if ("ADMINISTRADOR".equalsIgnoreCase(customer.getTipoUsuario())) { // Comparar ignorando may�sculas
            response.sendRedirect(request.getContextPath() + "/admin/listarJugadores");
        } else if ("CLIENTE".equalsIgnoreCase(customer.getTipoUsuario())) { // Comparar ignorando may�sculas
            response.sendRedirect("../view/client/clientHome.jsp");
        } else {
            // Si el tipo de usuario es desconocido, redirigir al login con un error
            request.setAttribute("error", "Tipo de usuario desconocido.");
            request.getRequestDispatcher("../../include/loginError.jsp").forward(request, response);
        }
    } else {
        // Redirigir al loginError.jsp con mensaje de error
        request.setAttribute("error", "Credenciales inv�lidas. Por favor, int�ntalo nuevamente.");
        request.getRequestDispatcher("../../include/loginError.jsp").forward(request, response);
    }
%>
