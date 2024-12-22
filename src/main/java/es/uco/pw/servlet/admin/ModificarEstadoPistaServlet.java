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

public class ModificarEstadoPistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GET: Muestra todas las pistas.
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
     * POST: Modifica el estado de una pista.
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
