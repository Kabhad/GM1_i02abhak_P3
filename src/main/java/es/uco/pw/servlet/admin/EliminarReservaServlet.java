package es.uco.pw.servlet.admin;

import es.uco.pw.business.reserva.*;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.ReservaBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servlet para gestionar la eliminación de reservas.
 *
 * <p>Este servlet permite a los administradores visualizar y eliminar reservas futuras.
 * Ofrece dos operaciones principales:</p>
 * <ul>
 *     <li><strong>GET:</strong> Muestra todas las reservas futuras disponibles para ser eliminadas.</li>
 *     <li><strong>POST:</strong> Elimina una reserva seleccionada y recarga la lista de reservas futuras.</li>
 * </ul>
 */
public class EliminarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja las solicitudes HTTP GET para mostrar todas las reservas futuras.
     *
     * <p>Obtiene las reservas futuras de la base de datos, las transforma en objetos {@link ReservaBean}
     * para facilitar su representación en la vista, y las envía a la página de eliminación de reservas.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
        List<ReservaDTO> reservasFuturasDTO = reservasDAO.consultarReservasFuturas();

        // Clasificar y transformar reservas a ReservaBean
        List<ReservaBean> reservasFuturas = empaquetarReservasFuturas(reservasFuturasDTO);

        // Pasar las reservas futuras a la vista
        request.setAttribute("reservasFuturas", reservasFuturas);
        request.getRequestDispatcher("/mvc/view/admin/eliminarReservas.jsp").forward(request, response);
    }

    /**
     * Maneja las solicitudes HTTP POST para eliminar una reserva seleccionada.
     *
     * <p>Recibe el ID de la reserva desde el formulario, intenta eliminarla de la base de datos
     * y recarga la lista de reservas futuras para reflejar los cambios.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
        String idReservaParam = request.getParameter("idReserva");

        if (idReservaParam != null) {
            try {
                int idReserva = Integer.parseInt(idReservaParam);
                reservasDAO.eliminarReserva(idReserva);
                request.setAttribute("mensaje", "Reserva eliminada correctamente.");
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de reserva inválido.");
            } catch (Exception e) {
                request.setAttribute("error", "No se pudo eliminar la reserva. Inténtalo de nuevo.");
            }
        }

        // Recargar las reservas futuras después de eliminar
        List<ReservaDTO> reservasFuturasDTO = reservasDAO.consultarReservasFuturas();
        List<ReservaBean> reservasFuturas = empaquetarReservasFuturas(reservasFuturasDTO);

        request.setAttribute("reservasFuturas", reservasFuturas);
        request.getRequestDispatcher("/mvc/view/admin/eliminarReservas.jsp").forward(request, response);
    }

    /**
     * Transforma una lista de objetos {@link ReservaDTO} en una lista de {@link ReservaBean},
     * clasificando y empaquetando detalles específicos según el tipo de reserva.
     *
     * @param reservasDTO La lista de reservas obtenidas de la base de datos.
     * @return Una lista de {@link ReservaBean} con los detalles transformados y listos para la vista.
     */
    private List<ReservaBean> empaquetarReservasFuturas(List<ReservaDTO> reservasDTO) {
        List<ReservaBean> reservasFuturas = new ArrayList<>();
        Date fechaActual = new Date();

        for (ReservaDTO reserva : reservasDTO) {
            if (reserva.getFechaHora().after(fechaActual)) { // Solo reservas futuras
                ReservaBean reservaBean = new ReservaBean();

                // Datos comunes de la reserva
                reservaBean.setIdReserva(reserva.getIdReserva());
                reservaBean.setFechaHora(reserva.getFechaHora());
                reservaBean.setDuracionMinutos(reserva.getDuracionMinutos());
                reservaBean.setIdPista(reserva.getIdPista());
                reservaBean.setPrecio(reserva.getPrecio());
                reservaBean.setDescuento(reserva.getDescuento());

                // Datos específicos si es con bono
                if (reserva instanceof ReservaBono) {
                    ReservaBono reservaBono = (ReservaBono) reserva;
                    reservaBean.setIdBono(reservaBono.getIdBono());
                    reservaBean.setNumeroSesion(reservaBono.getNumeroSesion());
                }

                // Datos específicos según el tipo de reserva
                if (reserva.getReservaEspecifica() instanceof ReservaFamiliar) {
                    ReservaFamiliar familiar = (ReservaFamiliar) reserva.getReservaEspecifica();
                    reservaBean.setNumeroAdultos(familiar.getNumeroAdultos());
                    reservaBean.setNumeroNinos(familiar.getNumeroNinos());
                } else if (reserva.getReservaEspecifica() instanceof ReservaInfantil) {
                    ReservaInfantil infantil = (ReservaInfantil) reserva.getReservaEspecifica();
                    reservaBean.setNumeroNinos(infantil.getNumeroNinos());
                } else if (reserva.getReservaEspecifica() instanceof ReservaAdulto) {
                    ReservaAdulto adulto = (ReservaAdulto) reserva.getReservaEspecifica();
                    reservaBean.setNumeroAdultos(adulto.getNumeroAdultos());
                }

                reservasFuturas.add(reservaBean);
            }
        }
        return reservasFuturas;
    }
}
