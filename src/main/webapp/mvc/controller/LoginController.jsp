<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");

    // Instanciar DAO y pasar el contexto
    JugadoresDAO jugadoresDAO = new JugadoresDAO();
    JugadorDTO jugador = jugadoresDAO.autenticarJugador(correo, contrasena, application);

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
