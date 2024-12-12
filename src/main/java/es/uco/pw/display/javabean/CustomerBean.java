package es.uco.pw.display.javabean;

import java.io.Serializable;

/**
 * CustomerBean representa a un usuario que ha iniciado sesión en el sistema.
 * Encapsula los datos más relevantes del usuario para mantener su información en la sesión.
 */
public class CustomerBean implements Serializable {

    // Serializable ID, útil para la serialización en servidores distribuidos.
    private static final long serialVersionUID = 1L;

    /** Nombre completo del usuario */
    private String nombre;

    /** Correo electrónico del usuario */
    private String correo;

    /** Tipo de usuario (administrador o cliente) */
    private String tipoUsuario;

    /** Fecha de inscripción en el sistema */
    private String fechaInscripcion;

    /** Fecha de la próxima reserva, si aplica */
    private String fechaProximaReserva;

    /**
     * Constructor por defecto, necesario para que sea un JavaBean.
     */
    public CustomerBean() {
    }

    /**
     * Constructor con parámetros para inicializar todos los campos.
     *
     * @param nombre              Nombre completo del usuario.
     * @param correo              Correo electrónico del usuario.
     * @param tipoUsuario         Tipo de usuario (administrador o cliente).
     * @param fechaInscripcion    Fecha de inscripción en el sistema.
     * @param fechaProximaReserva Fecha de la próxima reserva.
     */
    public CustomerBean(String nombre, String correo, String tipoUsuario, String fechaInscripcion, String fechaProximaReserva) {
        this.nombre = nombre;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaProximaReserva = fechaProximaReserva;
    }

    // Métodos getter y setter para encapsular el acceso a los atributos.

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del usuario.
     *
     * @param nombre Nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo Correo del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el tipo de usuario.
     *
     * @return Tipo de usuario (administrador o cliente).
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Establece el tipo de usuario.
     *
     * @param tipoUsuario Tipo de usuario (administrador o cliente).
     */
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Obtiene la fecha de inscripción en el sistema.
     *
     * @return Fecha de inscripción.
     */
    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    /**
     * Establece la fecha de inscripción en el sistema.
     *
     * @param fechaInscripcion Fecha de inscripción.
     */
    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    /**
     * Obtiene la fecha de la próxima reserva.
     *
     * @return Fecha de la próxima reserva o null si no aplica.
     */
    public String getFechaProximaReserva() {
        return fechaProximaReserva;
    }

    /**
     * Establece la fecha de la próxima reserva.
     *
     * @param fechaProximaReserva Fecha de la próxima reserva.
     */
    public void setFechaProximaReserva(String fechaProximaReserva) {
        this.fechaProximaReserva = fechaProximaReserva;
    }

    /**
     * Proporciona una representación en texto de este objeto.
     *
     * @return Información del CustomerBean en formato legible.
     */
    @Override
    public String toString() {
        return "CustomerBean{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", fechaInscripcion='" + fechaInscripcion + '\'' +
                ", fechaProximaReserva='" + fechaProximaReserva + '\'' +
                '}';
    }
}
