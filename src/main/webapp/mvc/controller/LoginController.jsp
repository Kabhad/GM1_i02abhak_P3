<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");

    // Instanciar DAO pasando el contexto de la aplicación
    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);
    JugadorDTO jugador = jugadoresDAO.autenticarJugador(correo, contrasena);

    if (jugador != null && jugador.isCuentaActiva()) {
        session.setAttribute("jugador", jugador);
        if ("administrador".equals(jugador.getTipoUsuario())) {
            response.sendRedirect("../view/adminHome.jsp");
        } else {
            response.sendRedirect("../view/clientHome.jsp");
        }
    } else {
        request.setAttribute("error", "Correo o contraseña incorrectos.");
        request.getRequestDispatcher("../view/login.jsp").forward(request, response);
    }
%>
