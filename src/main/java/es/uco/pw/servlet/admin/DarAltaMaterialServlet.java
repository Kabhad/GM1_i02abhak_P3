package es.uco.pw.servlet.admin;

import es.uco.pw.business.material.EstadoMaterial;
import es.uco.pw.business.material.TipoMaterial;
import es.uco.pw.display.javabean.MaterialBean;
import es.uco.pw.data.dao.PistasDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet para gestionar el alta de nuevos materiales deportivos.
 *
 * <p>Este servlet maneja solicitudes HTTP POST para procesar los datos enviados desde un formulario,
 * crear un nuevo material y guardarlo en la base de datos a través del DAO de pistas.</p>
 *
 * <p>En caso de éxito, redirige al listado de materiales con un mensaje de confirmación.
 * Si ocurre un error, muestra un mensaje de error en la vista correspondiente.</p>
 */
public class DarAltaMaterialServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja solicitudes HTTP POST para dar de alta un nuevo material deportivo.
     *
     * <p>Recupera los datos del formulario, crea un {@code MaterialBean}, y llama al DAO para
     * guardar el material en la base de datos. Gestiona errores y redirige según el resultado.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recuperar parámetros del formulario
            int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));
            TipoMaterial tipo = TipoMaterial.valueOf(request.getParameter("tipo"));
            boolean usoExterior = Boolean.parseBoolean(request.getParameter("usoExterior"));
            EstadoMaterial estado = EstadoMaterial.valueOf(request.getParameter("estado"));

            // Crear el bean
            MaterialBean materialBean = new MaterialBean(idMaterial, tipo, usoExterior, estado);

            // Guardar el material en la base de datos
            PistasDAO materialesDAO = new PistasDAO(getServletContext());
            materialesDAO.crearMaterial(materialBean.getId(), tipo, materialBean.isUsoExterior(), estado);

            // Redirigir al listado con mensaje de éxito
            request.setAttribute("mensaje", "Material creado con éxito.");
            request.getRequestDispatcher("/admin/listarMateriales").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al crear el material: " + e.getMessage());
            request.getRequestDispatcher("/include/darAltaMaterialError.jsp").forward(request, response);
        }
    }
}
