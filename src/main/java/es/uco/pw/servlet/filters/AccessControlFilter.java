package es.uco.pw.servlet.filters;

import es.uco.pw.display.javabean.CustomerBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filtro de control de acceso para gestionar permisos de usuarios en la aplicación.
 * 
 * <p>Este filtro se aplica a todas las solicitudes y verifica si el usuario tiene
 * los permisos necesarios para acceder a las distintas secciones de la aplicación.</p>
 */
@WebFilter("/*") // Intercepta todas las solicitudes
public class AccessControlFilter implements Filter {

    /**
     * Filtra las solicitudes y valida los permisos de acceso según el rol del usuario.
     *
     * @param request  La solicitud HTTP del cliente.
     * @param response La respuesta HTTP del servidor.
     * @param chain    La cadena de filtros a ejecutar.
     * @throws IOException      Si ocurre un error en la entrada/salida.
     * @throws ServletException Si ocurre un error en el procesamiento del servlet.
     */
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
            path.startsWith(contextPath + "/js/") || // Agregada carpeta js
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

	    // Obtener rol del usuario autenticado
	    String rol = (usuario.getTipoUsuario() != null) ? usuario.getTipoUsuario().toUpperCase() : "";
	    
	    // Excepción para la página modifyUser.jsp
	    if (path.startsWith(contextPath + "/mvc/view/modifyUser.jsp")) {
	        chain.doFilter(request, response); // Continuar, acceso permitido a ambos roles
	        return;
	    }

	    // Validar acceso a servlets
	    if (path.startsWith(contextPath + "/admin")) {
	        if (!"ADMINISTRADOR".equals(rol)) {
	            request.setAttribute("error", "Acceso denegado. Solo los administradores pueden acceder a esta página.");
	            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
	            return;
	        }
	    } else if (path.startsWith(contextPath + "/client")) {
	        if (!"CLIENTE".equals(rol)) { // Cambiado para que solo CLIENTE pueda acceder
	            request.setAttribute("error", "Acceso denegado. Solo los clientes pueden acceder a esta página.");
	            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
	            return;
	        }
	    }

	    // Validar acceso a vistas
	    if (path.startsWith(contextPath + "/mvc/view/")) {
	        if (path.startsWith(contextPath + "/mvc/view/admin")) {
	            if (!"ADMINISTRADOR".equals(rol)) {
	                request.setAttribute("error", "Acceso denegado. Solo los administradores pueden acceder a esta vista.");
	                request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
	                return;
	            }
	        } else if (path.startsWith(contextPath + "/mvc/view/client")) {
	            if (!"CLIENTE".equals(rol)) { // Cambiado para que solo CLIENTE pueda acceder
	                request.setAttribute("error", "Acceso denegado. Solo los clientes pueden acceder a esta vista.");
	                request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
	                return;
	            }
	        } else {
	            // Si la vista no está ni en /admin ni en /client, se considera acceso denegado
	            request.setAttribute("error", "Acceso denegado. La página solicitada no está permitida.");
	            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
	            return;
	        }
	    }

	    // Continuar con la solicitud si pasa las verificaciones
	    chain.doFilter(request, response);
	}


    /**
     * Método llamado cuando se destruye el filtro.
     */
    @Override
    public void destroy() {
        // Método opcional
    }

    /**
     * Inicializa el filtro.
     *
     * @param arg0 Configuración del filtro.
     * @throws ServletException Si ocurre un error en la inicialización.
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Método opcional
    }
}
