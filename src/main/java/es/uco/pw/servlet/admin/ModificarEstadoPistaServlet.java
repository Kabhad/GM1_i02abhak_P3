package es.uco.pw.servlet.admin;

import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.business.pista.PistaDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet para modificar el estado de las pistas deportivas.
 *
 * <p>Este servlet permite a los administradores gestionar el estado de las pistas
 * a través de dos operaciones principales:</p>
 * <ul>
 *     <li><strong>GET:</strong> Muestra todas las pistas existentes para que puedan ser seleccionadas y editadas.</li>
 *     <li><strong>POST:</strong> Permite modificar el estado de una pista específica en la base de datos.</li>
 * </ul>
 *
 * <p>Las pistas son gestionadas a través del {@link PistasDAO} y enviadas a las vistas
 * como objetos {@link PistaDTO}.</p>
 */
public class ModificarEstadoPistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja solicitudes HTTP GET para mostrar todas las pistas.
     *
     * <p>Obtiene las pistas existentes desde la base de datos y las envía a la vista correspondiente
     * para ser visualizadas y gestionadas.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Usar el DAO para obtener las pistas
            PistasDAO pistasDAO = new PistasDAO(getServletContext());
            List<PistaDTO> pistasDTO = pistasDAO.listarPistas();

            // Convertir DTO a un formato adecuado para la vista
            List<PistaDTO> pistas = new ArrayList<>(pistasDTO);

            // Enviar pistas a la vista
            request.setAttribute("pistas", pistas);
            request.getRequestDispatcher("/mvc/view/admin/modificarEstadoPista.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar las pistas: " + e.getMessage());
            request.getRequestDispatcher("/mvc/view/admin/modificarEstadoPista.jsp").forward(request, response);
        }
    }

    /**
     * Maneja solicitudes HTTP POST para modificar el estado de una pista.
     *
     * <p>Recupera los datos enviados desde el formulario, valida los datos y actualiza el estado
     * de una pista en la base de datos utilizando el DAO.</p>
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
            int idPista = Integer.parseInt(request.getParameter("idPista"));
            boolean nuevoEstado = Boolean.parseBoolean(request.getParameter("nuevoEstado"));

            // Actualizar el estado en la base de datos
            PistasDAO pistasDAO = new PistasDAO(getServletContext());
            pistasDAO.modificarEstadoPista(idPista, nuevoEstado);

            request.setAttribute("mensaje", "Estado de la pista modificado correctamente.");
        } catch (Exception e) {
            request.setAttribute("error", "Error al modificar el estado de la pista: " + e.getMessage());
        }

        // Recargar las pistas después de la modificación
        doGet(request, response);
    }
}
