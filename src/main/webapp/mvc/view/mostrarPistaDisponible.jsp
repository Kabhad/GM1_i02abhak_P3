<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.PistaBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pistas Disponibles</title>
</head>
<body>
    <h1>Pistas Disponibles</h1>

    <%
        // Recuperar la lista de pistas desde el atributo de la solicitud
        List<PistaBean> pistasDisponibles = (List<PistaBean>) request.getAttribute("pistasDisponibles");

        if (pistasDisponibles != null && !pistasDisponibles.isEmpty()) {
    %>
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
        <p>No hay pistas disponibles en este momento.</p>
    <%
        }
    %>
</body>
</html>
