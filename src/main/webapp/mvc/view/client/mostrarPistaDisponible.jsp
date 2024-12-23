<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.PistaBean" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Enlace al archivo CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mostrarPistaDisponible.css">
    <title>Pistas Disponibles</title>
</head>
<body>
    <h1>Pistas Disponibles</h1>

    <%
        // Obtener la lista de pistas disponibles de la solicitud
        List<PistaBean> pistasDisponibles = (List<PistaBean>) request.getAttribute("pistasDisponibles");

        // Comprobar si hay pistas disponibles
        if (pistasDisponibles != null && !pistasDisponibles.isEmpty()) {
    %>
        <!-- Tabla para mostrar las pistas disponibles -->
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Disponible</th>
                    <th>Exterior</th>
                    <th>Tamaño</th>
                    <th>Máx. Jugadores</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Recorrer la lista de pistas y mostrarlas en la tabla
                    for (PistaBean pista : pistasDisponibles) {
                %>
                    <tr>
                        <td><%= pista.getIdPista() %></td>
                        <td><%= pista.getNombrePista() %></td>
                        <td><%= pista.isDisponible() ? "Sí" : "No" %></td>
                        <td><%= pista.isExterior() ? "Sí" : "No" %></td>
                        <td><%= pista.getTamanoPista() %></td>
                        <td><%= pista.getMaxJugadores() %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <!-- Mensaje si no hay pistas disponibles -->
        <p>No hay pistas disponibles en este momento.</p>
    <%
        }
    %>
    
    <!-- Botones de navegación -->
    <div class="button-container">
        <a href="../mvc/view/client/buscarPistaDisponible.jsp" class="nav-button">Hacer Otra Búsqueda</a>
        <a href="../mvc/view/client/clientHome.jsp" class="nav-button">Volver al Menú Principal</a>
    </div>
    
</body>
</html>
