package es.uco.pw.display.javabean;

import java.io.Serializable;
import es.uco.pw.business.pista.TamanoPista;

/**
 * PistaBean representa una pista deportiva para su uso en un entorno web.
 * Encapsula los datos más relevantes de una pista.
 */
public class PistaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único de la pista */
    private int idPista;

    /** Nombre de la pista */
    private String nombrePista;

    /** Indica si la pista está disponible */
    private boolean disponible;

    /** Indica si la pista es de tipo exterior */
    private boolean exterior;

    /** Tamaño de la pista */
    private TamanoPista tamanoPista;

    /** Número máximo de jugadores permitidos */
    private int maxJugadores;

    /**
     * Constructor vacío, necesario para que sea un JavaBean.
     */
    public PistaBean() {
    }

    /**
     * Constructor parametrizado para inicializar los atributos de la pista.
     *
     * @param idPista       Identificador único de la pista.
     * @param nombrePista   Nombre de la pista.
     * @param disponible    Estado de disponibilidad de la pista.
     * @param exterior      Si la pista es exterior o no.
     * @param tamanoPista   Tamaño de la pista.
     * @param maxJugadores  Número máximo de jugadores permitidos.
     */
    public PistaBean(int idPista, String nombrePista, boolean disponible, boolean exterior, TamanoPista tamanoPista, int maxJugadores) {
        this.idPista = idPista;
        this.nombrePista = nombrePista;
        this.disponible = disponible;
        this.exterior = exterior;
        this.tamanoPista = tamanoPista;
        this.maxJugadores = maxJugadores;
    }

    // Métodos getter y setter

    public int getIdPista() {
        return idPista;
    }

    public void setIdPista(int idPista) {
        this.idPista = idPista;
    }

    public String getNombrePista() {
        return nombrePista;
    }

    public void setNombrePista(String nombrePista) {
        this.nombrePista = nombrePista;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isExterior() {
        return exterior;
    }

    public void setExterior(boolean exterior) {
        this.exterior = exterior;
    }

    public TamanoPista getTamanoPista() {
        return tamanoPista;
    }

    public void setTamanoPista(TamanoPista tamanoPista) {
        this.tamanoPista = tamanoPista;
    }

    public int getMaxJugadores() {
        return maxJugadores;
    }

    public void setMaxJugadores(int maxJugadores) {
        this.maxJugadores = maxJugadores;
    }

    /**
     * Representación en formato texto de los atributos de la pista.
     *
     * @return Cadena de texto con los atributos de la pista.
     */
    @Override
    public String toString() {
        return "PistaBean{" +
                "idPista=" + idPista +
                ", nombrePista='" + nombrePista + '\'' +
                ", disponible=" + disponible +
                ", exterior=" + exterior +
                ", tamanoPista=" + tamanoPista +
                ", maxJugadores=" + maxJugadores +
                '}';
    }
}
