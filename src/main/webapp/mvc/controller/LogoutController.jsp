<%@ page session="true" %>
<%
    // Invalidar la sesi�n actual
    if (session != null) {
        session.invalidate();
    }

    // Redirigir al login
	response.sendRedirect(request.getContextPath() + "/index.jsp");
%>
