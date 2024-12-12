<%@ page session="true" %>
<%
    // Invalidar la sesión actual
    if (session != null) {
        session.invalidate();
    }

    // Redirigir al login
    response.sendRedirect("../view/login.jsp");
%>
