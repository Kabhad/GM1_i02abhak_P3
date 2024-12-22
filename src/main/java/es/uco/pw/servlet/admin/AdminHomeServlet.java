package es.uco.pw.servlet.admin;

import es.uco.pw.data.dao.JugadoresDAO;
import es.uco.pw.display.javabean.CustomerBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/home")
public class AdminHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext application = getServletContext();
        JugadoresDAO jugadoresDAO = new JugadoresDAO(application);

        // Obtener la lista de clientes desde el DAO
        List<CustomerBean> clientes = jugadoresDAO.obtenerListaDeClientes();

        // Pasar los datos como atributo a la vista
        request.setAttribute("clientes", clientes);

        // Redirigir a la vista del panel de administración
        request.getRequestDispatcher("/mvc/view/admin/adminHome.jsp").forward(request, response);
    }
}
