package es.uco.pw.servlet.client;

import es.uco.pw.business.jugador.JugadorDTO;
import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.reserva.Bono;
import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.CustomerBean;
import es.uco.pw.display.javabean.ReservaBean;
import es.uco.pw.business.reserva.ReservaDTO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Servlet encargado de gestionar las reservas realizadas utilizando bonos.
 */
public class RealizarReservaBonoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método que maneja las solicitudes GET al servlet.
     * Se encarga de verificar el estado del bono y mostrar las pistas disponibles para reservar.
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP enviada.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
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
        PistasDAO pistasDAO = new PistasDAO(getServletContext());

        try {
            String correo = customer.getCorreo();
            JugadorDTO jugador = reservasDAO.buscarJugadorPorCorreo(correo, getServletContext());
            if (jugador == null) {
                throw new IllegalArgumentException("No se encontró un jugador asociado al correo: " + correo);
            }

            int idJugador = jugador.getIdJugador();
            Bono bono = reservasDAO.obtenerBonoPorJugador(idJugador);

            if (bono == null || bono.estaCaducado() || bono.getSesionesRestantes() <= -1) {
                request.setAttribute("mensaje", "No tienes un bono válido. ¿Deseas crear uno?");
                request.getRequestDispatcher("/mvc/view/client/crearBono.jsp").forward(request, response);
                return;
            }

            String tipoReserva = request.getParameter("tipoReserva");
            String fechaHoraParam = request.getParameter("fechaHora");
            String duracionParam = request.getParameter("duracion");

            if (tipoReserva != null && fechaHoraParam != null && duracionParam != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date fechaHora = sdf.parse(fechaHoraParam);
                int duracionMinutos = Integer.parseInt(duracionParam);

                List<PistaDTO> pistas = pistasDAO.listarPistasDisponiblesPorTipoYFecha(tipoReserva, fechaHora, duracionMinutos);
                StringBuilder opcionesPistas = new StringBuilder();
                for (PistaDTO pista : pistas) {
                    opcionesPistas.append("<option value='").append(pista.getIdPista()).append("'>")
                            .append(pista.getNombrePista()).append(" - ")
                            .append(pista.isExterior() ? "Exterior" : "Interior")
                            .append("</option>");
                }
                request.setAttribute("opcionesPistas", opcionesPistas.toString());
            }

            request.setAttribute("idBono", bono.getIdBono());
            request.setAttribute("sesionesRestantes", bono.getSesionesRestantes());
            request.setAttribute("fechaCaducidad", bono.getFechaCaducidad());
            request.setAttribute("tieneBono", true);
            request.getRequestDispatcher("/mvc/view/client/realizarReservaBono.jsp").forward(request, response);

        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("error", "Formato de fecha inválido.");
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al obtener información del bono o pistas disponibles.");
            request.getRequestDispatcher("/mvc/view/client/realizarReservaBono.jsp").forward(request, response);
        }
    }

    /**
     * Método que gestiona la creación de un bono nuevo.
     *
     * @param request   La solicitud HTTP.
     * @param response  La respuesta HTTP.
     * @param customer  El cliente que realiza la solicitud.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void gestionarCreacionBono(HttpServletRequest request, HttpServletResponse response, CustomerBean customer) throws ServletException, IOException {
        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());

        try {
            JugadorDTO jugadorDTO = reservasDAO.buscarJugadorPorCorreo(customer.getCorreo(), getServletContext());
            if (jugadorDTO == null) {
                throw new IllegalArgumentException("Jugador no encontrado.");
            }

            Bono nuevoBono = reservasDAO.crearNuevoBono(jugadorDTO.getIdJugador());
            if (nuevoBono != null) {
                request.setAttribute("mensaje", "Bono creado con éxito. Ahora puedes realizar tu reserva.");
                request.setAttribute("idBono", nuevoBono.getIdBono());
                request.setAttribute("sesionesRestantes", nuevoBono.getSesionesRestantes());
                request.setAttribute("fechaCaducidad", nuevoBono.getFechaCaducidad());
                request.setAttribute("tieneBono", true);
                request.getRequestDispatcher("/mvc/view/client/realizarReservaBono.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("mensaje", "No se pudo crear el bono. Inténtalo de nuevo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al crear el bono: " + e.getMessage());
        }

        request.getRequestDispatcher("/mvc/view/client/crearBono.jsp").forward(request, response);
    }

    /**
     * Método que maneja la lógica de realizar una reserva utilizando un bono.
     *
     * @param request   La solicitud HTTP.
     * @param response  La respuesta HTTP.
     * @param customer  El cliente que realiza la reserva.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void gestionarReserva(HttpServletRequest request, HttpServletResponse response, CustomerBean customer) throws ServletException, IOException {
        try {
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

            ServletContext context = getServletContext();
            ReservasDAO reservasDAO = new ReservasDAO(context);
            PistasDAO pistasDAO = new PistasDAO(context);

            JugadorDTO jugadorDTO = reservasDAO.buscarJugadorPorCorreo(customer.getCorreo(), context);
            if (jugadorDTO == null) {
                throw new IllegalArgumentException("Jugador no encontrado.");
            }

            PistaDTO pistaDTO = pistasDAO.buscarPistaPorId(idPista);
            if (pistaDTO == null) {
                throw new IllegalArgumentException("Pista no encontrada o no disponible.");
            }

            boolean reservaExitosa = reservasDAO.hacerReservaBono(jugadorDTO, fechaHora, duracionMinutos, pistaDTO, numeroAdultos, numeroNinos);

            if (reservaExitosa) {
                Bono bonoActualizado = reservasDAO.obtenerBonoPorJugador(jugadorDTO.getIdJugador());

                ReservaBean reservaBean = new ReservaBean();
                reservaBean.setFechaHora(fechaHora);
                reservaBean.setDuracionMinutos(duracionMinutos);
                reservaBean.setIdPista(idPista);
                reservaBean.setNumeroAdultos(numeroAdultos);
                reservaBean.setNumeroNinos(numeroNinos);
                reservaBean.setPrecio(ReservaDTO.calcularPrecio(duracionMinutos, 0));

                if (bonoActualizado != null) {
                    reservaBean.setIdBono(bonoActualizado.getIdBono());
                    reservaBean.setNumeroSesion(5 - bonoActualizado.getSesionesRestantes());
                } else {
                    reservaBean.setIdBono(null);
                    reservaBean.setNumeroSesion(5);
                }

                request.setAttribute("reserva", reservaBean);
                request.getRequestDispatcher("/mvc/view/client/resultadoReserva.jsp").forward(request, response);
            } else {
                throw new IllegalStateException("No se pudo realizar la reserva.");
            }

        } catch (ParseException e) {
            System.out.println("Error de formato de fecha: " + e.getMessage());
            request.setAttribute("error", "Formato de fecha inválido.");
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error inesperado al realizar la reserva: " + e.getMessage());
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        }
    }

    /**
     * Método que maneja las solicitudes POST al servlet.
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP enviada.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        String crearBonoParam = request.getParameter("crearBono");

        if (crearBonoParam != null && crearBonoParam.equals("true")) {
            gestionarCreacionBono(request, response, customer);
        } else {
            gestionarReserva(request, response, customer);
        }
    }
}
