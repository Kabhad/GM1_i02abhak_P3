/**
 * Validar que se seleccione una pista y confirmar la acción.
 * @param {HTMLFormElement} form - El formulario actual.
 * @param {number} materialId - El ID del material que se va a asociar.
 * @returns {boolean} True si la validación pasa, False en caso contrario.
 */
function validarAsociacion(form, materialId) {
    const pistaSeleccionada = form.nombrePista.value;

    if (!pistaSeleccionada) {
        alert(`Por favor, selecciona una pista para asociar el material con ID ${materialId}.`);
        return false;
    }

    return confirm(`¿Estás seguro de que deseas asociar el material con ID ${materialId} a la pista ${pistaSeleccionada}?`);
}

/**
 * Deshabilitar el botón de envío para evitar múltiples solicitudes.
 * @param {HTMLButtonElement} button - El botón que se va a deshabilitar.
 */
function deshabilitarBoton(button) {
    button.disabled = true;
}
