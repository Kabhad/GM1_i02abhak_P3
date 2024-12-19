package es.uco.pw.servlet.admin;

import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.pista.TamanoPista;
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

@WebServlet(name = "ListarPistasServlet", urlPatterns = "/admin/listarPistas")
public class ListarPistasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
                    TamanoPista.valueOf(dto.getPista().toString()),
                    dto.getMax_jugadores()
                ));
            }

            // Añadir la lista de pistas a la solicitud para ser mostrada en la vista
            request.setAttribute("pistas", pistas);

            // Redirigir a la vista correspondiente
            request.getRequestDispatcher("/mvc/view/listarPistas.jsp").forward(request, response);
        } catch (Exception e) {
            // Si hay un error, redirigir a la página de error con un mensaje descriptivo
            request.setAttribute("error", "Error al obtener la lista de pistas: " + e.getMessage());
            request.getRequestDispatcher("/include/listarPistasError.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
