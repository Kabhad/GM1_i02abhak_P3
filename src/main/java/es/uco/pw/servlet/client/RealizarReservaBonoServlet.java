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
        PistasDAO pistasDAO = new PistasDAO(getServletContext());

        try {
            // Obtener el ID del jugador desde el CustomerBean
            String correo = customer.getCorreo();
            JugadorDTO jugador = reservasDAO.buscarJugadorPorCorreo(correo, getServletContext());
            if (jugador == null) {
                throw new IllegalArgumentException("No se encontró un jugador asociado al correo: " + correo);
            }

            // Comprobar si el usuario tiene un bono válido
            int idJugador = jugador.getIdJugador();
            Bono bono = reservasDAO.obtenerBonoPorJugador(idJugador);

            if (bono == null || bono.estaCaducado() || bono.getSesionesRestantes() <= -1) {
                request.setAttribute("mensaje", "No tienes un bono válido. ¿Deseas crear uno?");
                request.getRequestDispatcher("/mvc/view/client/crearBono.jsp").forward(request, response);
                return;
            }

            // Manejar el filtrado de pistas si se pasan parámetros
            String tipoReserva = request.getParameter("tipoReserva");
            String fechaHoraParam = request.getParameter("fechaHora");
            String duracionParam = request.getParameter("duracion");

            if (tipoReserva != null && fechaHoraParam != null && duracionParam != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date fechaHora = sdf.parse(fechaHoraParam);
                int duracionMinutos = Integer.parseInt(duracionParam);

                // Obtener las pistas disponibles por tipo, fecha y duración
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

            // Atributos del bono y redirección
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



    private void gestionarCreacionBono(HttpServletRequest request, HttpServletResponse response, CustomerBean customer) throws ServletException, IOException {
        ReservasDAO reservasDAO = new ReservasDAO(getServletContext());

        try {
            // Obtener el ID del jugador
            JugadorDTO jugadorDTO = reservasDAO.buscarJugadorPorCorreo(customer.getCorreo(), getServletContext());
            if (jugadorDTO == null) {
                throw new IllegalArgumentException("Jugador no encontrado.");
            }

            // Crear un nuevo bono
            Bono nuevoBono = reservasDAO.crearNuevoBono(jugadorDTO.getIdJugador());
            if (nuevoBono != null) {
                // Actualizar los atributos para la vista de realizar reserva
                request.setAttribute("mensaje", "Bono creado con éxito. Ahora puedes realizar tu reserva.");
                request.setAttribute("idBono", nuevoBono.getIdBono());
                request.setAttribute("sesionesRestantes", nuevoBono.getSesionesRestantes());
                request.setAttribute("fechaCaducidad", nuevoBono.getFechaCaducidad());
                request.setAttribute("tieneBono", true);

                // Redirigir directamente a la vista de realizar reserva
                request.getRequestDispatcher("/mvc/view/client/realizarReservaBono.jsp").forward(request, response);
                return;
            } else {
                // Mensaje en caso de fallo al crear el bono
                request.setAttribute("mensaje", "No se pudo crear el bono. Inténtalo de nuevo.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al crear el bono: " + e.getMessage());
        }

        // Si ocurre un error o no se crea el bono, redirigir de nuevo a crearBono.jsp
        request.getRequestDispatcher("/mvc/view/client/crearBono.jsp").forward(request, response);
    }

    private void gestionarReserva(HttpServletRequest request, HttpServletResponse response, CustomerBean customer) throws ServletException, IOException {
        try {
            // Captura de parámetros desde el formulario
            String fechaHoraParam = request.getParameter("fechaHora");
            String duracionParam = request.getParameter("duracion");
            String idPistaParam = request.getParameter("idPista");
            String numeroAdultosParam = request.getParameter("numeroAdultos");
            String numeroNinosParam = request.getParameter("numeroNinos");

            // Parsear y validar los parámetros
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date fechaHora = sdf.parse(fechaHoraParam);

            int duracionMinutos = Integer.parseInt(duracionParam);
            int idPista = Integer.parseInt(idPistaParam);
            int numeroAdultos = Integer.parseInt(numeroAdultosParam);
            int numeroNinos = Integer.parseInt(numeroNinosParam);

            // Obtener los objetos necesarios
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

            // Realizar la reserva utilizando el bono
            boolean reservaExitosa = reservasDAO.hacerReservaBono(jugadorDTO, fechaHora, duracionMinutos, pistaDTO, numeroAdultos, numeroNinos);

            if (reservaExitosa) {
                // Obtener información del bono actualizado
                Bono bonoActualizado = reservasDAO.obtenerBonoPorJugador(jugadorDTO.getIdJugador());

                // Obtener la reserva asociada al bono y usuario
                ReservaDTO reservaDTO = reservasDAO.obtenerReservaPorIdBono(jugadorDTO.getIdJugador(), bonoActualizado);

                if (reservaDTO == null) {
                    throw new IllegalStateException("No se pudo recuperar la reserva asociada al bono.");
                }

                // Preparar el ReservaBean
                ReservaBean reservaBean = new ReservaBean();
                reservaBean.setIdReserva(reservaDTO.getIdReserva());
                reservaBean.setFechaHora(reservaDTO.getFechaHora());
                reservaBean.setDuracionMinutos(reservaDTO.getDuracionMinutos());
                reservaBean.setIdPista(reservaDTO.getIdPista());
                reservaBean.setNumeroAdultos(numeroAdultos);
                reservaBean.setNumeroNinos(numeroNinos);
                reservaBean.setPrecio(reservaDTO.getPrecio());
                reservaBean.setDescuento(reservaDTO.getDescuento());

                // Incluir información del bono en el bean
                reservaBean.setIdBono(bonoActualizado.getIdBono());
                reservaBean.setNumeroSesion(5 - bonoActualizado.getSesionesRestantes());

                // Pasar el bean a la vista
                request.setAttribute("reserva", reservaBean);
                request.getRequestDispatcher("/mvc/view/client/resultadoReserva.jsp").forward(request, response);
            } else {
                throw new IllegalStateException("No se pudo realizar la reserva.");
            }

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




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        // Verificar si es una solicitud para crear un bono
        String crearBonoParam = request.getParameter("crearBono");

        if (crearBonoParam != null && crearBonoParam.equals("true")) {
            gestionarCreacionBono(request, response, customer);
        } else {
            gestionarReserva(request, response, customer);
        }
    }



}
