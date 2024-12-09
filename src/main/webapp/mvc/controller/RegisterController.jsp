<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
    // Par�metros recibidos del formulario
    String nombre = request.getParameter("nombre");
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");
    String fechaNacimientoStr = request.getParameter("fechaNacimiento");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaNacimiento = null;
    try {
        fechaNacimiento = sdf.parse(fechaNacimientoStr);
    } catch (Exception e) {
        response.sendRedirect("../view/register.jsp?error=Fecha inv�lida.");
        return;
    }

    // Instanciar DAO pasando el contexto de la aplicaci�n
    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);

    // Crear un nuevo jugador con tipo de usuario "cliente" (por defecto)
    JugadorDTO nuevoJugador = new JugadorDTO();
    nuevoJugador.setNombreApellidos(nombre);
    nuevoJugador.setCorreoElectronico(correo);
    nuevoJugador.setContrasena(contrasena);
    nuevoJugador.setTipoUsuario("cliente"); // Fijado a "cliente"
    nuevoJugador.setFechaNacimiento(fechaNacimiento);

    // Registrar al jugador
    String mensaje = jugadoresDAO.altaJugador(nuevoJugador);

    if (mensaje.contains("�xito")) {
        response.sendRedirect("../view/login.jsp?success=Registrado con �xito. Por favor, inicia sesi�n.");
    } else {
        response.sendRedirect("../view/register.jsp?error=" + mensaje);
    }
%>
