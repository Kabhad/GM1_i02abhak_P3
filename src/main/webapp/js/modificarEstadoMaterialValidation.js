/**
 * Confirma el cambio de estado del material antes de enviar el formulario.
 * @param {string} estadoActual - Estado actual del material.
 * @returns {boolean} - True si el usuario confirma el cambio, false en caso contrario.
 */
function confirmarCambioEstado(estadoActual) {
    const nuevoEstado = event.target.querySelector("select[name='nuevoEstado']").value;
    if (estadoActual === nuevoEstado) {
        alert("El estado seleccionado es igual al actual. No se realizaron cambios.");
        return false;
    }
    return confirm(`¿Estás seguro de cambiar el estado de '${estadoActual}' a '${nuevoEstado}'?`);
}
