function validarFormulario() {
    const fechaHora = document.getElementById("nuevaFechaHora");
    const duracion = document.getElementById("nuevaDuracion");
    const numeroAdultos = parseInt(document.getElementById("nuevosAdultos").value || 0);
    const numeroNinos = parseInt(document.getElementById("nuevosNinos").value || 0);
    const tipoReserva = document.getElementById("tipoReserva").value;
    const errores = [];

    // Validar fecha y hora
    const fechaSeleccionada = new Date(fechaHora.value);
    const fechaActual = new Date();
    fechaActual.setHours(fechaActual.getHours() + 24);

    if (!fechaHora.value || fechaSeleccionada <= fechaActual) {
        errores.push("La fecha y hora seleccionadas deben ser al menos 24 horas en el futuro.");
    }

    // Validar duración
    const duracionesValidas = [60, 90, 120];
    if (!duracionesValidas.includes(parseInt(duracion.value))) {
        errores.push("La duración seleccionada no es válida.");
    }

    // Validar tipo de reserva y número de participantes
    if (tipoReserva === "infantil" && numeroNinos <= 0) {
        errores.push("Una reserva infantil debe incluir al menos un niño.");
    }

    if (tipoReserva === "familiar" && (numeroAdultos <= 0 || numeroNinos <= 0)) {
        errores.push("Una reserva familiar debe incluir al menos un adulto y un niño.");
    }

    if (tipoReserva === "adulto" && numeroAdultos <= 0) {
        errores.push("Una reserva de adultos debe incluir al menos un adulto.");
    }

    // Mostrar errores
    if (errores.length > 0) {
        alert("Errores encontrados:\n" + errores.join("\n"));
        return false;
    }

    return true;
}

function actualizarPistas() {
    const esReservaBono = document.getElementById("esReservaBono").value === "true";

    // Si es una reserva de bono, no realizar la actualización automática
    if (esReservaBono) {
        console.log("Actualización deshabilitada para reservas de bono.");
        return; // Salir sin enviar el formulario
    }

    // Enviar formulario solo si no es una reserva de bono
    document.getElementById("filtrarPistasForm").submit();
}

