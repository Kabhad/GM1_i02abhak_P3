document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    form.addEventListener("submit", function (event) {
        const idPista = document.getElementById("idPista").value;
        const nombrePista = document.getElementById("nombrePista").value;
        const disponible = document.getElementById("disponible").value;
        const exterior = document.getElementById("exterior").value;
        const tamanoPista = document.getElementById("tamanoPista").value;
        const maxJugadores = document.getElementById("maxJugadores").value;

        let errores = [];

        if (isNaN(idPista) || idPista <= 0) {
            errores.push("El ID de la pista debe ser un número mayor que 0.");
        }

        if (nombrePista.trim() === "") {
            errores.push("El nombre de la pista no puede estar vacío.");
        }

        if (!["true", "false"].includes(disponible)) {
            errores.push("Selecciona una opción válida para disponibilidad.");
        }

        if (!["true", "false"].includes(exterior)) {
            errores.push("Selecciona una opción válida para el tipo de pista.");
        }

        // Validación corregida para tamanoPista
        if (!["MINIBASKET", "_3VS3", "ADULTOS"].includes(tamanoPista)) {
            errores.push("Selecciona un tamaño válido para la pista.");
        }

        if (isNaN(maxJugadores) || maxJugadores <= 0) {
            errores.push("El número máximo de jugadores debe ser un número mayor que 0.");
        }

        if (errores.length > 0) {
            alert("Por favor, corrige los siguientes errores:\n\n" + errores.join("\n"));
            event.preventDefault();
        }
    });
});
