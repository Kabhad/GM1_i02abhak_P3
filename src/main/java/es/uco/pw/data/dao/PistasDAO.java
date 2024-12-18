package es.uco.pw.data.dao;


import es.uco.pw.business.material.EstadoMaterial;
import es.uco.pw.business.material.MaterialDTO;
import es.uco.pw.business.material.TipoMaterial;
import es.uco.pw.business.pista.PistaDTO;
import es.uco.pw.business.pista.TamanoPista;
import es.uco.pw.data.common.DBConnection;
import java.util.*;

import javax.servlet.ServletContext;

import java.io.*;
import java.sql.*;

/**
 * Clase que gestiona las pistas y materiales del sistema.
 * Implementa el patrón Singleton para asegurar que solo haya una instancia de esta clase.
 */
public class PistasDAO {

	
	/**
	 * Conexión actual con la base de datos.
	 */
    private java.sql.Connection con;

	/**
	 * Propiedades con las consultas SQL necesarias para el funcionamiento del DAO.
	 */
	private Properties prop;

	/**
	 * Constructor que carga las propiedades SQL desde el archivo `sql.properties`.
	 * 
	 * @param application ServletContext para acceder a los parámetros y archivos de configuración.
	 */
	public PistasDAO(ServletContext application) {
	    prop = new Properties();
	    try {
	        // Obtener la ruta al archivo 'sql.properties' desde el contexto de la aplicación (web.xml)
	        String path = application.getInitParameter("sqlproperties");
	        if (path == null) {
	            throw new FileNotFoundException("El parámetro 'sqlproperties' no está definido en web.xml");
	        }

	        // Obtener la ruta física y cargar el archivo de propiedades
	        BufferedReader reader = new BufferedReader(new FileReader(application.getRealPath(path)));
	        prop.load(reader);
	        reader.close();
	    } catch (FileNotFoundException e) {
	        System.err.println("Archivo 'sql.properties' no encontrado. Verifica la ruta en web.xml.");
	        e.printStackTrace();
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo 'sql.properties'.");
	        e.printStackTrace();
	    }
	}


    /**
     * Método para crear una nueva pista y añadirla a la lista de pistas.
     * 
     * @param nombre       Nombre de la nueva pista.
     * @param disponible   Indica si la pista está disponible.
     * @param exterior     Indica si la pista es exterior.
     * @param pista        Tamaño de la pista.
     * @param maxJugadores Número máximo de jugadores en la pista.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void crearPista(String nombre, boolean disponible, boolean exterior, TamanoPista pista, int maxJugadores) throws SQLException {
        DBConnection conexion = new DBConnection();
        con = (Connection) conexion.getConnection();
        PistaDTO pistaE = buscarPistaPorNombre(nombre);
        if (pistaE != null) {
            throw new IllegalArgumentException("Ya existe una pista con el nombre especificado.");
        }
        String sql = prop.getProperty("crearPista");
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setBoolean(2, disponible);
            ps.setBoolean(3, exterior);
            ps.setString(4, pista.name());
            ps.setInt(5, maxJugadores);
            ps.executeUpdate();
            ps.close();
        } finally {
            conexion.closeConnection();
        }
    }

    /**
     * Método para crear un nuevo material y añadirlo a la lista de materiales.
     * 
     * @param idMaterial   Identificador del nuevo material.
     * @param tipo         Tipo del nuevo material.
     * @param usoExterior  Indica si el material es para uso exterior.
     * @param estado       Estado del nuevo material.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void crearMaterial(int idMaterial, TipoMaterial tipo, boolean usoExterior, EstadoMaterial estado) throws SQLException {
        DBConnection conexion = new DBConnection();
        con = (Connection) conexion.getConnection();
        MaterialDTO materialExistente = buscarMaterialPorId(idMaterial);
        if (materialExistente != null) {
            throw new IllegalArgumentException("Ya existe un material con el ID especificado.");
        }
        String sql = "INSERT INTO Material (idMaterial, tipo, usoExterior, estado) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMaterial);
            ps.setString(2, tipo.name());
            ps.setBoolean(3, usoExterior);
            ps.setString(4, estado.name());
            ps.executeUpdate();
            ps.close();
        } finally {
            conexion.closeConnection();
        }
    }

    /**
     * Excepción personalizada para indicar que un elemento no fue encontrado.
     */
    public class ElementoNoEncontradoException extends Exception {
    	/**
    	 * Identificador para la serialización de la clase.
    	 */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor que acepta un mensaje.
         * 
         * @param message Mensaje de la excepción.
         */
        public ElementoNoEncontradoException(String message) {
            super(message);
        }
    }

    /**
     * Excepción personalizada para errores en la asociación de materiales.
     */
    public class AsociacionMaterialException extends Exception {
    	/**
    	 * Identificador para la serialización de la clase.
    	 */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor que acepta un mensaje.
         * 
         * @param message Mensaje de la excepción.
         */
        public AsociacionMaterialException(String message) {
            super(message);
        }
    }

    /**
     * Método para asociar un material a una pista disponible.
     * 
     *  @param nombrePista El nombre de la pista a la que se quiere asociar el material.
 * 	 *  @param idMaterial  El ID del material a asociar.
 * 	 *  @return {@code true} si la asociación se realizó con éxito; {@code false} en caso contrario.
 *   *  @throws SQLException Si ocurre un error al interactuar con la base de datos.
 *   *  @throws ElementoNoEncontradoException Si la pista o el material no se encuentran en la base de datos.
 *   *  @throws AsociacionMaterialException Si la pista no está disponible, el material no está en buen estado, 
 *   *          el material ya está reservado, o si no se cumplen las condiciones para la asociación.
     */
    public boolean asociarMaterialAPista(String nombrePista, int idMaterial) throws SQLException, ElementoNoEncontradoException, AsociacionMaterialException {
        DBConnection conexion = new DBConnection();
        con = (Connection) conexion.getConnection();
        
        // Consulta para buscar la pista por nombre
        String buscarPistaPorNombre = prop.getProperty("buscarPistaPorNombre");
        // Consulta para obtener el estado del material
        String obtenerEstadoMaterial = prop.getProperty("obtenerEstadoMaterial");
        // Consulta para asociar el material a la pista
        String asociarMaterialAPista = prop.getProperty("asociarMaterialAPista");
        // Consulta para actualizar el estado del material
        String actualizarEstadoMaterial = prop.getProperty("actualizarEstadoMaterial");
        // Consulta para verificar si el material ya esta asociado
        String verificarMateriales = prop.getProperty("verificarMaterialesAsociados");

        // Buscar la pista por nombre primero y verificar disponibilidad
        try (PreparedStatement psBuscar = con.prepareStatement(buscarPistaPorNombre)) {
            psBuscar.setString(1, nombrePista);
            ResultSet rs = psBuscar.executeQuery();
            if (!rs.next()) {
                throw new ElementoNoEncontradoException("No se encontró la pista con el nombre especificado.");
            }

            int idPista = rs.getInt("idPista");
            boolean esExterior = rs.getBoolean("exterior");
            boolean disponible = rs.getBoolean("disponible");

            // Validación de disponibilidad de la pista
            if (!disponible) {
                throw new AsociacionMaterialException("La pista no está disponible para la asociación de materiales.");
            }
            rs.close();

            // Ahora, verificar el estado del material
            try (PreparedStatement psEstado = con.prepareStatement(obtenerEstadoMaterial)) {
                psEstado.setInt(1, idMaterial);
                ResultSet rsEstado = psEstado.executeQuery();
                if (!rsEstado.next()) {
                    throw new ElementoNoEncontradoException("No se encontró el material con el ID especificado.");
                }
                TipoMaterial tipoMaterial = TipoMaterial.valueOf(rsEstado.getString("tipo"));
                boolean usoExteriorMaterial = rsEstado.getBoolean("usoExterior");
                String estadoMaterial = rsEstado.getString("estado");

                if ("MAL_ESTADO".equals(estadoMaterial) || "RESERVADO".equals(estadoMaterial)) {
                    throw new AsociacionMaterialException("El material no se puede asociar porque está en MAL_ESTADO o ya está RESERVADO.");
                }
                rsEstado.close();

                if (esExterior && !usoExteriorMaterial) {
                    throw new AsociacionMaterialException("El material no se puede usar en una pista exterior porque no es apto para exteriores.");
                }

                try (PreparedStatement psVerificar = con.prepareStatement(verificarMateriales)) {
                    psVerificar.setInt(1, idPista);
                    ResultSet rsVerificar = psVerificar.executeQuery();
                    int pelotas = 0, canastas = 0, conos = 0;

                    while (rsVerificar.next()) {
                        TipoMaterial tipo = TipoMaterial.valueOf(rsVerificar.getString("tipo"));
                        int cantidad = rsVerificar.getInt("cantidad");
                        switch (tipo) {
                            case PELOTAS:
                                pelotas = cantidad;
                                break;
                            case CANASTAS:
                                canastas = cantidad;
                                break;
                            case CONOS:
                                conos = cantidad;
                                break;
                        }
                    }
                    if (tipoMaterial == TipoMaterial.PELOTAS && pelotas >= 12) {
                        throw new AsociacionMaterialException("No se pueden añadir más de 12 pelotas a la pista.");
                    }
                    if (tipoMaterial == TipoMaterial.CANASTAS && canastas >= 2) {
                        throw new AsociacionMaterialException("No se pueden añadir más de 2 canastas a la pista.");
                    }
                    if (tipoMaterial == TipoMaterial.CONOS && conos >= 20) {
                        throw new AsociacionMaterialException("No se pueden añadir más de 20 conos a la pista.");
                    }
                }

                try (PreparedStatement psAsociar = con.prepareStatement(asociarMaterialAPista)) {
                    psAsociar.setInt(1, idPista);
                    psAsociar.setInt(2, idMaterial);
                    int filasActualizadas = psAsociar.executeUpdate();

                    if (filasActualizadas > 0) {
                        try (PreparedStatement psActualizarEstado = con.prepareStatement(actualizarEstadoMaterial)) {
                            psActualizarEstado.setInt(1, idMaterial);
                            psActualizarEstado.executeUpdate();
                        }
                        return true;
                    } else {
                        throw new AsociacionMaterialException("No se pudo asociar el material.");
                    }
                }
            }
        }
    }


    /**
     * Método auxiliar para buscar una pista por su nombre.
     * 
     * @param nombrePista Nombre de la pista a buscar.
     * @return La pista correspondiente al nombre dado, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public PistaDTO buscarPistaPorNombre(String nombrePista) throws SQLException {
        DBConnection conexion = new DBConnection();
        con = (Connection) conexion.getConnection();
        String sql = prop.getProperty("buscarPistaPorNombre");
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombrePista);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PistaDTO(rs.getString("nombre"), rs.getBoolean("disponible"),
                                        rs.getBoolean("exterior"), TamanoPista.valueOf(rs.getString("tamanoPista")),
                                        rs.getInt("maxJugadores"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método auxiliar para buscar un material por su ID.
     * 
     * @param idMaterial ID del material a buscar.
     * @return El material correspondiente al ID dado, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    private MaterialDTO buscarMaterialPorId(int idMaterial) throws SQLException {
        DBConnection conexion = new DBConnection();
        con = (Connection) conexion.getConnection();
        String sql = prop.getProperty("buscarMaterialPorId");
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMaterial);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MaterialDTO(rs.getInt("idMaterial"), TipoMaterial.valueOf(rs.getString("tipo")),
                                           rs.getBoolean("usoExterior"), EstadoMaterial.valueOf(rs.getString("estado")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método para buscar las pistas disponibles filtradas por tamaño, tipo (exterior/interior) y fecha.
     * Si tamano o exterior son null, no se aplica el filtro correspondiente.
     *
     * @param tamano El tamaño de la pista (MINIBASKET, ADULTOS, _3VS3) o null para "Cualquiera".
     * @param exterior Booleano que indica si la pista es exterior/interior, o null para "Cualquiera".
     * @param fecha La fecha en la que se busca la disponibilidad (formato "yyyy-MM-dd").
     * @return Lista de pistas disponibles que coinciden con los filtros.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<PistaDTO> buscarPistasPorTipoYFecha(TamanoPista tamano, Boolean exterior, String fecha) throws SQLException {
        List<PistaDTO> pistas = new ArrayList<>();
        DBConnection conexion = new DBConnection();
        this.con = conexion.getConnection();

        if (this.con == null) {
            throw new SQLException("Error: No se pudo obtener la conexión a la base de datos.");
        }
        if (this.prop == null) {
            throw new IllegalStateException("Error: Las propiedades 'prop' no están inicializadas.");
        }

        String sql = prop.getProperty("buscarPistasPorTipoYFecha");
        if (sql == null || sql.isEmpty()) {
            throw new IllegalStateException("Error: La consulta SQL para 'buscarPistasPorTipoYFecha' no está definida.");
        }

        try (PreparedStatement ps = this.con.prepareStatement(sql)) {
            // Parámetros para la consulta SQL
            ps.setObject(1, tamano != null ? tamano.name() : null); // TamanoPista
            ps.setObject(2, tamano != null ? tamano.name() : null); // Para el OR NULL
            ps.setObject(3, exterior); // Exterior
            ps.setObject(4, exterior); // Para el OR NULL
            ps.setString(5, fecha);    // Fecha

            // Ejecutar consulta
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pistas.add(new PistaDTO(
                    	rs.getInt("idPista"),  // Recupera el ID de la pista
                        rs.getString("nombre"),
                        rs.getBoolean("disponible"),
                        rs.getBoolean("exterior"),
                        TamanoPista.valueOf(rs.getString("tamanoPista")),
                        rs.getInt("maxJugadores")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta de pistas por tipo y fecha.");
            throw e;
        }
        return pistas;
    }


    /**
     * Método para listar todas las pistas no disponibles.
     * 
     * @return Lista de pistas no disponibles.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<PistaDTO> listarPistasNoDisponibles() throws SQLException {
        List<PistaDTO> pistas = new ArrayList<>();
        DBConnection conexion = new DBConnection();
        con = (Connection) conexion.getConnection();
        String sql = prop.getProperty("listarPistasNoDisponibles");
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            Map<Integer, PistaDTO> mapaPistas = new HashMap<>();
            while (rs.next()) {
                int idPista = rs.getInt("idPista");
                String nombrePista = rs.getString("nombre");
                if (!mapaPistas.containsKey(idPista)) {
                    PistaDTO pista = new PistaDTO(
                        idPista,
                        nombrePista,
                        rs.getBoolean("disponible"),
                        rs.getBoolean("exterior"),
                        TamanoPista.valueOf(rs.getString("tamanoPista")),
                        rs.getInt("maxJugadores")
                    );
                    mapaPistas.put(idPista, pista);
                }
                PistaDTO pista = mapaPistas.get(idPista);
                int idMaterial = rs.getInt("idMaterial");
                if (idMaterial != 0) {
                    TipoMaterial tipo = TipoMaterial.valueOf(rs.getString("tipo"));
                    EstadoMaterial estado = EstadoMaterial.valueOf(rs.getString("estado"));
                    MaterialDTO material = new MaterialDTO(
                        idMaterial,
                        tipo,
                        rs.getBoolean("usoExterior"),
                        estado
                    );
                    pista.getMateriales().add(material);
                }
            }
            pistas.addAll(mapaPistas.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pistas;
    }

    /**
     * Método para buscar pistas disponibles según el número de jugadores y tipo de pista.
     * 
     * @param numJugadores Número de jugadores que se busca.
     * @param tipoPista    Tipo de pista que se busca.
     * @return Lista de pistas disponibles que cumplen con los criterios dados.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<PistaDTO> buscarPistasDisponibles(int numJugadores, TamanoPista tipoPista) throws SQLException {
        List<PistaDTO> pistasFiltradas = new ArrayList<>();
        Map<Integer, PistaDTO> mapaPistas = new HashMap<>();
        DBConnection conexion = new DBConnection();
        this.con = conexion.getConnection();
        if (this.con == null) {
            System.err.println("Error: No se pudo obtener la conexión a la base de datos.");
            return pistasFiltradas;
        }
        if (this.prop == null) {
            System.err.println("Error: Las propiedades 'prop' no están inicializadas.");
            return pistasFiltradas;
        }
        String sql = this.prop.getProperty("buscarPistasDisponibles");
        if (sql == null || sql.isEmpty()) {
            System.err.println("Error: La consulta SQL para 'buscarPistasDisponibles' no está definida o está vacía.");
            return pistasFiltradas;
        }
        try (PreparedStatement ps = this.con.prepareStatement(sql)) {
            ps.setInt(1, numJugadores);
            ps.setString(2, tipoPista.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idPista = rs.getInt("idPista");
                    if (!mapaPistas.containsKey(idPista)) {
                        PistaDTO pista = new PistaDTO(
                            rs.getString("nombre"),
                            rs.getBoolean("disponible"),
                            rs.getBoolean("exterior"),
                            TamanoPista.valueOf(rs.getString("tamanoPista")),
                            rs.getInt("maxJugadores")
                        );
                        pista.setIdPista(idPista);
                        mapaPistas.put(idPista, pista);
                    }
                    PistaDTO pista = mapaPistas.get(idPista);
                    int idMaterial = rs.getInt("idMaterial");
                    if (idMaterial != 0) {
                        TipoMaterial tipo = TipoMaterial.valueOf(rs.getString("tipoMaterial"));
                        boolean usoExterior = rs.getBoolean("usoExterior");
                        EstadoMaterial estado = EstadoMaterial.valueOf(rs.getString("estado"));
                        MaterialDTO material = new MaterialDTO(idMaterial, tipo, usoExterior, estado);
                        pista.getMateriales().add(material);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta de pistas disponibles.");
            e.printStackTrace();
        }
        pistasFiltradas.addAll(mapaPistas.values());
        return pistasFiltradas;
    }

    /**
     * Método para listar pistas disponibles según el tipo de reserva.
     * 
     * @param tipoReserva Tipo de reserva (infantil, familiar, adulto).
     * @return Lista de pistas que cumplen con el tipo de reserva dado.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     * @throws IllegalArgumentException Si el tipo de reserva no es válido.
     */
    public List<PistaDTO> listarPistasDisponibles(String tipoReserva) throws SQLException {
        List<PistaDTO> pistas = new ArrayList<>();
        DBConnection conexion = new DBConnection();
        con = (Connection) conexion.getConnection();
        
        String sql = prop.getProperty("listarPistasDisponibles");
        if (sql == null || sql.isEmpty()) {
            System.err.println("Error: La consulta SQL para 'listarPistasDisponibles' no está definida o está vacía.");
            return pistas;
        }
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Configurar el tipo de pista según el tipo de reserva
            switch (tipoReserva.toLowerCase()) {
                case "infantil":
                    ps.setString(1, "MINIBASKET");
                    ps.setString(2, "MINIBASKET"); // Para mantener la consulta con dos parámetros
                    break;
                case "familiar":
                    ps.setString(1, "MINIBASKET");
                    ps.setString(2, "_3VS3");
                    break;
                case "adulto":
                    ps.setString(1, "ADULTOS");
                    ps.setString(2, "ADULTOS"); // Para mantener la consulta con dos parámetros
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de reserva no válido: " + tipoReserva);
            }

            try (ResultSet rs = ps.executeQuery()) {
                Map<Integer, PistaDTO> mapaPistas = new HashMap<>();
                while (rs.next()) {
                    int idPista = rs.getInt("idPista");
                    if (!mapaPistas.containsKey(idPista)) {
                        PistaDTO pista = new PistaDTO(
                            idPista,
                            rs.getString("nombre"),
                            rs.getBoolean("disponible"),
                            rs.getBoolean("exterior"),
                            TamanoPista.valueOf(rs.getString("tamanoPista")),
                            rs.getInt("maxJugadores")
                        );
                        mapaPistas.put(idPista, pista);
                    }
                    PistaDTO pista = mapaPistas.get(idPista);
                    int idMaterial = rs.getInt("idMaterial");
                    if (idMaterial != 0) {
                        TipoMaterial tipo = TipoMaterial.valueOf(rs.getString("tipo"));
                        EstadoMaterial estado = EstadoMaterial.valueOf(rs.getString("estado"));
                        MaterialDTO material = new MaterialDTO(
                            idMaterial,
                            tipo,
                            rs.getBoolean("usoExterior"),
                            estado
                        );
                        pista.getMateriales().add(material);
                    }
                }
                pistas.addAll(mapaPistas.values());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pistas;
    }


    /**
     * Método para listar todas las pistas con sus detalles.
     * 
     * @return Lista de pistas con sus detalles.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<PistaDTO> listarPistas() throws SQLException {
        List<PistaDTO> pistas = new ArrayList<>();
        String sql = this.prop.getProperty("listarPistas");
        if (sql == null || sql.isEmpty()) {
            System.err.println("Error: La consulta SQL para 'listarPistas' no está definida o está vacía.");
            return pistas;
        }
        if (this.prop == null) {
            System.err.println("Error: Las propiedades 'prop' no están inicializadas.");
            return pistas;
        }
        if (this.con == null) {
            DBConnection conexion = new DBConnection();
            this.con = conexion.getConnection();
            if (this.con == null) {
                System.err.println("Error: No se pudo obtener la conexión a la base de datos.");
                return pistas;
            }
        }
        try (PreparedStatement ps = this.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PistaDTO pista = new PistaDTO(
                    rs.getString("nombre"),
                    rs.getBoolean("disponible"),
                    rs.getBoolean("exterior"),
                    TamanoPista.valueOf(rs.getString("tamanoPista")),
                    rs.getInt("maxJugadores")
                );
                pista.setIdPista(rs.getInt("idPista"));
                pistas.add(pista);
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta de listar pistas.");
            e.printStackTrace();
        }
        return pistas;
    }
    
    /**
     * Método para obtener todos los materiales almacenados en la base de datos.
     *
     * @return Lista de objetos MaterialDTO con todos los materiales disponibles.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<MaterialDTO> obtenerTodosLosMateriales() throws SQLException {
        List<MaterialDTO> materiales = new ArrayList<>();
        DBConnection conexion = new DBConnection();
        this.con = conexion.getConnection();

        if (this.con == null) {
            throw new SQLException("Error: No se pudo obtener la conexión a la base de datos.");
        }
        String sql = prop.getProperty("obtenerTodosLosMateriales");
        if (sql == null || sql.isEmpty()) {
            throw new IllegalStateException("Error: La consulta SQL para 'obtenerTodosLosMateriales' no está definida.");
        }

        try (PreparedStatement ps = this.con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MaterialDTO material = new MaterialDTO(
                    rs.getInt("idMaterial"),
                    TipoMaterial.valueOf(rs.getString("tipo")),
                    rs.getBoolean("usoExterior"),
                    EstadoMaterial.valueOf(rs.getString("estado"))
                );
                materiales.add(material);
            }
        } finally {
            conexion.closeConnection();
        }
        return materiales;
    }
    
    /**
     * Método para modificar el estado de un material.
     * 
     * @param idMaterial El ID del material cuyo estado se desea modificar.
     * @param nuevoEstado El nuevo estado del material (DISPONIBLE, RESERVADO, MAL_ESTADO).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     * @throws IllegalArgumentException Si el nuevo estado es inválido.
     */
    public void modificarEstadoMaterial(int idMaterial, EstadoMaterial nuevoEstado) throws SQLException {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado del material no puede ser nulo.");
        }

        DBConnection conexion = new DBConnection();
        con = conexion.getConnection();
        String sql = prop.getProperty("modificarEstadoMaterial");

        if (sql == null || sql.isEmpty()) {
            throw new IllegalStateException("Error: La consulta SQL para 'modificarEstadoMaterial' no está definida.");
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, idMaterial);

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas == 0) {
                throw new SQLException("No se encontró un material con el ID especificado.");
            }
        } finally {
            conexion.closeConnection();
        }
    }

    /**
     * Método para modificar el estado de una pista.
     * 
     * @param idPista El ID de la pista cuyo estado se desea modificar.
     * @param disponible El nuevo estado de disponibilidad de la pista (true para disponible, false para no disponible).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void modificarEstadoPista(int idPista, boolean disponible) throws SQLException {
        DBConnection conexion = new DBConnection();
        con = conexion.getConnection();
        String sql = prop.getProperty("modificarEstadoPista");

        if (sql == null || sql.isEmpty()) {
            throw new IllegalStateException("Error: La consulta SQL para 'modificarEstadoPista' no está definida.");
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, disponible);
            ps.setInt(2, idPista);

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas == 0) {
                throw new SQLException("No se encontró una pista con el ID especificado.");
            }
        } finally {
            conexion.closeConnection();
        }
    }


    /**
     * Método para buscar una pista por su ID.
     * 
     * @param idPista ID de la pista a buscar.
     * @return La pista correspondiente al ID dado, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public PistaDTO buscarPistaPorId(int idPista) throws SQLException {
        DBConnection conexion = new DBConnection();
        con = (Connection) conexion.getConnection();
        String sql = prop.getProperty("buscarPistaPorId");
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPista);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PistaDTO(
                        rs.getInt("idPista"), rs.getString("nombre"),
                        rs.getBoolean("disponible"), rs.getBoolean("exterior"),
                        TamanoPista.valueOf(rs.getString("tamanoPista")),
                        rs.getInt("maxJugadores")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}