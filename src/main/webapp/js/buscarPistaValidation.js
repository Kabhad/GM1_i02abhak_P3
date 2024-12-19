document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("buscarPistasForm");

    form.addEventListener("submit", function (event) {
        // Obtener valores de los campos
        const tamano = document.getElementById("tamano").value;
        const exterior = document.getElementById("exterior").value;
        const fecha = document.getElementById("fecha").value;

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

        // Validar fecha
        if (fecha) {
            const fechaSeleccionada = new Date(fecha); // Fecha seleccionada
            const fechaActual = new Date(); // Fecha actual
            fechaActual.setHours(0, 0, 0, 0); // Ignorar hora para comparar solo la fecha

            if (fechaSeleccionada < fechaActual) {
                errors.push("La fecha no puede ser anterior a la fecha actual.");
            }
        } else {
            errors.push("La fecha es obligatoria.");
        }

        // Mostrar errores si los hay
        if (errors.length > 0) {
            alert("Errores en el formulario:\n" + errors.join("\n"));
            event.preventDefault(); // Evitar el envío del formulario
        }
    });
});
