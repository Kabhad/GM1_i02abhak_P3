<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
    String nombre = request.getParameter("nombre");
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");
    String fechaNacimientoStr = request.getParameter("fechaNacimiento");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaNacimiento = null;
    try {
        fechaNacimiento = sdf.parse(fechaNacimientoStr);
    } catch (Exception e) {
        response.sendRedirect("../include/registerError.jsp?error=Fecha inválida.");
        return;
    }

    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);
    JugadorDTO nuevoJugador = new JugadorDTO();
    nuevoJugador.setNombreApellidos(nombre);
    nuevoJugador.setCorreoElectronico(correo);
    nuevoJugador.setContrasena(contrasena);
    nuevoJugador.setTipoUsuario("cliente");
    nuevoJugador.setFechaNacimiento(fechaNacimiento);

    String mensaje = jugadoresDAO.altaJugador(nuevoJugador);

    if (mensaje.contains("éxito")) {
        response.sendRedirect("../view/login.jsp?success=Registrado con éxito. Por favor, inicia sesión.");
    } else {
        response.sendRedirect("../include/registerError.jsp?error=" + mensaje);
    }
%>

