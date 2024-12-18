package es.uco.pw.servlet.filters;

import es.uco.pw.display.javabean.CustomerBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*") // Intercepta todas las solicitudes
public class AccessControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false); // Obtener la sesión actual
        String path = httpRequest.getRequestURI(); // Obtener la URL solicitada
        String contextPath = httpRequest.getContextPath(); // Contexto de la aplicación

     // Rutas públicas que no requieren autenticación
        if (path.startsWith(contextPath + "/index.jsp") || 
            path.startsWith(contextPath + "/include/") || 
            path.startsWith(contextPath + "/css/") || 
            path.startsWith(contextPath + "/img/") || 
            path.startsWith(contextPath + "/mvc/view/login.jsp") || 
            path.startsWith(contextPath + "/mvc/view/register.jsp") || 
            path.startsWith(contextPath + "/mvc/controller/LogoutController.jsp") || 
            path.startsWith(contextPath + "/mvc/controller/LoginController.jsp") ||
            path.startsWith(contextPath + "/mvc/controller/RegisterController.jsp"))
        {
            chain.doFilter(request, response); // Continuar sin verificar permisos
            return;
        }


        // Verificar autenticación
        CustomerBean usuario = (session != null) ? (CustomerBean) session.getAttribute("customer") : null;
        if (usuario == null) {
            // Redirigir a página de error si no hay usuario en sesión
            request.setAttribute("error", "Debe iniciar sesión para acceder a esta página.");
            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
            return;
        }

        // Verificar permisos por rol y ruta
        String rol = usuario.getTipoUsuario().toUpperCase(); // Convertir rol a mayúsculas para consistencia
        if (path.startsWith(contextPath + "/admin") && !"ADMINISTRADOR".equals(rol)) {
            request.setAttribute("error", "Acceso denegado. Solo los administradores pueden acceder a esta página.");
            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
            return;
        }

        if (path.startsWith(contextPath + "/client") && !"CLIENTE".equals(rol) && !"ADMINISTRADOR".equals(rol)) {
            request.setAttribute("error", "Acceso denegado. Solo los clientes pueden acceder a esta página.");
            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
            return;
        }

        // Continuar con la solicitud si pasa las verificaciones
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Método opcional
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Método opcional
    }
}
