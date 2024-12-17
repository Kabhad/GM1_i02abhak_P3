import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.business.reserva.ReservaDTO;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.ReservaBean;

@WebServlet(name = "EliminarReservaServlet", urlPatterns = "/admin/eliminarReserva")
public class EliminarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
        String idReservaParam = request.getParameter("idReserva");

        if (idReservaParam != null) {
            try {
                int idReserva = Integer.parseInt(idReservaParam);
                boolean eliminada = reservasDAO.eliminarReserva(idReserva);

                if (eliminada) {
                    request.setAttribute("mensaje", "Reserva eliminada correctamente.");
                } else {
                    request.setAttribute("error", "No se pudo eliminar la reserva. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de reserva inválido.");
            }
        }

        // Recargar reservas actualizadas
        List<ReservaDTO> reservasFuturasDTO = reservasDAO.consultarReservasFuturas();
        List<ReservaBean> reservasFuturas = new ArrayList<>();
        Date fechaActual = new Date();

        for (ReservaDTO reserva : reservasFuturasDTO) {
            if (reserva.getFechaHora().after(fechaActual)) {
                ReservaBean bean = new ReservaBean();
                bean.setIdReserva(reserva.getIdReserva());
                bean.setFechaHora(reserva.getFechaHora());
                bean.setDuracionMinutos(reserva.getDuracionMinutos());
                bean.setIdPista(reserva.getIdPista());
                bean.setPrecio(reserva.getPrecio());
                reservasFuturas.add(bean);
            }
        }

        request.setAttribute("reservasFuturas", reservasFuturas);
        request.getRequestDispatcher("/mvc/view/eliminarReservas.jsp").forward(request, response);
    }
}
