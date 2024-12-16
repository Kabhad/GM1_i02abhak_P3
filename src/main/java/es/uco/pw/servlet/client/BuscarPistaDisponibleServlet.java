package es.uco.pw.servlet.client;

import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.pista.TamanoPista;
import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.display.javabean.PistaBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet para buscar pistas disponibles en el sistema.
 */
@WebServlet("/BuscarPistaDisponible")
public class BuscarPistaDisponibleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Método doGet para manejar las solicitudes GET y buscar pistas disponibles.
     *
     * @param request  Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP.
     * @throws ServletException Si ocurre un error de Servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Crear una instancia del DAO
        PistasDAO pistasDAO = new PistasDAO();

        try {
            // Obtener los parámetros de búsqueda del formulario
            String tamanoParam = request.getParameter("tamano");
            String exteriorParam = request.getParameter("exterior");

            // Llamar al método del DAO para buscar pistas disponibles
            List<PistaDTO> pistasDisponibles = pistasDAO.buscarPistasDisponibles();

            // Convertir la lista de PistaDTO a PistaBean para la vista
            List<PistaBean> pistaBeans = new ArrayList<>();
            for (PistaDTO pistaDTO : pistasDisponibles) {
                // Filtrar según los parámetros recibidos
                if (tamanoParam != null && !tamanoParam.isEmpty() && !tamanoParam.equals(pistaDTO.getPista().name())) {
                    continue; // Saltar si el tamaño no coincide
                }
                if (exteriorParam != null && !exteriorParam.isEmpty() && Boolean.parseBoolean(exteriorParam) != pistaDTO.isExterior()) {
                    continue; // Saltar si el exterior no coincide
                }

                // Encapsular la información en PistaBean
                PistaBean bean = new PistaBean(
                        pistaDTO.getIdPista(),
                        pistaDTO.getNombrePista(),
                        pistaDTO.isDisponible(),
                        pistaDTO.isExterior(),
                        pistaDTO.getPista(),
                        pistaDTO.getMax_jugadores()
                );
                pistaBeans.add(bean);
            }

            // Enviar la lista de pistas a la vista
            request.setAttribute("pistasDisponibles", pistaBeans);
            request.getRequestDispatcher("/mostrarPistasDisponibles.jsp").forward(request, response);

        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            request.setAttribute("error", "Error al buscar pistas disponibles: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    /**
     * Método doPost redirige a doGet.
     *
     * @param request  Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP.
     * @throws ServletException Si ocurre un error de Servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
