package es.uco.pw.display.javabean;

import java.io.Serializable;
import java.util.Date;

/**
 * JavaBean que encapsula los datos de una reserva para la vista.
 */
public class ReservaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idReserva;
    private Date fechaHora;
    private int duracionMinutos;
    private int idPista;
    private float precio;
    private float descuento;

    // Atributos específicos para reservas con bono
    private Integer idBono;
    private Integer numeroSesion;

    // Atributos específicos para reservas familiares e infantiles
    private Integer numeroAdultos = 0; // Inicializado a 0
    private Integer numeroNinos = 0;   // Inicializado a 0

    // Constructor vacío
    public ReservaBean() { }

    // Getters y Setters
    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public int getIdPista() { return idPista; }
    public void setIdPista(int idPista) { this.idPista = idPista; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    public float getDescuento() { return descuento; }
    public void setDescuento(float descuento) { this.descuento = descuento; }

    public Integer getIdBono() { return idBono; }
    public void setIdBono(Integer idBono) { this.idBono = idBono; }

    public Integer getNumeroSesion() { return numeroSesion; }
    public void setNumeroSesion(Integer numeroSesion) { this.numeroSesion = numeroSesion; }

    public Integer getNumeroAdultos() { return numeroAdultos; }
    public void setNumeroAdultos(Integer numeroAdultos) { this.numeroAdultos = numeroAdultos; }

    public Integer getNumeroNinos() { return numeroNinos; }
    public void setNumeroNinos(Integer numeroNinos) { this.numeroNinos = numeroNinos; }

    /**
     * Método calculado: Devuelve el tipo de reserva basado en los valores de adultos y niños.
     * @return String con el tipo de reserva: "Infantil", "Adulto" o "Familiar".
     */
    /**
     * Método calculado: Devuelve el tipo de reserva basado en los valores de adultos y niños.
     * @return String con el tipo de reserva: "Infantil", "Adulto" o "Familiar".
     */
    public String getTipoReserva() {
        if ((numeroAdultos != null && numeroAdultos > 0) && (numeroNinos == null || numeroNinos == 0)) {
            return "Adulto";
        } else if ((numeroNinos != null && numeroNinos > 0) && (numeroAdultos == null || numeroAdultos == 0)) {
            return "Infantil";
        } else if (numeroAdultos != null && numeroAdultos > 0 && numeroNinos != null && numeroNinos > 0) {
            return "Familiar";
        }
        return "Desconocido";
    }

}
