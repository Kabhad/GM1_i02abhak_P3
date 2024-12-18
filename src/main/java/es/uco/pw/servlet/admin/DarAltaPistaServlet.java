package es.uco.pw.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.business.pista.TamanoPista;
import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.display.javabean.PistaBean;

@WebServlet(name = "DarAltaPistaServlet", urlPatterns = "/admin/darAltaPista")
public class DarAltaPistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recuperar parámetros del formulario
            String nombrePista = request.getParameter("nombrePista");
            boolean disponible = Boolean.parseBoolean(request.getParameter("disponible"));
            boolean exterior = Boolean.parseBoolean(request.getParameter("exterior"));
            
            // Capturar el valor de tamanoPista y convertirlo a TamanoPista
            String tamanoPistaParam = request.getParameter("tamanoPista");
            System.out.println("Valor recibido para tamanoPista: " + tamanoPistaParam);
            TamanoPista tamanoPista = null;
            if (tamanoPistaParam != null && !tamanoPistaParam.isEmpty()) {
                try {
                    tamanoPista = TamanoPista.valueOf(tamanoPistaParam.toUpperCase());
                } catch (IllegalArgumentException e) {
                    request.setAttribute("error", "Tamaño de pista no válido.");
                    request.getRequestDispatcher("/mvc/view/darAltaPista.jsp").forward(request, response);
                    return;
                }
            }

            int maxJugadores = Integer.parseInt(request.getParameter("maxJugadores"));
            int idPista = Integer.parseInt(request.getParameter("idPista")); // Obtener el ID de la pista desde el formulario

            // Crear el objeto PistasDAO
            PistasDAO pistasDAO = new PistasDAO(getServletContext());

            // Verificar si ya existe una pista con el mismo nombre
            if (pistasDAO.buscarPistaPorNombre(nombrePista) != null) {
                request.setAttribute("error", "Ya existe una pista con ese nombre.");
                request.getRequestDispatcher("/mvc/view/darAltaPista.jsp").forward(request, response);
                return;
            }

            // Verificar si ya existe una pista con el mismo ID
            if (pistasDAO.buscarPistaPorId(idPista) != null) {
                request.setAttribute("error", "Ya existe una pista con ese ID.");
                request.getRequestDispatcher("/mvc/view/darAltaPista.jsp").forward(request, response);
                return;
            }

            // Crear el bean de la pista
            PistaBean pistaBean = new PistaBean(idPista, nombrePista, disponible, exterior, tamanoPista, maxJugadores);

            // Guardar la pista en la base de datos
            pistasDAO.crearPista(pistaBean.getNombrePista(), pistaBean.isDisponible(), pistaBean.isExterior(), pistaBean.getTamanoPista(), pistaBean.getMaxJugadores());

            // Redirigir al listado de pistas con mensaje de éxito
            request.setAttribute("mensaje", "Pista creada con éxito.");
            request.getRequestDispatcher("/admin/listarPistas").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al crear la pista: " + e.getMessage());
            request.getRequestDispatcher("/mvc/view/darAltaPista.jsp").forward(request, response);
        }
    }
}
