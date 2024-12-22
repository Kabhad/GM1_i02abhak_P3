package es.uco.pw.servlet.client;

import es.uco.pw.business.reserva.*;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.ReservaBean;
import es.uco.pw.display.javabean.CustomerBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class CancelarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GET: Muestra reservas del usuario logueado con más de 24 horas de antelación.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        CustomerBean usuario = (session != null) ? (CustomerBean) session.getAttribute("customer") : null;

        if (usuario == null) {
            request.setAttribute("error", "Debe iniciar sesión para acceder a esta página.");
            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
            return;
        }

        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
        List<ReservaDTO> reservasDTO = reservasDAO.consultarReservasPorCorreo(usuario.getCorreo());

        // Filtrar reservas con 24 horas de antelación
        List<ReservaBean> reservasFiltradas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        Date fechaLimite = calendar.getTime();

        for (ReservaDTO reserva : reservasDTO) {
            if (reserva.getFechaHora().after(fechaLimite)) { // Solo reservas con más de 24 horas
                ReservaBean reservaBean = new ReservaBean();
                reservaBean.setIdReserva(reserva.getIdReserva());
                reservaBean.setFechaHora(reserva.getFechaHora());
                reservaBean.setDuracionMinutos(reserva.getDuracionMinutos());
                reservaBean.setIdPista(reserva.getIdPista());
                reservaBean.setPrecio(reserva.getPrecio());
                reservaBean.setDescuento(reserva.getDescuento());

                reservasFiltradas.add(reservaBean);
            }
        }

        request.setAttribute("reservas", reservasFiltradas);
        request.getRequestDispatcher("/mvc/view/client/cancelarReserva.jsp").forward(request, response);
    }

    /**
     * POST: Elimina la reserva seleccionada y recarga la lista.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        CustomerBean usuario = (session != null) ? (CustomerBean) session.getAttribute("customer") : null;

        if (usuario == null) {
            request.setAttribute("error", "Debe iniciar sesión para realizar esta acción.");
            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
            return;
        }

        String idReservaParam = request.getParameter("idReserva");
        if (idReservaParam == null || idReservaParam.isEmpty()) {
            request.setAttribute("error", "ID de reserva inválido.");
            doGet(request, response);
            return;
        }

        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
        try {
            int idReserva = Integer.parseInt(idReservaParam);
            reservasDAO.eliminarReserva(idReserva);
            request.setAttribute("mensaje", "Reserva cancelada con éxito.");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID de la reserva debe ser un número válido.");
        } catch (Exception e) {
            request.setAttribute("error", "Error al cancelar la reserva: " + e.getMessage());
        }

        doGet(request, response); // Recargar la lista después de eliminar
    }
}
