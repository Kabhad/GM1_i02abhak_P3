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
    
    if (nuevoNombre == null || nuevoNombre.trim().isEmpty() || 
        nuevaContrasena == null || nuevaContrasena.trim().isEmpty()) {
        response.sendRedirect("../../include/modifyError.jsp?message=Campos obligatorios vac�os.");
        return;
    }

    if (!nuevoNombre.matches("^[a-zA-Z\\s]+$")) {
        response.sendRedirect("../../include/modifyError.jsp?message=El nombre solo puede contener letras.");
        return;
    }

    if (nuevaContrasena.length() < 6 || 
        !nuevaContrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")) {
        response.sendRedirect("../../include/modifyError.jsp?message=La contrase�a no cumple con los requisitos.");
        return;
    }
        
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
        if ("administrador".equals(customer.getTipoUsuario())) {
            response.sendRedirect("../view/adminHome.jsp");
        } else {
            response.sendRedirect("../view/clientHome.jsp");
        }
    } else {
        // Redirigir a una p�gina de error en caso de fallo
        response.sendRedirect("../../include/modifyError.jsp?message=" + mensaje);
    }
%>
