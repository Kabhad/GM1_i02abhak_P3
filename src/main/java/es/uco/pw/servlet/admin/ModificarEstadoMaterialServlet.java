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

public class ModificarEstadoMaterialServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GET: Muestra todos los materiales.
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
     * POST: Modifica el estado de un material.
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

