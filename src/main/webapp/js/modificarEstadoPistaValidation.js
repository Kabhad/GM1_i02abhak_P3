/**
 * Confirma el cambio de estado de la pista antes de enviar el formulario.
 * @param {boolean} estadoActual - Estado actual de la pista (true para disponible, false para no disponible).
 * @returns {boolean} - True si el usuario confirma el cambio, false en caso contrario.
 */
function confirmarCambioEstadoPista(estadoActual) {
    // Convertir estado actual a formato legible
    const estadoActualTexto = estadoActual ? "Disponible" : "No Disponible";
    const selectElement = event.target.querySelector("select[name='nuevoEstado']");
    const nuevoEstado = selectElement.value === "true"; // Convertir string a boolean
    const nuevoEstadoTexto = nuevoEstado ? "Disponible" : "No Disponible";

    if (estadoActual === nuevoEstado) {
        alert("El estado seleccionado es igual al actual. No se realizaron cambios.");
        return false;
    }
    return confirm(`¿Estás seguro de cambiar el estado de '${estadoActualTexto}' a '${nuevoEstadoTexto}'?`);
}
