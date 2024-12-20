package es.uco.pw.servlet.client;

import es.uco.pw.business.reserva.ReservaDTO;
import es.uco.pw.business.reserva.ReservaAdulto;
import es.uco.pw.business.reserva.ReservaBono;
import es.uco.pw.business.reserva.ReservaFamiliar;
import es.uco.pw.business.reserva.ReservaInfantil;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.CustomerBean;
import es.uco.pw.display.javabean.ReservaBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ConsultarReservaServlet", urlPatterns = "/client/consultarReserva")
public class ConsultarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        if (customer == null) {
            request.setAttribute("error", "Debes iniciar sesión para acceder a esta funcionalidad.");
            request.getRequestDispatcher("/include/consultarReservaError.jsp").forward(request, response);
            return;
        }

        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaFinParam = request.getParameter("fechaFin");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = sdf.parse(fechaInicioParam);
            Date fechaFin = sdf.parse(fechaFinParam);

            if (fechaInicio.after(fechaFin)) {
                request.setAttribute("error", "La fecha de inicio no puede ser posterior a la fecha de fin.");
                request.getRequestDispatcher("/include/consultarReservaError.jsp").forward(request, response);
                return;
            }

            // Consulta las reservas desde la base de datos
            ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
            List<ReservaDTO> reservas = reservasDAO.consultarReservasPorCorreoYFechas(customer.getCorreo(), fechaInicio, fechaFin);

            if (reservas == null || reservas.isEmpty()) {
                request.setAttribute("error", "No se encontraron reservas en el rango seleccionado.");
                request.getRequestDispatcher("/include/consultarReservaError.jsp").forward(request, response);
                return;
            }

            // Transformar y clasificar reservas en finalizadas y futuras
            List<ReservaBean> reservasFinalizadas = new ArrayList<>();
            List<ReservaBean> reservasFuturas = new ArrayList<>();
            Date fechaActual = new Date();

            for (ReservaDTO reserva : reservas) {
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

             // Datos específicos si es Familiar, Infantil o Adulto
                if (reserva.getReservaEspecifica() instanceof ReservaFamiliar) {
                    ReservaFamiliar reservaFamiliar = (ReservaFamiliar) reserva.getReservaEspecifica();
                    reservaBean.setNumeroAdultos(reservaFamiliar.getNumeroAdultos());
                    reservaBean.setNumeroNinos(reservaFamiliar.getNumeroNinos());
                } else if (reserva.getReservaEspecifica() instanceof ReservaInfantil) {
                    ReservaInfantil reservaInfantil = (ReservaInfantil) reserva.getReservaEspecifica();
                    reservaBean.setNumeroNinos(reservaInfantil.getNumeroNinos());
                } else if (reserva.getReservaEspecifica() instanceof ReservaAdulto) {
                    ReservaAdulto reservaAdulto = (ReservaAdulto) reserva.getReservaEspecifica();
                    reservaBean.setNumeroAdultos(reservaAdulto.getNumeroAdultos());
                }


                // Clasificación de reservas
                if (reserva.getFechaHora().before(fechaActual)) {
                    reservasFinalizadas.add(reservaBean);
                } else {
                    reservasFuturas.add(reservaBean);
                }	            
            }



            // Pasar las listas a la vista
            request.setAttribute("reservasFinalizadas", reservasFinalizadas);
            request.setAttribute("reservasFuturas", reservasFuturas);
            request.getRequestDispatcher("/mvc/view/client/mostrarReservas.jsp").forward(request, response);

        } catch (ParseException e) {
            request.setAttribute("error", "Formato de fecha inválido. Usa yyyy-MM-dd.");
            request.getRequestDispatcher("/include/consultarReservaError.jsp").forward(request, response);
        }
    }
}
