<%@ page import="es.uco.pw.business.jugador.JugadorDTO" %>
<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%
    // Obtener los datos enviados desde el formulario de registro
    String nombre = request.getParameter("nombre");
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");
    String fechaNacimientoStr = request.getParameter("fechaNacimiento");

    // Convertir la fecha de nacimiento de String a Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaNacimiento = null;
    try {
        fechaNacimiento = sdf.parse(fechaNacimientoStr);
    } catch (Exception e) {
        // Redirigir a un error si la fecha es inválida
        response.sendRedirect(request.getContextPath() + "/include/registerError.jsp?error=Fecha inválida.");
        return;
    }

    // Validar que la fecha no sea futura ni mayor a 80 años atrás
    Calendar hoy = Calendar.getInstance();
    Calendar limiteMin = Calendar.getInstance();
    limiteMin.add(Calendar.YEAR, -80);

    if (fechaNacimiento.after(hoy.getTime())) {
        response.sendRedirect(request.getContextPath() + "/include/registerError.jsp?error=La fecha de nacimiento no puede ser futura.");
        return;
    }

    if (fechaNacimiento.before(limiteMin.getTime())) {
        response.sendRedirect(request.getContextPath() + "/include/registerError.jsp?error=La fecha de nacimiento debe ser dentro de los últimos 80 años.");
        return;
    }

    // Continuar con el registro del jugador
    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);
    JugadorDTO nuevoJugador = new JugadorDTO();
    nuevoJugador.setNombreApellidos(nombre);
    nuevoJugador.setCorreoElectronico(correo);
    nuevoJugador.setContrasena(contrasena);
    nuevoJugador.setTipoUsuario("cliente");
    nuevoJugador.setFechaNacimiento(fechaNacimiento);

    // Intentar registrar al nuevo jugador en la base de datos
    String mensaje = jugadoresDAO.altaJugador(nuevoJugador);

    if (mensaje.contains("exito")) {
        // Redirigir al login con un mensaje de éxito
        request.setAttribute("successMessage", "Registrado con éxito. Por favor, inicia sesión.");
        request.getRequestDispatcher("/mvc/view/login.jsp").forward(request, response);
    } else {
        // Redirigir a un error si falla el registro
        request.setAttribute("errorMessage", mensaje);
        response.sendRedirect(request.getContextPath() + "/include/registerError.jsp?error=" + mensaje);
    }
%>
