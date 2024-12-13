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

        // Rutas públicas que no requieren autenticación
        if (path.startsWith("/index.jsp") || path.startsWith("/include/") || path.startsWith("/css/") || path.startsWith("/img/")) {
            chain.doFilter(request, response); // Continuar sin verificar permisos
            return;
        }

        // Verificar autenticación
        CustomerBean usuario = (session != null) ? (CustomerBean) session.getAttribute("usuario") : null;
        if (usuario == null) {
            // Redirigir a página de error si no hay usuario en sesión
            request.setAttribute("error", "Debe iniciar sesión para acceder a esta página.");
            request.getRequestDispatcher("/include/error.jsp").forward(request, response);
            return;
        }

        // Verificar permisos por rol y ruta
        String rol = usuario.getTipoUsuario();
        if (path.startsWith("/admin") && !"ADMIN".equals(rol)) {
            request.setAttribute("error", "Acceso denegado. Solo los administradores pueden acceder a esta página.");
            request.getRequestDispatcher("/include/error.jsp").forward(request, response);
            return;
        }

        if (path.startsWith("/cliente") && !"CLIENTE".equals(rol) && !"ADMIN".equals(rol)) {
            request.setAttribute("error", "Acceso denegado. Solo los clientes pueden acceder a esta página.");
            request.getRequestDispatcher("/include/error.jsp").forward(request, response);
            return;
        }

        // Continuar con la solicitud si pasa las verificaciones
        chain.doFilter(request, response);
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
