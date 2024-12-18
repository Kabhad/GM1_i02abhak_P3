document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    form.addEventListener("submit", function (event) {
        // Obtener valores
        const idMaterial = document.getElementById("idMaterial").value;
        const tipo = document.getElementById("tipo").value;
        const usoExterior = document.getElementById("usoExterior").value;
        const estado = document.getElementById("estado").value;

        let errores = [];

        // Validar ID del Material
        if (isNaN(idMaterial) || idMaterial <= 0) {
            errores.push("El ID del material debe ser un número mayor que 0.");
        }

        // Validar Tipo de Material
        if (!["PELOTAS", "CANASTAS", "CONOS"].includes(tipo)) {
            errores.push("Selecciona un tipo de material válido.");
        }

        // Validar Uso Exterior
        if (usoExterior !== "true" && usoExterior !== "false") {
            errores.push("Selecciona una opción válida para el uso exterior.");
        }

        // Validar Estado
        if (!["DISPONIBLE", "RESERVADO", "MAL_ESTADO"].includes(estado)) {
            errores.push("Selecciona un estado válido para el material.");
        }

        // Mostrar errores
        if (errores.length > 0) {
            alert("Por favor, corrige los siguientes errores:\n\n" + errores.join("\n"));
            event.preventDefault(); // Evitar envío del formulario
        }
    });
});
