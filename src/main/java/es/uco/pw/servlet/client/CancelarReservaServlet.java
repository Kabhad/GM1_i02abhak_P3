package es.uco.pw.servlet.client;

import es.uco.pw.business.jugador.JugadorDTO;
import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.reserva.ReservaDTO;
import es.uco.pw.data.dao.ReservasDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Servlet para cancelar una reserva.
 */
@WebServlet("/CancelarReserva")
public class CancelarReservaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Maneja las solicitudes HTTP GET para cancelar una reserva.
     *
     * @param request  La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error de servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros de la solicitud
        String idReservaParam = request.getParameter("idReserva");
        String correoUsuario = request.getParameter("correoUsuario");

        // Validar parámetros
        if (idReservaParam == null || idReservaParam.isEmpty() || correoUsuario == null || correoUsuario.isEmpty()) {
            request.setAttribute("error", "Parámetros inválidos para cancelar la reserva.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int idReserva;
        try {
            idReserva = Integer.parseInt(idReservaParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID de la reserva debe ser un número válido.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Acceso al DAO
        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());

        try {
            // Buscar el jugador asociado al correo
            JugadorDTO jugador = reservasDAO.buscarJugadorPorCorreo(correoUsuario, getServletContext());
            if (jugador == null) {
                throw new IllegalArgumentException("No se encontró un jugador con el correo especificado.");
            }

            // Obtener la reserva para verificarla
            ReservaDTO reserva = reservasDAO.obtenerReservaPorId(idReserva);
            if (reserva == null) {
                throw new IllegalArgumentException("No se encontró la reserva especificada.");
            }

            // Obtener la pista asociada a la reserva
            PistaDTO pista = reservasDAO.buscarPistaPorId(reserva.getIdPista(), getServletContext());

            // Cancelar la reserva
            reservasDAO.cancelarReserva(jugador, pista, reserva.getFechaHora(), getServletContext());

            // Redirigir a una vista de éxito
            request.setAttribute("mensaje", "Reserva cancelada con éxito.");
            request.getRequestDispatcher("/mvc/view/clientHome.jsp").forward(request, response);

        } catch (IllegalArgumentException | SQLException e) {
            // Manejar errores y redirigir a una vista de error
            e.printStackTrace();
            request.setAttribute("error", "Error al cancelar la reserva: " + e.getMessage());
            request.getRequestDispatcher("/include/cancelarReservaError.jsp").forward(request, response);
        }
    }


    /**
     * Maneja las solicitudes HTTP POST redirigiéndolas al método doGet.
     *
     * @param request  La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error de servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
