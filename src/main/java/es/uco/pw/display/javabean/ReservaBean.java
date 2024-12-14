package es.uco.pw.display.javabean;

import es.uco.pw.business.reserva.ReservaDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase JavaBean que encapsula las reservas y proporciona métodos
 * para separar las reservas futuras y finalizadas.
 */
public class ReservaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Lista de todas las reservas del usuario.
     */
    private List<ReservaDTO> reservas;

    /**
     * Constructor por defecto.
     */
    public ReservaBean() {
    }

    /**
     * Constructor que inicializa el bean con una lista de reservas.
     *
     * @param reservas La lista de reservas a encapsular.
     */
    public ReservaBean(List<ReservaDTO> reservas) {
        this.reservas = reservas;
    }

    /**
     * Obtiene todas las reservas.
     *
     * @return La lista de todas las reservas.
     */
    public List<ReservaDTO> getReservas() {
        return reservas;
    }

    /**
     * Establece la lista de reservas.
     *
     * @param reservas La lista de reservas a establecer.
     */
    public void setReservas(List<ReservaDTO> reservas) {
        this.reservas = reservas;
    }

    /**
     * Obtiene las reservas futuras (fecha posterior a la fecha actual).
     *
     * @return Una lista de reservas futuras.
     */
    public List<ReservaDTO> getReservasFuturas() {
        Date now = new Date();
        return reservas.stream()
                .filter(reserva -> reserva.getFechaHora().after(now))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene las reservas finalizadas (fecha anterior o igual a la fecha actual).
     *
     * @return Una lista de reservas finalizadas.
     */
    public List<ReservaDTO> getReservasFinalizadas() {
        Date now = new Date();
        return reservas.stream()
                .filter(reserva -> reserva.getFechaHora().before(now) || reserva.getFechaHora().equals(now))
                .collect(Collectors.toList());
    }

    /**
     * Representación en cadena del bean, para depuración.
     *
     * @return Una cadena que representa el estado del bean.
     */
    @Override
    public String toString() {
        return "ReservaBean{" +
                "reservas=" + reservas +
                '}';
    }
}
