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
        PistasDAO pistasDAO = new PistasDAO();

        try {
            String tamanoParam = request.getParameter("tamano");
            String exteriorParam = request.getParameter("exterior");

            // Manejo correcto de los parámetros
            String tamano = (tamanoParam != null && !tamanoParam.trim().isEmpty()) ? tamanoParam : "";
            String exteriorStr = (exteriorParam != null && !exteriorParam.trim().isEmpty()) ? exteriorParam : "";
            Boolean exterior = null;

            if (!exteriorStr.isEmpty()) {
                exterior = Boolean.parseBoolean(exteriorStr);
            }

            System.out.println("Parametros enviados: ");
            System.out.println("tamano: " + tamano);
            System.out.println("exterior: " + exterior);

            // Llamada al método del DAO
            List<PistaDTO> pistasDisponibles = pistasDAO.buscarPistasDisponibles(tamano, exterior);

            // Convertir a PistaBean
            List<PistaBean> pistaBeans = new ArrayList<>();
            for (PistaDTO pistaDTO : pistasDisponibles) {
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

            // Pasar los resultados a la vista
            request.setAttribute("pistasDisponibles", pistaBeans);
            request.getRequestDispatcher("/mvc/view/mostrarPistaDisponible.jsp").forward(request, response);

        } catch (Exception e) {
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
