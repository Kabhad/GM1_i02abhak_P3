package es.uco.pw.servlet.client;

import es.uco.pw.business.jugador.JugadorDTO;
import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.reserva.Bono;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.CustomerBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet para realizar reservas utilizando bonos.
 */
@WebServlet(name = "RealizarReservasBonoServlet", urlPatterns = {"/client/realizarReservaBono"})
public class RealizarReservaBonoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        if (customer == null) {
            request.setAttribute("mensaje", "Debes iniciar sesión para realizar una reserva con bono.");
            request.getRequestDispatcher("/mvc/view/login.jsp").forward(request, response);
            return;
        }

        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());

        try {
            // Obtener el ID del jugador desde el CustomerBean
            String correo = customer.getCorreo();
            JugadorDTO jugador = reservasDAO.buscarJugadorPorCorreo(correo, getServletContext());
            if (jugador == null) {
                throw new IllegalArgumentException("No se encontró un jugador asociado al correo: " + correo);
            }

            int idJugador = jugador.getIdJugador();
            Bono bono = reservasDAO.obtenerBonoPorJugador(idJugador);

            if (bono == null || bono.estaCaducado() || bono.getSesionesRestantes() <= 0) {
                request.setAttribute("mensaje", "No tienes un bono válido. ¿Deseas crear uno?");
                request.getRequestDispatcher("/mvc/view/client/crearBono.jsp").forward(request, response);
                return;
            }

            // Atributos válidos del bono
            request.setAttribute("idBono", bono.getIdBono());
            request.setAttribute("sesionesRestantes", bono.getSesionesRestantes());
            request.setAttribute("fechaCaducidad", bono.getFechaCaducidad());
            request.setAttribute("tieneBono", true);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al obtener información del bono.");
        }

        request.getRequestDispatcher("/mvc/view/client/realizarReservaBono.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
        String mensaje = "";
        boolean exito = false;

        try {
            int idJugador = Integer.parseInt(request.getParameter("idJugador"));
            int idPista = Integer.parseInt(request.getParameter("idPista"));
            String fechaHoraStr = request.getParameter("fechaHora");
            int duracionMinutos = Integer.parseInt(request.getParameter("duracion"));
            int numeroAdultos = Integer.parseInt(request.getParameter("numeroAdultos"));
            int numeroNinos = Integer.parseInt(request.getParameter("numeroNinos"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date fechaHora = sdf.parse(fechaHoraStr);

            // Buscar jugador y pista
            JugadorDTO jugador = ReservasDAO.buscarJugadorPorId(idJugador, getServletContext());
            PistaDTO pista = reservasDAO.buscarPistaPorId(idPista, getServletContext());

            if (jugador == null || pista == null) {
                throw new IllegalArgumentException("Jugador o pista no encontrados.");
            }

            // Validar bono
            Bono bono = reservasDAO.obtenerBonoPorJugador(idJugador);
            if (bono == null || bono.estaCaducado() || bono.getSesionesRestantes() <= 0) {
                String crearBono = request.getParameter("crearBono");
                if ("true".equals(crearBono)) {
                    bono = reservasDAO.crearNuevoBono(idJugador);
                } else {
                    request.setAttribute("mensaje", "No tienes un bono válido. ¿Deseas crear uno?");
                    request.getRequestDispatcher("/mvc/view/crearBono.jsp").forward(request, response);
                    return;
                }
            }

            // Realizar la reserva con el bono
            exito = reservasDAO.hacerReservaBono(jugador, fechaHora, duracionMinutos, pista, numeroAdultos, numeroNinos);

            if (exito) {
                // Refrescar el bono después de la actualización
                bono = reservasDAO.obtenerBonoPorJugador(idJugador);

                mensaje = "Reserva realizada con éxito utilizando el bono.";
                request.setAttribute("idBono", bono.getIdBono());
                request.setAttribute("sesionesRestantes", bono.getSesionesRestantes());
                request.setAttribute("tieneBono", bono != null && !bono.estaCaducado() && bono.getSesionesRestantes() > 0);
            } else {
                mensaje = "No se pudo realizar la reserva. Inténtalo de nuevo.";
            }


        } catch (Exception e) {
            mensaje = "Error al realizar la reserva: " + e.getMessage();
            e.printStackTrace();
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("exito", exito);
        request.getRequestDispatcher("/mvc/view/reservaBono.jsp").forward(request, response);
    }
}
