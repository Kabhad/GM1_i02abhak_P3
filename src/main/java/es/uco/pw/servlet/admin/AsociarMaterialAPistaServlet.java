package es.uco.pw.servlet.admin;

import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.display.javabean.MaterialBean;
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
 * Servlet para gestionar la asociación de materiales a pistas deportivas.
 *
 * <p>Este servlet maneja solicitudes HTTP GET y POST para cargar información sobre materiales y pistas disponibles,
 * y para procesar la asociación de materiales a pistas. Utiliza el DAO de pistas para interactuar con la base de datos
 * y beans para representar los datos procesados en las vistas.</p>
 *
 * <p>El método GET carga los datos necesarios para mostrar en la interfaz, mientras que el método POST procesa la 
 * asociación de materiales a pistas y recarga la vista con el resultado de la operación.</p>
 */
public class AsociarMaterialAPistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja solicitudes HTTP GET para cargar información de materiales y pistas disponibles.
     *
     * <p>Carga la lista de materiales y pistas disponibles para asociar y envía estos datos como atributos
     * de la solicitud a la vista correspondiente. Si ocurre un error, muestra un mensaje en la vista.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PistasDAO pistasDAO = new PistasDAO(getServletContext());

            // Obtener materiales con información de pistas asociadas
            List<MaterialBean> materiales = pistasDAO.obtenerMaterialesConPistas();

            // Obtener pistas disponibles para el desplegable
            List<PistaDTO> pistasDTO = pistasDAO.listarPistas();
            List<PistaBean> pistas = new ArrayList<>();
            for (PistaDTO dto : pistasDTO) {
                if (dto.isDisponible()) {
                    pistas.add(new PistaBean(
                        dto.getIdPista(),
                        dto.getNombrePista(),
                        dto.isDisponible(),
                        dto.isExterior(),
                        dto.getPista(),
                        dto.getMax_jugadores()
                    ));
                }
            }

            request.setAttribute("materiales", materiales);
            request.setAttribute("pistas", pistas);
            request.getRequestDispatcher("/mvc/view/admin/asociarMaterialAPista.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar los materiales o pistas: " + e.getMessage());
            request.getRequestDispatcher("/mvc/view/admin/asociarMaterialAPista.jsp").forward(request, response);
        }
    }



    /**
     * Maneja solicitudes HTTP POST para asociar materiales a pistas.
     *
     * <p>Procesa los parámetros recibidos de la solicitud, realiza la asociación de materiales a pistas
     * a través del DAO de pistas, y recarga la vista con un mensaje de éxito o error según el resultado.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nombrePista = request.getParameter("nombrePista");
            int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));

            PistasDAO pistasDAO = new PistasDAO(getServletContext());

            // Asociar material a pista
            boolean exito = pistasDAO.asociarMaterialAPista(nombrePista, idMaterial);
            if (exito) {
                request.setAttribute("mensaje", "Material asociado exitosamente.");
            } else {
                request.setAttribute("error", "No se pudo asociar el material.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error al asociar el material: " + e.getMessage());
        }

        // Recargar vista
        doGet(request, response);
    }
}
