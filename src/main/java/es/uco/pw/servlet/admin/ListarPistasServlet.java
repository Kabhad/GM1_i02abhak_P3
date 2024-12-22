package es.uco.pw.servlet.admin;

import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.display.javabean.PistaBean;
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
 * Servlet para listar las pistas disponibles en el sistema.
 *
 * <p>Este servlet permite a los administradores obtener y visualizar una lista de pistas
 * disponibles en el sistema. Los datos se obtienen desde la base de datos a través del DAO
 * y se convierten en objetos {@link PistaBean} para su uso en la vista.</p>
 */
public class ListarPistasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja las solicitudes HTTP GET para listar las pistas.
     *
     * <p>Obtiene todas las pistas disponibles utilizando el DAO y las transforma
     * en una lista de objetos {@link PistaBean}, que se envían a la vista para su
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
            // Crear el objeto PistasDAO para acceder a las pistas
            PistasDAO pistasDAO = new PistasDAO(getServletContext());

            // Obtener todas las pistas usando el método correcto en el DAO
            List<PistaDTO> pistasDTO = pistasDAO.listarPistas();

            // Convertir los DTOs a Beans para la vista
            List<PistaBean> pistas = new ArrayList<>();
            for (PistaDTO dto : pistasDTO) {
                pistas.add(new PistaBean(
                    dto.getIdPista(),
                    dto.getNombrePista(),
                    dto.isDisponible(),
                    dto.isExterior(),
                    dto.getPista(),
                    dto.getMax_jugadores()
                ));
            }

            // Añadir la lista de pistas a la solicitud para ser mostrada en la vista
            request.setAttribute("pistas", pistas);

            // Redirigir a la vista correspondiente
            request.getRequestDispatcher("/mvc/view/admin/listarPistas.jsp").forward(request, response);
        } catch (Exception e) {
            // Si hay un error, redirigir a la página de error con un mensaje descriptivo
            request.setAttribute("error", "Error al obtener la lista de pistas: " + e.getMessage());
            request.getRequestDispatcher("/include/listarPistasError.jsp").forward(request, response);
        }
    }

    /**
     * Maneja las solicitudes HTTP POST para listar las pistas.
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
