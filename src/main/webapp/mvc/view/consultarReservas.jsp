<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Reservas</title>
    <script src="../js/validarConsultaReserva.js"></script>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <h1>Consulta de Reservas</h1>
    <form action="<c:url value='/cliente/consultarReservas' />" method="post" onsubmit="return validarFechas()">
        <label for="fechaInicio">Fecha Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" required>
        <br><br>
        <label for="fechaFin">Fecha Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" required>
        <br><br>
        <button type="submit">Consultar Reservas</button>
    </form>
</body>
</html>
