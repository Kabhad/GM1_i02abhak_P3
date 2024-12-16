package es.uco.pw.servlet.client;

import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.reserva.ReservaDTO;
import es.uco.pw.business.jugador.JugadorDTO;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.CustomerBean;
import es.uco.pw.display.javabean.ReservaBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "RealizarReservaServlet", urlPatterns = "/client/realizarReserva")
public class RealizarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        try {
            // Captura y validación de parámetros del formulario
            String fechaHoraParam = request.getParameter("fechaHora");
            String duracionParam = request.getParameter("duracion");
            String idPistaParam = request.getParameter("idPista");
            String numeroAdultosParam = request.getParameter("numeroAdultos");
            String numeroNinosParam = request.getParameter("numeroNinos");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date fechaHora = sdf.parse(fechaHoraParam);
            int duracionMinutos = Integer.parseInt(duracionParam);
            int idPista = Integer.parseInt(idPistaParam);
            int numeroAdultos = Integer.parseInt(numeroAdultosParam);
            int numeroNinos = Integer.parseInt(numeroNinosParam);

            // Obtener DAO, jugador y pista
            ServletContext context = getServletContext();
            ReservasDAO reservasDAO = new ReservasDAO(context);
            JugadorDTO jugadorDTO = reservasDAO.buscarJugadorPorCorreo(customer.getCorreo(), context);
            if (jugadorDTO == null) {
                throw new IllegalArgumentException("No se encontró el jugador asociado al correo proporcionado.");
            }

            PistaDTO pistaDTO = reservasDAO.buscarPistaPorId(idPista);
            if (pistaDTO == null) {
                throw new IllegalArgumentException("La pista seleccionada no existe o no está disponible.");
            }

            // Realizar la reserva
            int idReserva = reservasDAO.hacerReservaIndividual(
                    jugadorDTO, fechaHora, duracionMinutos, pistaDTO, numeroAdultos, numeroNinos, context);

            // Encapsular datos en ReservaBean
            ReservaBean reservaBean = new ReservaBean();
            reservaBean.setIdReserva(idReserva);
            reservaBean.setFechaHora(fechaHora);
            reservaBean.setDuracionMinutos(duracionMinutos);
            reservaBean.setIdPista(pistaDTO.getIdPista());
            reservaBean.setNumeroAdultos(numeroAdultos);
            reservaBean.setNumeroNinos(numeroNinos);
         // Calcular el descuento según la antigüedad
            float descuento = (jugadorDTO.calcularAntiguedad() > 2) ? 0.1f : 0.0f;

            // Calcular el precio basado en la duración y el descuento
            float precio = ReservaDTO.calcularPrecio(duracionMinutos, descuento);
         // Asignar al ReservaBean
            reservaBean.setPrecio(precio);
            reservaBean.setDescuento(descuento);


            // Pasar ReservaBean a la vista
            request.setAttribute("reserva", reservaBean);
            request.getRequestDispatcher("/mvc/view/resultadoReserva.jsp").forward(request, response);

        } catch (ParseException e) {
            request.setAttribute("error", "Formato de fecha inválido. Use el formato yyyy-MM-ddTHH:mm.");
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        }
    }
}
