package es.uco.pw.servlet.client;

import es.uco.pw.business.jugador.JugadorDTO;
import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.reserva.*;
import es.uco.pw.data.dao.PistasDAO;
import es.uco.pw.data.dao.ReservasDAO;
import es.uco.pw.display.javabean.CustomerBean;
import es.uco.pw.display.javabean.PistaBean;
import es.uco.pw.display.javabean.ReservaBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Servlet para gestionar la funcionalidad de modificación de reservas.
 * Permite listar reservas modificables, filtrar pistas disponibles según criterios,
 * y actualizar una reserva existente con nuevos datos.
 */
public class ModificarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método doGet para manejar solicitudes GET.
     * Redirige a la funcionalidad correspondiente según los parámetros proporcionados.
     * 
     * @param request  Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP.
     * @throws ServletException Si ocurre un error en el procesamiento del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filtrarReservas = request.getParameter("filtrarReservas");
        String idReserva = request.getParameter("idReserva");

        try {
            if (filtrarReservas != null && filtrarReservas.equals("true")) {
                listarReservasModificables(request, response);
            } else if (idReserva != null && !idReserva.isEmpty()) {
                // Primero filtrar las pistas disponibles antes de redirigir al formulario
                filtrarPistasDisponibles(request, response);
            } else {
                throw new IllegalArgumentException("Parámetro inválido para la acción solicitada.");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/include/modificarReservaError.jsp").forward(request, response);
        }
    }


    /**
     * Lista las reservas disponibles para modificar según el cliente autenticado.
     * Se consideran reservas modificables aquellas con más de 24 horas de antelación.
     * 
     * @param request  Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP.
     * @throws ServletException Si ocurre un error en el procesamiento del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    private void listarReservasModificables(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        if (customer == null) {
            request.setAttribute("error", "Debes iniciar sesión para acceder a esta funcionalidad.");
            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
            return;
        }

        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
        List<ReservaDTO> reservas = reservasDAO.consultarReservasPorCorreo(customer.getCorreo());

        List<ReservaBean> reservasModificables = new ArrayList<>();
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        Date limiteModificacion = calendar.getTime();

        for (ReservaDTO reserva : reservas) {
            if (reserva.getFechaHora().after(limiteModificacion)) {
                ReservaBean reservaBean = new ReservaBean();

                // Mapear los datos necesarios
                reservaBean.setIdReserva(reserva.getIdReserva());
                reservaBean.setFechaHora(reserva.getFechaHora());
                reservaBean.setDuracionMinutos(reserva.getDuracionMinutos());
                reservaBean.setIdPista(reserva.getIdPista());
                reservaBean.setPrecio(reserva.getPrecio());
                reservaBean.setDescuento(reserva.getDescuento());

                if (reserva instanceof ReservaBono) {
                    ReservaBono reservaBono = (ReservaBono) reserva;
                    reservaBean.setIdBono(reservaBono.getIdBono());
                    reservaBean.setNumeroSesion(reservaBono.getNumeroSesion());
                }

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

                reservasModificables.add(reservaBean);
            }
        }

        request.setAttribute("reservasModificables", reservasModificables);
        request.getRequestDispatcher("/mvc/view/client/verReservas.jsp").forward(request, response);
    }

    /**
     * Filtra las pistas disponibles según el tipo de reserva, la fecha y duración seleccionadas.
     * 
     * @param request  Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP.
     * @throws ServletException Si ocurre un error en el procesamiento del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    private void filtrarPistasDisponibles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idReservaParam = request.getParameter("idReserva");
        String fechaHoraParam = request.getParameter("fechaHora");
        String duracionParam = request.getParameter("duracion");

        try {
            if (idReservaParam == null || idReservaParam.isEmpty()) {
                throw new IllegalArgumentException("No se proporcionó un ID de reserva válido.");
            }

            // Obtener la reserva existente
            ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
            int idReserva = Integer.parseInt(idReservaParam);
            ReservaDTO reserva = reservasDAO.obtenerReservaPorId(idReserva);

            if (reserva == null) {
                throw new IllegalArgumentException("No se encontró la reserva con el ID proporcionado.");
            }

            // Determinar el tipo de reserva
            String tipoReserva;
            if (reserva.getReservaEspecifica() instanceof ReservaInfantil) {
                tipoReserva = "infantil";
            } else if (reserva.getReservaEspecifica() instanceof ReservaFamiliar) {
                tipoReserva = "familiar";
            } else if (reserva.getReservaEspecifica() instanceof ReservaAdulto) {
                tipoReserva = "adulto";
            } else {
                throw new IllegalArgumentException("El tipo de reserva no es válido.");
            }

            // Validar y parsear fecha
            Date fechaHora = null;
            if (fechaHoraParam != null && !fechaHoraParam.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                fechaHora = sdf.parse(fechaHoraParam);
            }

            // Validar y parsear duración
            int duracionMin = 0;
            if (duracionParam != null && !duracionParam.isEmpty()) {
                duracionMin = Integer.parseInt(duracionParam);
            }

            if (fechaHora == null || duracionMin <= 0) {
                throw new IllegalArgumentException("Faltan parámetros obligatorios para filtrar las pistas.");
            }

            // Obtener pistas disponibles según tipo de reserva
            PistasDAO pistasDAO = new PistasDAO(getServletContext());
            List<PistaDTO> listaPistasDTO = pistasDAO.listarPistasDisponiblesPorTipoYFecha(tipoReserva, fechaHora, duracionMin);

            // Convertir `PistaDTO` a `PistaBean` y enviarlas al JSP
            List<PistaBean> pistasDisponibles = new ArrayList<>();
            for (PistaDTO pista : listaPistasDTO) {
                PistaBean pistaBean = new PistaBean();
                pistaBean.setIdPista(pista.getIdPista());
                pistaBean.setNombrePista(pista.getNombrePista());
                pistaBean.setExterior(pista.isExterior());
                pistasDisponibles.add(pistaBean);
            }

            // Redirigir al JSP
            request.getRequestDispatcher("/mvc/view/client/modificarReserva.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al filtrar las pistas disponibles: " + e.getMessage());
            request.getRequestDispatcher("/include/modificarReservaError.jsp").forward(request, response);
        }
    }


    /**
     * Gestiona la redirección al formulario de modificación de reservas.
     * Prepara los datos necesarios para cargar el formulario, incluyendo la reserva original y las pistas disponibles.
     * 
     * @param request  Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP.
     * @throws ServletException Si ocurre un error en el procesamiento del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    private void gestionarRedireccionFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idReservaParam = request.getParameter("idReserva");

        if (idReservaParam != null && !idReservaParam.isEmpty()) {
            try {
                ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
                PistasDAO pistasDAO = new PistasDAO(getServletContext());
                int idReserva = Integer.parseInt(idReservaParam);

                // Obtener reserva por ID
                ReservaDTO reserva = reservasDAO.obtenerReservaPorId(idReserva);
                if (reserva == null) {
                    throw new IllegalArgumentException("Reserva no encontrada.");
                }

                // Preparar datos comunes para la vista del formulario
                request.setAttribute("idReserva", reserva.getIdReserva());
                request.setAttribute("idPistaOriginal", reserva.getIdPista());
                request.setAttribute("fechaHoraOriginal", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(reserva.getFechaHora()));
                request.setAttribute("duracion", reserva.getDuracionMinutos());
                request.setAttribute("numeroAdultos", 
                    reserva instanceof ReservaFamiliar || reserva instanceof ReservaAdulto 
                        ? ((ReservaFamiliar) reserva).getNumeroAdultos() 
                        : 0
                );
                request.setAttribute("numeroNinos", 
                    reserva instanceof ReservaFamiliar || reserva instanceof ReservaInfantil 
                        ? ((ReservaFamiliar) reserva).getNumeroNinos() 
                        : 0
                );

                // Indicar si la reserva es de bono
                boolean esReservaBono = reserva instanceof ReservaBono;
                request.setAttribute("esReservaBono", esReservaBono);

                // Filtrar pistas disponibles si NO es una reserva de bono
                if (!esReservaBono) {
                    List<PistaDTO> pistasDisponibles = pistasDAO.listarPistasDisponiblesPorTipoYFecha(
                        "adulto", // Cambiar según el tipo necesario
                        reserva.getFechaHora(),
                        reserva.getDuracionMinutos()
                    );
                    List<PistaBean> pistas = new ArrayList<>();
                    for (PistaDTO pistaDTO : pistasDisponibles) {
                        PistaBean pista = new PistaBean();
                        pista.setIdPista(pistaDTO.getIdPista());
                        pista.setNombrePista(pistaDTO.getNombrePista());
                        pista.setExterior(pistaDTO.isExterior());
                        pistas.add(pista);
                    }
                    request.setAttribute("pistasDisponibles", pistas);
                } else {
                    // Si es una reserva de bono, usar la pista original sin filtrar
                    List<PistaBean> pistas = new ArrayList<>();
                    PistaBean pistaOriginal = new PistaBean();
                    pistaOriginal.setIdPista(reserva.getIdPista());
                    pistaOriginal.setNombrePista("Pista Actual (no modificable)");
                    pistaOriginal.setExterior(false); // Información básica
                    pistas.add(pistaOriginal);

                    request.setAttribute("pistasDisponibles", pistas);
                }

                // Redirigir a la vista del formulario
                request.getRequestDispatcher("/mvc/view/client/modificarReserva.jsp").forward(request, response);

            } catch (Exception e) {
                request.setAttribute("error", "Error al cargar los datos de la reserva: " + e.getMessage());
                request.getRequestDispatcher("/mvc/view/client/verReservas.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "No se seleccionó ninguna reserva para modificar.");
            request.getRequestDispatcher("/mvc/view/client/verReservas.jsp").forward(request, response);
        }
    }


    /**
     * Gestiona la modificación de una reserva existente.
     * Valida los datos enviados, actualiza la reserva y redirige con un mensaje de éxito o error.
     * 
     * @param request  Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP.
     * @param customer Cliente autenticado que realiza la modificación.
     * @throws ServletException Si ocurre un error en el procesamiento del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    private void gestionarModificacionReserva(HttpServletRequest request, HttpServletResponse response, CustomerBean customer) throws ServletException, IOException {
        try {
            // Obtener datos del formulario
            int idReserva = Integer.parseInt(request.getParameter("idReserva"));
            Date nuevaFechaHora = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getParameter("nuevaFechaHora"));
            int nuevaDuracion = Integer.parseInt(request.getParameter("nuevaDuracion"));
            int nuevosAdultos = Integer.parseInt(request.getParameter("nuevosAdultos"));
            int nuevosNinos = Integer.parseInt(request.getParameter("nuevosNinos"));

            ReservasDAO reservasDAO = new ReservasDAO(getServletContext());
            JugadorDTO jugador = reservasDAO.buscarJugadorPorCorreo(customer.getCorreo(), getServletContext());
            ReservaDTO reservaOriginal = reservasDAO.obtenerReservaPorId(idReserva);
            PistaDTO pistaOriginal = reservasDAO.buscarPistaPorId(reservaOriginal.getIdPista(), getServletContext());

            PistaDTO pistaNueva = null; // Inicializar variable para pista nueva

            // Comprobar si la reserva es de bono
            if (reservaOriginal instanceof ReservaBono) {
                // Para reservas de bono, usar siempre la pista original
                pistaNueva = pistaOriginal;
            } else {
                // Obtener pista nueva del formulario si no es una reserva de bono
                String idPistaNuevaParam = request.getParameter("idPistaNueva");
                if (idPistaNuevaParam == null || idPistaNuevaParam.isEmpty()) {
                    throw new IllegalArgumentException("No se ha proporcionado una pista nueva válida.");
                }
                pistaNueva = reservasDAO.buscarPistaPorId(Integer.parseInt(idPistaNuevaParam), getServletContext());
            }

            // Modificar la reserva
            reservasDAO.modificarReserva(
                jugador,
                pistaOriginal,
                reservaOriginal.getFechaHora(),
                pistaNueva,
                nuevaFechaHora,
                nuevaDuracion,
                nuevosAdultos,
                nuevosNinos,
                getServletContext()
            );

            // Redirigir con éxito
            request.setAttribute("mensaje", "Reserva modificada exitosamente.");
            response.sendRedirect(request.getContextPath() + "/client/modificarReserva?filtrarReservas=true");
        } catch (Exception e) {
            request.setAttribute("error", "Error al modificar la reserva: " + e.getMessage());
            request.getRequestDispatcher("/include/modificarReservaError.jsp").forward(request, response);
        }
    }



    /**
     * Método doPost para manejar solicitudes POST.
     * Redirige a la funcionalidad correspondiente según los parámetros proporcionados.
     * 
     * @param request  Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP.
     * @throws ServletException Si ocurre un error en el procesamiento del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        if (customer == null) {
            request.setAttribute("error", "Debes iniciar sesión para realizar esta acción.");
            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
            return;
        }

        // Diferenciar la acción según los parámetros
        String redirigirFormulario = request.getParameter("redirigirFormulario");

        if (redirigirFormulario != null && redirigirFormulario.equals("true")) {
            gestionarRedireccionFormulario(request, response);
        } else {
            gestionarModificacionReserva(request, response, customer);
        }
    }

}
