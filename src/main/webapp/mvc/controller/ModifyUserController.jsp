<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%
    // Recuperar el CustomerBean de la sesi�n para identificar al usuario autenticado
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        // Redirigir al login si no hay usuario autenticado
        response.sendRedirect("../view/login.jsp");
        return;
    }

    // Obtener los datos enviados desde el formulario (nombre y contrase�a)
    String nuevoNombre = request.getParameter("nombre");
    String nuevaContrasena = request.getParameter("contrasena");

    // Instanciar el DAO de jugadores para actualizar los datos en la base de datos
    JugadoresDAO jugadoresDAO = new JugadoresDAO(application);

    // Intentar modificar el usuario en la base de datos
    String mensaje = jugadoresDAO.modificarJugador(
        customer.getCorreo(),
        nuevoNombre,
        nuevaContrasena
    );

    if (mensaje.contains("�xito")) {
        // Actualizar los datos del CustomerBean en la sesi�n
        customer.setNombre(nuevoNombre);
        session.setAttribute("customer", customer);

        // Redirigir al panel correspondiente seg�n el tipo de usuario
        if ("administrador".equalsIgnoreCase(customer.getTipoUsuario())) {
            response.sendRedirect(request.getContextPath() + "/admin/listarJugadores");
        } else {
            response.sendRedirect("../view/client/clientHome.jsp");
        }
    } else {
        // Redirigir a una p�gina de error si falla la modificaci�n
        response.sendRedirect("../../include/modifyError.jsp?message=" + mensaje);
    }
%>
