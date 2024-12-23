<%@ page session="true" %>
<%
    // Invalidar la sesi�n actual para cerrar sesi�n
    if (session != null) {
        session.invalidate();
    }

    // Redirigir al inicio de sesi�n despu�s de cerrar sesi�n
    response.sendRedirect(request.getContextPath() + "/index.jsp");
%>
