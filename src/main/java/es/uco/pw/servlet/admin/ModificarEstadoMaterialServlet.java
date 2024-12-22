package es.uco.pw.servlet.admin;

import es.uco.pw.business.material.EstadoMaterial;
import es.uco.pw.business.material.MaterialDTO;
import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.display.javabean.MaterialBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet para modificar el estado de los materiales deportivos.
 *
 * <p>Este servlet permite a los administradores gestionar el estado de los materiales
 * a través de dos operaciones principales:</p>
 * <ul>
 *     <li><strong>GET:</strong> Muestra todos los materiales existentes para que puedan ser seleccionados y editados.</li>
 *     <li><strong>POST:</strong> Permite modificar el estado de un material específico en la base de datos.</li>
 * </ul>
 *
 * <p>Los materiales son gestionados a través del {@link PistasDAO} y se envían a las vistas
 * como objetos {@link MaterialBean}.</p>
 */
public class ModificarEstadoMaterialServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja solicitudes HTTP GET para mostrar todos los materiales.
     *
     * <p>Obtiene los materiales existentes desde la base de datos, los convierte a beans,
     * y los envía a la vista correspondiente para ser visualizados y gestionados.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Usar el DAO para obtener materiales
            PistasDAO materialesDAO = new PistasDAO(getServletContext());
            List<MaterialDTO> materialesDTO = materialesDAO.obtenerTodosLosMateriales();

            // Convertir DTO a Bean
            List<MaterialBean> materiales = new ArrayList<>();
            for (MaterialDTO dto : materialesDTO) {
                materiales.add(new MaterialBean(dto.getId(), dto.getTipo(), dto.isUsoExterior(), dto.getEstado()));
            }

            // Enviar materiales a la vista
            request.setAttribute("materiales", materiales);
            request.getRequestDispatcher("/mvc/view/admin/modificarEstadoMaterial.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar los materiales: " + e.getMessage());
            request.getRequestDispatcher("/mvc/view/admin/modificarEstadoMaterial.jsp").forward(request, response);
        }
    }

    /**
     * Maneja solicitudes HTTP POST para modificar el estado de un material.
     *
     * <p>Recupera los datos enviados desde el formulario, valida el nuevo estado,
     * y actualiza el estado del material en la base de datos a través del DAO.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Capturar parámetros
            int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));
            String nuevoEstadoParam = request.getParameter("nuevoEstado");

            // Validar el nuevo estado
            EstadoMaterial nuevoEstado = EstadoMaterial.valueOf(nuevoEstadoParam);

            // Actualizar el estado en la base de datos
            PistasDAO pistasDAO = new PistasDAO(getServletContext());
            pistasDAO.modificarEstadoMaterial(idMaterial, nuevoEstado);

            request.setAttribute("mensaje", "Estado del material modificado correctamente.");
        } catch (Exception e) {
            request.setAttribute("error", "Error al modificar el estado del material: " + e.getMessage());
        }

        // Recargar los materiales después de la modificación
        doGet(request, response);
    }
}

