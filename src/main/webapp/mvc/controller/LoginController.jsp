<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.data.dao.ReservasDAO" %>
<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>

<%
    // Obtener los parámetros ingresados por el usuario en el formulario de inicio de sesión
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");

    // Instanciar los DAOs necesarios para manejar jugadores y reservas
    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);
    ReservasDAO reservasDAO = new ReservasDAO(application);

    // Intentar autenticar al jugador utilizando su correo y contraseña
    JugadorDTO jugador = jugadoresDAO.autenticarJugador(correo, contrasena);

    if (jugador != null && jugador.isCuentaActiva()) {
        // Crear un CustomerBean para almacenar los datos del usuario autenticado en la sesión
        String proximaReserva = null;
        if ("CLIENTE".equalsIgnoreCase(jugador.getTipoUsuario())) {
            // Solo los clientes necesitan obtener la próxima reserva
            proximaReserva = reservasDAO.obtenerProximaReserva(jugador.getCorreoElectronico());
        }

        CustomerBean customer = new CustomerBean(
            jugador.getNombreApellidos(),
            jugador.getCorreoElectronico(),
            jugador.getTipoUsuario().toUpperCase(), // Tipo de usuario en mayúsculas
            jugador.getFechaInscripcion() != null ? jugador.getFechaInscripcion().toString() : "No disponible",
            proximaReserva != null ? proximaReserva : "No disponible"
        );

        // Almacenar el objeto CustomerBean en la sesión
        session.setAttribute("customer", customer);

        // Redirigir según el tipo de usuario autenticado
        if ("ADMINISTRADOR".equalsIgnoreCase(customer.getTipoUsuario())) {
            response.sendRedirect(request.getContextPath() + "/admin/listarJugadores");
        } else if ("CLIENTE".equalsIgnoreCase(customer.getTipoUsuario())) {
            response.sendRedirect("../view/client/clientHome.jsp");
        } else {
            // Redirigir al login con un error si el tipo de usuario es desconocido
            request.setAttribute("error", "Tipo de usuario desconocido.");
            request.getRequestDispatcher("../../include/loginError.jsp").forward(request, response);
        }
    } else {
        // Redirigir al loginError.jsp si las credenciales son inválidas
        request.setAttribute("error", "Credenciales inválidas. Por favor, inténtalo nuevamente.");
        request.getRequestDispatcher("../../include/loginError.jsp").forward(request, response);
    }
%>
