<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uco.pw.display.javabean.PistaBean" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Pistas</title>
</head>
<body>

<h2>Listado de Pistas</h2>

<!-- Inicia el ciclo for para iterar sobre la lista de pistas -->
<%
    List<PistaBean> pistas = (List<PistaBean>) request.getAttribute("pistas");
    if (pistas != null) {
        for (PistaBean pista : pistas) {
%>
            <div>
                <h3><%= pista.getNombrePista() %></h3>
                <p>Disponible: <%= pista.isDisponible() ? "Sí" : "No" %></p>
                <p>Exterior: <%= pista.isExterior() ? "Sí" : "No" %></p>
                <p>Tamaño: <%= pista.getTamanoPista() %></p>
                <p>Max Jugadores: <%= pista.getMaxJugadores() %></p>
            </div>
<%
        }
    } else {
%>
        <p>No hay pistas disponibles.</p>
<%
    }
%>

</body>
</html>
