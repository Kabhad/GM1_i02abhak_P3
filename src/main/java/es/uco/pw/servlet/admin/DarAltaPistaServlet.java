package es.uco.pw.servlet.admin;

import es.uco.pw.business.pista.TamanoPista;
import es.uco.pw.display.javabean.PistaBean;
import es.uco.pw.data.dao.PistasDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet para gestionar el alta de nuevas pistas deportivas.
 *
 * <p>Este servlet maneja solicitudes HTTP POST para procesar los datos enviados desde un formulario,
 * crear una nueva pista y guardarla en la base de datos a través del DAO de pistas.</p>
 *
 * <p>En caso de éxito, redirige al listado de pistas con un mensaje de confirmación.
 * Si ocurre un error, muestra un mensaje de error en la vista correspondiente.</p>
 */
public class DarAltaPistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja solicitudes HTTP POST para dar de alta una nueva pista deportiva.
     *
     * <p>Recupera los datos del formulario, crea un {@code PistaBean}, y llama al DAO para
     * guardar la pista en la base de datos. Gestiona errores y redirige según el resultado.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recuperar parámetros del formulario
            String nombrePista = request.getParameter("nombrePista");
            boolean disponible = Boolean.parseBoolean(request.getParameter("disponible"));
            boolean exterior = Boolean.parseBoolean(request.getParameter("exterior"));

            // Capturar el valor de tamanoPista y convertirlo al enum correspondiente
            String tamanoPistaParam = request.getParameter("tamanoPista");
            TamanoPista tamanoPista = null;

            // Mapeo de los valores recibidos al enum TamanoPista
            if (tamanoPistaParam != null && !tamanoPistaParam.isEmpty()) {
                try {
                    // Convertir el string a TamanoPista enum (asegurándonos de que es en mayúsculas)
                    tamanoPista = TamanoPista.valueOf(tamanoPistaParam.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Si el valor no es válido, mostramos un mensaje de error
                    request.setAttribute("error", "Tamaño de pista no válido.");
                    request.getRequestDispatcher("/mvc/view/admin/darAltaPista.jsp").forward(request, response);
                    return;
                }
            }

            // Obtener el resto de parámetros
            int maxJugadores = Integer.parseInt(request.getParameter("maxJugadores"));

            // Crear el objeto PistasDAO
            PistasDAO pistasDAO = new PistasDAO(getServletContext());

            // Crear el bean de la pista sin idPista ya que es autoincremental
            PistaBean pistaBean = new PistaBean(0, nombrePista, disponible, exterior, tamanoPista, maxJugadores);

            // Guardar la pista en la base de datos
            pistasDAO.crearPista(pistaBean.getNombrePista(), pistaBean.isDisponible(), pistaBean.isExterior(), pistaBean.getTamanoPista(), pistaBean.getMaxJugadores());

            // Redirigir al listado de pistas con mensaje de éxito
            request.setAttribute("mensaje", "Pista creada con éxito.");
            request.getRequestDispatcher("/admin/listarPistas").forward(request, response);
        } catch (Exception e) {
            // Manejar posibles errores y redirigir al error de listarPistas
            request.setAttribute("error", "Error al crear la pista: " + e.getMessage());
            request.getRequestDispatcher("/include/darAltaPistaError.jsp").forward(request, response); // Redirigir al error de listarPistas
        }
    }
}
