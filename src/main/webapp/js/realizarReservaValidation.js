function validarFormulario() {
    const numeroAdultos = document.getElementById("numeroAdultos");
    const numeroNinos = document.getElementById("numeroNinos");
    const tipoReserva = document.getElementById("tipoReserva");
    const fechaHora = document.getElementById("fechaHora");
    const duracion = document.getElementById("duracion");
    const idPista = document.getElementById("idPista");

    let errores = [];

    // Validar número de adultos y niños según el tipo de reserva
    const adultos = parseInt(numeroAdultos.value) || 0;
    const ninos = parseInt(numeroNinos.value) || 0;

    if (tipoReserva.value === "adulto") {
        if (adultos <= 0) {
            errores.push("Debe haber al menos un adulto en una reserva de tipo adulto.");
        }
        if (ninos > 0) {
            errores.push("No puede haber niños en una reserva de tipo adulto.");
        }
    }

    if (tipoReserva.value === "infantil") {
        if (ninos <= 0) {
            errores.push("Debe haber al menos un niño en una reserva de tipo infantil.");
        }
        if (adultos > 0) {
            errores.push("No puede haber adultos en una reserva de tipo infantil.");
        }
    }

    if (tipoReserva.value === "familiar") {
        if (adultos <= 0 || ninos <= 0) {
            errores.push("Debe haber al menos un adulto y un niño en una reserva de tipo familiar.");
        }
    }

    // Validar fecha y hora
    if (!fechaHora.value) {
        errores.push("Debes seleccionar una fecha y hora válida.");
    } else {
        const fechaSeleccionada = new Date(fechaHora.value);
        const fechaActual = new Date();
        if (fechaSeleccionada <= fechaActual) {
            errores.push("La fecha y hora seleccionadas deben ser futuras.");
        }
    }

    // Validar duración
    const duracionesValidas = ["60", "90", "120"];
    if (!duracionesValidas.includes(duracion.value)) {
        errores.push("La duración seleccionada no es válida.");
    }

    // Validar selección de pista
    if (!idPista.value) {
        errores.push("Debes seleccionar una pista válida.");
    }

    // Mostrar errores si existen
    if (errores.length > 0) {
        alert("Errores encontrados:\n" + errores.join("\n"));
        return false; // Cancelar el envío del formulario
    }

    return true; // Permitir el envío del formulario
}
