<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Realizar Reserva</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/realizarReserva.css">
</head>
<body>
    <h1>Realizar una Nueva Reserva</h1>
    <form action="${pageContext.request.contextPath}/client/realizarReserva" method="post">
        <!-- Selección de tipo de reserva -->
        <label for="tipoReserva">Tipo de Reserva:</label><br>
        <select id="tipoReserva" name="tipoReserva" onchange="filtrarPistas()" required>
            <option value="adulto">Adulto</option>
            <option value="infantil">Infantil</option>
            <option value="familiar">Familiar</option>
        </select><br><br>

        <!-- Selección de pista -->
        <label for="idPista">Pista Disponible:</label><br>
        <select id="idPista" name="idPista" required>
            <option value="" disabled selected>Selecciona una pista</option>
            <!-- Las opciones de pista se cargarán dinámicamente con JavaScript -->
        </select><br><br>

        <!-- Fecha y hora -->
        <label for="fechaHora">Fecha y Hora:</label><br>
        <input type="datetime-local" id="fechaHora" name="fechaHora" required><br><br>

        <!-- Duración -->
        <label for="duracion">Duración:</label><br>
        <select id="duracion" name="duracion" required>
            <option value="60">1 Hora</option>
            <option value="90">1 Hora y 30 Minutos</option>
            <option value="120">2 Horas</option>
        </select><br><br>

        <!-- Número de adultos -->
        <label for="numeroAdultos">Número de Adultos:</label><br>
        <input type="number" id="numeroAdultos" name="numeroAdultos" min="0" required><br><br>

        <!-- Número de niños -->
        <label for="numeroNinos">Número de Niños:</label><br>
        <input type="number" id="numeroNinos" name="numeroNinos" min="0" required><br><br>

        <!-- Botón de envío -->
        <button type="submit" class="btn-primary">Realizar Reserva</button>
    </form>

    <script>
        // Simulación de pistas disponibles por tipo
        const pistasDisponibles = {
            adulto: [
                { id: 1, nombre: "Pista A - Exterior" },
                { id: 2, nombre: "Pista B - Interior" }
            ],
            infantil: [
                { id: 3, nombre: "Pista C - Infantil" },
                { id: 4, nombre: "Pista D - Multiuso" }
            ],
            familiar: [
                { id: 1, nombre: "Pista A - Exterior" },
                { id: 3, nombre: "Pista C - Infantil" }
            ]
        };

        // Filtrar las pistas según el tipo de reserva seleccionado
        function filtrarPistas() {
            const tipo = document.getElementById("tipoReserva").value;
            const pistaSelect = document.getElementById("idPista");

            // Limpiar opciones anteriores
            pistaSelect.innerHTML = '<option value="" disabled selected>Selecciona una pista</option>';

            // Agregar nuevas opciones
            if (pistasDisponibles[tipo]) {
                pistasDisponibles[tipo].forEach(pista => {
                    const option = document.createElement("option");
                    option.value = pista.id;
                    option.textContent = pista.nombre;
                    pistaSelect.appendChild(option);
                });
            }
        }
    </script>
</body>
</html>
