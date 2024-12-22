<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%
	/**
	 * Este script procesa la modificación de los datos de un usuario.
	 * 
	 * Pasos del flujo:
	 * 1. Comprueba si el usuario está autenticado. Si no, redirige al inicio de sesión.
	 * 2. Obtiene los datos enviados desde el formulario (nuevo nombre y nueva contraseña).
	 * 3. Usa el DAO de jugadores para actualizar la información en la base de datos.
	 * 4. Si la operación es exitosa:
	 *    - Actualiza los datos del `CustomerBean` en la sesión.
	 *    - Redirige al menú de administrador o cliente según el tipo de usuario.
	 * 5. Si hay un error, redirige a una página de error con el mensaje correspondiente.
	 */
    // Obtener el CustomerBean de la sesión
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../view/login.jsp");
        return;
    }

    // Obtener los parámetros del formulario
    String nuevoNombre = request.getParameter("nombre");
    String nuevaContrasena = request.getParameter("contrasena");

    // Instanciar el DAO de jugadores
    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);

    // Modificar el jugador en la base de datos
    String mensaje = jugadoresDAO.modificarJugador(
        customer.getCorreo(),
        nuevoNombre,
        nuevaContrasena
    );

    // Actualizar los datos del CustomerBean
    if (mensaje.contains("éxito")) {
        customer.setNombre(nuevoNombre);
        session.setAttribute("customer", customer);

        // Verificar el tipo de usuario para redirigir
        if ("administrador".equalsIgnoreCase(customer.getTipoUsuario())) {
            response.sendRedirect(request.getContextPath() + "/admin/listarJugadores");
        } else {
            response.sendRedirect("../view/client/clientHome.jsp");
        }
    } else {
        // Redirigir a una página de error en caso de fallo
        response.sendRedirect("../../include/modifyError.jsp?message=" + mensaje);
    }
%>
