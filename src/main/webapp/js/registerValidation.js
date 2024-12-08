function validateRegister() {
    const nombre = document.getElementById("nombre").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const contrasena = document.getElementById("contrasena").value.trim();
    const tipoUsuario = document.getElementById("tipoUsuario").value;
    const fechaNacimiento = document.getElementById("fechaNacimiento").value;
    const fechaActual = new Date();
    const fechaNacimientoDate = new Date(fechaNacimiento);

    if (nombre === "" || correo === "" || contrasena === "" || tipoUsuario === "" || fechaNacimiento === "") {
        alert("Por favor, completa todos los campos.");
        return false;
    }
    if (!/^[a-zA-Z\s]+$/.test(nombre)) {
        alert("El nombre solo puede contener letras.");
        return false;
    }
    if (!/\S+@\S+\.\S+/.test(correo)) {
        alert("Por favor, introduce un correo válido.");
        return false;
    }
    if (contrasena.length < 6 || !/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/.test(contrasena)) {
        alert("La contraseña debe contener al menos 6 caracteres, incluyendo una letra mayúscula, una minúscula, un número y un carácter especial.");
        return false;
    }
    if (fechaNacimientoDate >= fechaActual) {
        alert("La fecha de nacimiento no puede ser en el futuro.");
        return false;
    }
    const edad = fechaActual.getFullYear() - fechaNacimientoDate.getFullYear();
    if (fechaNacimientoDate.getMonth() > fechaActual.getMonth() ||
        (fechaNacimientoDate.getMonth() === fechaActual.getMonth() && fechaNacimientoDate.getDate() > fechaActual.getDate())) {
        edad--;
    }
    if (edad < 18) {
        alert("Debes tener al menos 18 años para registrarte.");
        return false;
    }
    return true;
}
