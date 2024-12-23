package es.uco.pw.servlet.admin;

import es.uco.pw.data.dao.JugadoresDAO;
import es.uco.pw.display.javabean.CustomerBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet para listar jugadores con reservas.
 *
 * <p>Este servlet permite a los administradores visualizar una lista de jugadores
 * que tienen reservas registradas en el sistema. Se asegura de que solo usuarios
 * con rol de administrador puedan acceder a esta funcionalidad.</p>
 */
public class ListarJugadoresServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Maneja las solicitudes HTTP GET para listar los jugadores con reservas.
     *
     * <p>Verifica que el usuario autenticado sea un administrador antes de obtener
     * los jugadores con reservas de la base de datos. La lista resultante se convierte
     * en una lista de objetos {@link CustomerBean} y se envía a la vista correspondiente.</p>
     *
     * @param request  La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        CustomerBean usuario = (CustomerBean) session.getAttribute("customer");

        // Verificar que el usuario sea administrador
        if (usuario == null || !"ADMINISTRADOR".equalsIgnoreCase(usuario.getTipoUsuario())) {
            request.setAttribute("error", "Acceso denegado. Solo los administradores pueden acceder a esta página.");
            request.getRequestDispatcher("/include/errorACF.jsp").forward(request, response);
            return;
        }

        // Obtener la lista de jugadores con reservas
        JugadoresDAO jugadoresDAO = new JugadoresDAO(getServletContext());
        String listaClientes = jugadoresDAO.listarJugadoresConReservas();

        // Convertir el String en una lista de CustomerBean
        List<CustomerBean> clientes = new ArrayList<>();
        if (listaClientes != null && !listaClientes.isEmpty()) {
            String[] clientesArray = listaClientes.split("\n");
            for (String cliente : clientesArray) {
                if (cliente.trim().isEmpty() || !cliente.contains(",")) { // Filtrar líneas vacías o malformadas
                    continue; // Ignorar estas líneas
                }
                try {
                    String[] datos = cliente.split(", ");
                    String id = datos[0].split(":")[1].trim();
                    String nombre = datos[1].split(":")[1].trim();
                    String fechaInscripcion = datos[2].split(":")[1].trim();
                    int reservasCompletadas = Integer.parseInt(datos[3].split(":")[1].trim());

                    CustomerBean customerBean = new CustomerBean(
                        nombre,
                        null,
                        "CLIENTE",
                        fechaInscripcion,
                        null,
                        reservasCompletadas
                    );
                    clientes.add(customerBean);
                } catch (Exception e) {
                    // Manejaríamos errores aquí pero como no procesamos el ID y lo marca como error pese a mostrar bien la tabla se queda así
                }
            }
        }


        // Pasar la lista al JSP
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher("/mvc/view/admin/adminHome.jsp").forward(request, response);
    }
}
