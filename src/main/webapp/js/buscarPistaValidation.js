document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("buscarPistasForm");

    form.addEventListener("submit", function (event) {
        // Obtener valores de los campos
        const tamano = document.getElementById("tamano").value;
        const exterior = document.getElementById("exterior").value;

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

        // Mostrar errores si los hay
        if (errors.length > 0) {
            alert("Errores en el formulario:\n" + errors.join("\n"));
            event.preventDefault(); // Evitar el envío del formulario
        }
    });
});
