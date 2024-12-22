package es.uco.pw.servlet.admin;

import es.uco.pw.business.material.MaterialDTO;
import es.uco.pw.display.javabean.MaterialBean;
import es.uco.pw.data.dao.PistasDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet para listar los materiales disponibles en el sistema.
 *
 * <p>Este servlet permite a los administradores obtener y visualizar una lista de materiales
 * disponibles en el sistema. Los datos se obtienen desde la base de datos a través del DAO
 * y se convierten en objetos {@link MaterialBean} para su uso en la vista.</p>
 */
public class ListarMaterialesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja las solicitudes HTTP GET para listar los materiales.
     *
     * <p>Obtiene todos los materiales disponibles utilizando el DAO y los transforma
     * en una lista de objetos {@link MaterialBean}, que se envían a la vista para su
     * visualización.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PistasDAO materialesDAO = new PistasDAO(getServletContext());
            List<MaterialDTO> materialesDTO = materialesDAO.obtenerTodosLosMateriales();

            // Convertir DTO a Bean
            List<MaterialBean> materiales = new ArrayList<>();
            for (MaterialDTO dto : materialesDTO) {
                materiales.add(new MaterialBean(dto.getId(), dto.getTipo(), dto.isUsoExterior(), dto.getEstado()));
            }

            request.setAttribute("materiales", materiales);
            request.getRequestDispatcher("/mvc/view/admin/listarMateriales.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al obtener la lista de materiales.");
            request.getRequestDispatcher("/mvc/view//admin/listarMateriales.jsp").forward(request, response);
        }
    }
    
    /**
     * Maneja las solicitudes HTTP POST para listar los materiales.
     *
     * <p>Redirige las solicitudes POST a la lógica de manejo de solicitudes GET,
     * permitiendo que ambos métodos realicen la misma funcionalidad.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
