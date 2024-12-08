<%@ page import="es.uco.pw.data.dao.JugadoresDAO, es.uco.pw.business.jugador.JugadorDTO" %>
<%
    // Obtener parámetros del formulario
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");

    // Instanciar DAO para autenticación
    JugadoresDAO jugadoresDAO = new JugadoresDAO();
    JugadorDTO jugador = jugadoresDAO.autenticarJugador(correo, contrasena);

    if (jugador != null && jugador.isCuentaActiva()) {
        // Establecer sesión y redirigir según el tipo de usuario
        session.setAttribute("jugador", jugador);

        if (jugador.getTipoUsuario().equalsIgnoreCase("administrador")) {
            response.sendRedirect("../view/adminHome.jsp");
        } else {
            response.sendRedirect("../view/clientHome.jsp");
        }
    } else {
        // Enviar mensaje de error a la vista
        request.setAttribute("error", "Correo o contraseña incorrectos, o cuenta inactiva.");
        request.getRequestDispatcher("../view/login.jsp").forward(request, response);
    }
%>
