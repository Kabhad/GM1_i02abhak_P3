document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("buscarPistasForm");

    form.addEventListener("submit", function (event) {
        // Obtener valores de los campos
        const tamano = document.getElementById("tamano").value;
        const exterior = document.getElementById("exterior").value;
        const fechaHora = document.getElementById("fechaHora").value;

        // Validar los valores
        let errors = [];

        // Validar tamaño (opcional, ya que puede estar vacío)
        if (tamano && !["MINIBASKET", "ADULTOS", "_3VS3"].includes(tamano)) {
            errors.push("El tamaño seleccionado no es válido.");
        }

        // Validar exterior (opcional, ya que puede estar vacío)
        if (exterior && !["true", "false"].includes(exterior)) {
            errors.push("La opción de exterior no es válida.");
        }

        // Validar fecha y hora
        if (fechaHora) {
            const fechaHoraSeleccionada = new Date(fechaHora); // Fecha y hora seleccionada
            const fechaHoraActual = new Date(); // Fecha y hora actual

            if (fechaHoraSeleccionada < fechaHoraActual) {
                errors.push("La fecha y hora no pueden ser anteriores a la fecha y hora actual.");
            }

            // Validar que la hora esté entre 9:00 y 21:00
            const horaSeleccionada = fechaHoraSeleccionada.getHours();
            const minutoSeleccionado = fechaHoraSeleccionada.getMinutes();

            if (horaSeleccionada < 9 || (horaSeleccionada > 21 || (horaSeleccionada === 21 && minutoSeleccionado > 0))) {
                errors.push("La hora debe estar entre las 9:00 y las 21:00.");
            }
        } else {
            errors.push("La fecha y hora son obligatorias.");
        }

        // Mostrar errores si los hay
        if (errors.length > 0) {
            alert("Errores en el formulario:\n" + errors.join("\n"));
            event.preventDefault(); // Evitar el envío del formulario
        }
    });
});
