package es.uco.pw.servlet.client;

import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.reserva.ReservaDTO;
import es.uco.pw.business.jugador.JugadorDTO;
import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.CustomerBean;
import es.uco.pw.display.javabean.ReservaBean;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servlet para gestionar la realización de reservas.
 *
 * <p>Este servlet permite a los usuarios visualizar pistas disponibles para reservas
 * según sus criterios seleccionados y confirmar la reserva, almacenándola en la base de datos.</p>
 */
public class RealizarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Maneja solicitudes HTTP GET para obtener pistas disponibles según criterios.
     *
     * <p>Este método permite a los usuarios filtrar pistas disponibles según el tipo de reserva,
     * la fecha y hora seleccionadas, y la duración especificada. Genera opciones de pistas para
     * la selección en el formulario de reserva.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoReserva = request.getParameter("tipoReserva");
        String fechaHoraParam = request.getParameter("fechaHora"); // Fecha y hora seleccionadas
        String duracionParam = request.getParameter("duracion"); // Duración seleccionada
        ServletContext context = getServletContext();

        try {
            // Validar y parsear la fecha
            Date fechaHora = null;
            if (fechaHoraParam != null && !fechaHoraParam.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                fechaHora = sdf.parse(fechaHoraParam);
            }

            // Validar y parsear la duración
            int duracionMin = 0;
            if (duracionParam != null && !duracionParam.isEmpty()) {
                duracionMin = Integer.parseInt(duracionParam);
            }

            if (tipoReserva == null || fechaHora == null || duracionMin <= 0) {
                throw new IllegalArgumentException("Faltan parámetros obligatorios para filtrar las pistas.");
            }

            // Obtener pistas disponibles filtradas por tipo, fecha y duración
            PistasDAO pistasDAO = new PistasDAO(context);
            List<PistaDTO> listaPistasDTO = pistasDAO.listarPistasDisponiblesPorTipoYFecha(tipoReserva, fechaHora, duracionMin);

            // Generar HTML de opciones
            StringBuilder opcionesPistas = new StringBuilder();
            for (PistaDTO pista : listaPistasDTO) {
                opcionesPistas.append("<option value='")
                              .append(pista.getIdPista())
                              .append("'>")
                              .append(pista.getNombrePista())
                              .append(" - ")
                              .append(pista.isExterior() ? "Exterior" : "Interior")
                              .append("</option>");
            }

            // Enviar las opciones preformateadas al JSP
            request.setAttribute("opcionesPistas", opcionesPistas.toString());
            request.setAttribute("tipoReserva", tipoReserva);
            request.setAttribute("fechaHora", fechaHoraParam); // Mantener el valor seleccionado
            request.setAttribute("duracion", duracionParam); // Mantener la duración seleccionada
            request.getRequestDispatcher("/mvc/view/client/realizarReserva.jsp").forward(request, response);

        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("error", "Formato de fecha inválido.");
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar las pistas disponibles.");
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        }
    }





    /**
     * Maneja solicitudes HTTP POST para realizar una reserva.
     *
     * <p>El método valida los datos del formulario, crea una reserva en la base de datos
     * y redirige al usuario a una página de confirmación con los detalles de la reserva realizada.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        try {
            // Captura de parámetros
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

            // Validar jugador y pista
            ServletContext context = getServletContext();
            ReservasDAO reservasDAO = new ReservasDAO(context);
            JugadorDTO jugadorDTO = reservasDAO.buscarJugadorPorCorreo(customer.getCorreo(), context);

            if (jugadorDTO == null) {
                throw new IllegalArgumentException("Jugador no encontrado.");
            }

            PistasDAO pistasDAO = new PistasDAO(context);
            PistaDTO pistaDTO = pistasDAO.buscarPistaPorId(idPista);

            if (pistaDTO == null) {
                throw new IllegalArgumentException("Pista no encontrada o no disponible.");
            }

         // Crear reserva
            int idReserva = reservasDAO.hacerReservaIndividual(
                    jugadorDTO, fechaHora, duracionMinutos, pistaDTO, numeroAdultos, numeroNinos, context);

            // Obtener la reserva completa desde la base de datos
            ReservaDTO reservaDTO = reservasDAO.obtenerReservaPorId(idReserva);

            if (reservaDTO == null) {
                throw new IllegalStateException("No se pudo recuperar la reserva con ID: " + idReserva);
            }

            // Preparar ReservaBean para la vista
            ReservaBean reservaBean = new ReservaBean();
            reservaBean.setIdReserva(reservaDTO.getIdReserva());
            reservaBean.setFechaHora(reservaDTO.getFechaHora());
            reservaBean.setDuracionMinutos(reservaDTO.getDuracionMinutos());
            reservaBean.setIdPista(reservaDTO.getIdPista());
            reservaBean.setNumeroAdultos(numeroAdultos);
            reservaBean.setNumeroNinos(numeroNinos);

            // Extraer el precio y descuento correctos del ReservaDTO
            reservaBean.setPrecio(reservaDTO.getPrecio());
            reservaBean.setDescuento(reservaDTO.getDescuento());

            // Redirigir a la vista resultadoReserva.jsp
            request.setAttribute("reserva", reservaBean);
            request.getRequestDispatcher("/mvc/view/client/resultadoReserva.jsp").forward(request, response);


        } catch (ParseException e) {
            request.setAttribute("error", "Formato de fecha inválido.");
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al realizar la reserva.");
            request.getRequestDispatcher("/include/realizarReservaError.jsp").forward(request, response);
        }
    }
}
