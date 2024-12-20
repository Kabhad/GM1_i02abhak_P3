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
