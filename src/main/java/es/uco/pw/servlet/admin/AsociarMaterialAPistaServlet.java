package es.uco.pw.servlet.admin;

import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.business.material.MaterialDTO;
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

@WebServlet(name = "AsociarMaterialAPistaServlet", urlPatterns = "/admin/asociarMaterialAPista")
public class AsociarMaterialAPistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PistasDAO pistasDAO = new PistasDAO(getServletContext());

            // Obtener materiales disponibles
            List<MaterialDTO> materialesDTO = pistasDAO.obtenerTodosLosMateriales();
            List<MaterialBean> materiales = new ArrayList<>();

            for (MaterialDTO dto : materialesDTO) {
                int idPista = pistasDAO.obtenerIdPistaPorMaterial(dto.getId()); // Método para obtener el idPista
                String nombrePista = pistasDAO.obtenerNombrePistaPorMaterial(dto.getId());

                materiales.add(new MaterialBean(dto.getId(), dto.getTipo(), dto.isUsoExterior(), dto.getEstado(), idPista, nombrePista));
            }

            // Obtener pistas disponibles
            List<PistaDTO> pistasDTO = pistasDAO.listarPistas();
            List<PistaBean> pistas = new ArrayList<>();
            for (PistaDTO dto : pistasDTO) {
                if (dto.isDisponible()) {
                    pistas.add(new PistaBean(dto.getIdPista(), dto.getNombrePista(), dto.isDisponible(), dto.isExterior(), dto.getPista(), dto.getMax_jugadores()));
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
