/**
 * Función para validar el formulario de modificación de datos del usuario.
 *
 * Reglas de validación:
 * 1. Todos los campos deben estar completos.
 *    - Si algún campo está vacío, muestra un mensaje de alerta y retorna `false`.
 * 2. Validación del campo "nombre":
 *    - Solo se permiten letras (incluyendo acentos y diéresis), la ñ y espacios.
 *    - Si no cumple, muestra un mensaje de alerta y retorna `false`.
 * 3. Validación del campo "contraseña":
 *    - Debe tener al menos 6 caracteres.
 *    - Debe incluir al menos una letra mayúscula, una minúscula, un número y un carácter especial.
 *    - Si no cumple, muestra un mensaje de alerta y retorna `false`.
 *
 * @returns {boolean} `true` si todas las validaciones se pasan, `false` de lo contrario.
 */
function validateModifyForm() {
    const nombre = document.getElementById("nombre").value.trim();
    const contrasena = document.getElementById("contrasena").value.trim();

    if (nombre === "" || contrasena === "") {
        alert("Por favor, completa todos los campos.");
        return false;
    }

    // Aceptar caracteres válidos, incluyendo letras con tildes, diéresis, la ñ y espacios
    if (!/^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\s]+$/.test(nombre)) {
        alert("El nombre solo puede contener letras, espacios y caracteres válidos en español.");
        return false;
    }

    // Validación de contraseña
    if (contrasena.length < 6 || 
        !/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/.test(contrasena)) {
        alert("La contraseña debe contener al menos 6 caracteres, incluyendo una letra mayúscula, una minúscula, un número y un carácter especial.");
        return false;
    }

    return true;
}
