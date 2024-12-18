<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cancelar Reserva</title>
    <link rel="stylesheet" href="../../css/cancelarReserva.css">
</head>
<body>
    <h1>Cancelar Reserva</h1>
    <form id="cancelarReservaForm" action="${pageContext.request.contextPath}/CancelarReserva" method="POST">
        <label for="idReserva">ID de la Reserva:</label>
        <input type="number" name="idReserva" id="idReserva" required>
        <br><br>
        
        <label for="correoUsuario">Correo Electrónico:</label>
        <input type="email" name="correoUsuario" id="correoUsuario" required>
        <br><br>
        
        <button type="submit">Cancelar Reserva</button>
    </form>
    
    <!-- Botón para volver al menú principal -->
    <div class="button-container">
        <a href="../view/clientHome.jsp" class="nav-button">Volver al Menú Principal</a>
    </div>
</body>
</html>
