document.addEventListener("DOMContentLoaded", function () {
    const formulario = document.querySelector("form");
    const numeroAdultos = document.getElementById("numeroAdultos");
    const numeroNinos = document.getElementById("numeroNinos");
    const tipoReserva = document.getElementById("tipoReserva");

    formulario.addEventListener("submit", function (event) {
        let errores = [];

        // Validar número de adultos y niños según el tipo de reserva
        if (tipoReserva.value === "adulto" && parseInt(numeroAdultos.value) <= 0) {
            errores.push("Debe haber al menos un adulto en una reserva de tipo adulto.");
        }
        if (tipoReserva.value === "infantil" && parseInt(numeroNinos.value) <= 0) {
            errores.push("Debe haber al menos un niño en una reserva de tipo infantil.");
        }
        if (tipoReserva.value === "familiar" && parseInt(numeroAdultos.value) <= 0 && parseInt(numeroNinos.value) <= 0) {
            errores.push("Debe haber al menos un adulto o un niño en una reserva de tipo familiar.");
        }

        // Validar fecha y hora
        const fechaHora = document.getElementById("fechaHora").value;
        if (!fechaHora) {
            errores.push("Debes seleccionar una fecha y hora válida.");
        } else {
            const fechaSeleccionada = new Date(fechaHora);
            const fechaActual = new Date();
            if (fechaSeleccionada < fechaActual) {
                errores.push("La fecha y hora seleccionadas no pueden ser anteriores a la actual.");
            }
        }

        // Validar duración
        const duracion = document.getElementById("duracion").value;
        if (!["60", "90", "120"].includes(duracion)) {
            errores.push("La duración seleccionada no es válida.");
        }

        // Validar selección de pista
        const idPista = document.getElementById("idPista").value;
        if (!idPista) {
            errores.push("Debes seleccionar una pista válida.");
        }

        // Mostrar errores si existen
        if (errores.length > 0) {
            event.preventDefault();
            alert("Errores encontrados:\n" + errores.join("\n"));
        }
    });
});
