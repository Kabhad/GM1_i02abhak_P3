package es.uco.pw.servlet.client;

import es.uco.pw.business.reserva.ReservaDTO;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.CustomerBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/cliente/consultarReservas")
public class ConsultarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        if (customer == null) {
            request.setAttribute("error", "Debes iniciar sesión para acceder a esta funcionalidad.");
            request.getRequestDispatcher("../view/consultarReservaError.jsp").forward(request, response);
            return;
        }

        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaFinParam = request.getParameter("fechaFin");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio, fechaFin;

        try {
            fechaInicio = sdf.parse(fechaInicioParam);
            fechaFin = sdf.parse(fechaFinParam);

            if (fechaInicio.after(fechaFin)) {
                request.setAttribute("error", "La fecha de inicio no puede ser posterior a la fecha de fin.");
                request.getRequestDispatcher("../view/consultarReservaError.jsp").forward(request, response);
                return;
            }

            // Lógica de consulta y redirección a la vista correspondiente
            ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
            List<ReservaDTO> reservas = reservasDAO.consultarReservasPorCorreoYFechas(customer.getCorreo(), fechaInicio, fechaFin);

            Date now = new Date();
            List<ReservaDTO> reservasFuturas = reservas.stream().filter(r -> r.getFechaHora().after(now)).toList();
            List<ReservaDTO> reservasFinalizadas = reservas.stream().filter(r -> r.getFechaHora().before(now)).toList();

            request.setAttribute("reservasFuturas", reservasFuturas);
            request.setAttribute("reservasFinalizadas", reservasFinalizadas);

            request.getRequestDispatcher("../view/resultadoConsulta.jsp").forward(request, response);

        } catch (ParseException e) {
            request.setAttribute("error", "Formato de fecha inválido.");
            request.getRequestDispatcher("../view/consultarReservaError.jsp").forward(request, response);
        }
    }
}



