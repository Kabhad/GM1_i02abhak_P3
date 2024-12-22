<%@ page import="es.uco.pw.data.dao.JugadoresDAO" %>
<%@ page import="es.uco.pw.display.javabean.CustomerBean" %>
<%
    // Obtener el CustomerBean de la sesi�n
    CustomerBean customer = (CustomerBean) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../view/login.jsp");
        return;
    }

    // Obtener los par�metros del formulario
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
    if (mensaje.contains("�xito")) {
        customer.setNombre(nuevoNombre);
        session.setAttribute("customer", customer);

        // Verificar el tipo de usuario para redirigir
        if ("administrador".equalsIgnoreCase(customer.getTipoUsuario())) {
            response.sendRedirect(request.getContextPath() + "/admin/listarJugadores");
        } else {
            response.sendRedirect("../view/client/clientHome.jsp");
        }
    } else {
        // Redirigir a una p�gina de error en caso de fallo
        response.sendRedirect("../../include/modifyError.jsp?message=" + mensaje);
    }
%>
