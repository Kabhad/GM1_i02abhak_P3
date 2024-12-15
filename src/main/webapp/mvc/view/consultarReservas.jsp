<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Reservas</title>
    <script src="../js/validarConsultaReserva.js"></script>
    <link rel="stylesheet" href="../../css/consultarReservas.css">
</head>
<body>
    <h1>Consulta de Reservas</h1>
    <form action="${pageContext.request.contextPath}/client/consultarReserva" method="post" onsubmit="return validarFechas()">
        <label for="fechaInicio">Fecha Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" required>

        <label for="fechaFin">Fecha Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" required>

        <button type="submit">Consultar Reservas</button>
    </form>

    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="../view/clientHome.jsp" class="nav-button">Volver al Menú Principal</a>
    </div>
</body>
</html>
