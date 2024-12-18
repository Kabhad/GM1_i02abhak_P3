package es.uco.pw.display.javabean;

import es.uco.pw.business.material.EstadoMaterial;
import es.uco.pw.business.material.TipoMaterial;

/**
 * Bean para encapsular los datos del MaterialDTO.
 */
public class MaterialBean {

    /**
     * Identificador único del material.
     */
    private int id;

    /**
     * Tipo de material (pelotas, canastas, conos).
     */
    private String tipo;

    /**
     * Indica si el material es apto para uso en exteriores.
     */
    private boolean usoExterior;

    /**
     * Estado del material (disponible, reservado, mal estado).
     */
    private String estado;

    /**
     * Constructor por defecto.
     */
    public MaterialBean() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param id          Identificador del material.
     * @param tipo        Tipo del material.
     * @param usoExterior Uso exterior del material.
     * @param estado      Estado del material.
     */
    public MaterialBean(int id, TipoMaterial tipo, boolean usoExterior, EstadoMaterial estado) {
        this.id = id;
        this.tipo = tipo.name();
        this.usoExterior = usoExterior;
        this.estado = estado.name();
    }

    /**
     * Obtiene el identificador del material.
     *
     * @return El identificador del material.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del material.
     *
     * @param id El nuevo identificador del material.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo del material.
     *
     * @return El tipo del material.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del material.
     *
     * @param tipo El nuevo tipo del material.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Indica si el material es apto para uso exterior.
     *
     * @return true si es para uso exterior; false en caso contrario.
     */
    public boolean isUsoExterior() {
        return usoExterior;
    }

    /**
     * Establece si el material es apto para uso exterior.
     *
     * @param usoExterior true si es para uso exterior; false en caso contrario.
     */
    public void setUsoExterior(boolean usoExterior) {
        this.usoExterior = usoExterior;
    }

    /**
     * Obtiene el estado del material.
     *
     * @return El estado del material.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del material.
     *
     * @param estado El nuevo estado del material.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Devuelve una representación en forma de cadena del bean.
     *
     * @return Representación del MaterialBean como cadena.
     */
    @Override
    public String toString() {
        return "MaterialBean [id=" + id + ", tipo=" + tipo + ", usoExterior=" + usoExterior + ", estado=" + estado + "]";
    }
}
