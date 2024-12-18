package es.uco.pw.servlet.admin;

import es.uco.pw.business.material.EstadoMaterial;
import es.uco.pw.business.material.TipoMaterial;
import es.uco.pw.display.javabean.MaterialBean;
import es.uco.pw.data.dao.PistasDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DarAltaMaterialServlet", urlPatterns = "/admin/darAltaMaterial")
public class DarAltaMaterialServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recuperar parámetros del formulario
            int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));
            TipoMaterial tipo = TipoMaterial.valueOf(request.getParameter("tipo"));
            boolean usoExterior = Boolean.parseBoolean(request.getParameter("usoExterior"));
            EstadoMaterial estado = EstadoMaterial.valueOf(request.getParameter("estado"));

            // Crear el bean
            MaterialBean materialBean = new MaterialBean(idMaterial, tipo, usoExterior, estado);

            // Guardar el material en la base de datos
            PistasDAO materialesDAO = new PistasDAO(getServletContext());
            materialesDAO.crearMaterial(materialBean.getId(), tipo, materialBean.isUsoExterior(), estado);

            // Redirigir al listado con mensaje de éxito
            request.setAttribute("mensaje", "Material creado con éxito.");
            request.getRequestDispatcher("/admin/listarMateriales").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al crear el material: " + e.getMessage());
            request.getRequestDispatcher("/mvc/view/crearMaterial.jsp").forward(request, response);
        }
    }
}
