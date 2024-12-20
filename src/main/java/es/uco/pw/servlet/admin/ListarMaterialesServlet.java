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

@WebServlet(name = "ListarMaterialesServlet", urlPatterns = "/admin/listarMateriales")
public class ListarMaterialesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
