<%@ page session="true" %>
<%
    // Invalidar la sesión actual para cerrar sesión
    if (session != null) {
        session.invalidate();
    }

    // Redirigir al inicio de sesión después de cerrar sesión
    response.sendRedirect(request.getContextPath() + "/index.jsp");
%>
