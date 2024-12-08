function validateLogin() {
    const correo = document.getElementById("correo").value.trim();
    const contrasena = document.getElementById("contrasena").value.trim();

    if (correo === "" || contrasena === "") {
        alert("Por favor, completa todos los campos.");
        return false;
    }
    if (!/\S+@\S+\.\S+/.test(correo)) {
        alert("Por favor, introduce un correo válido.");
        return false;
    }
    if (/['"<>]/.test(correo) || /['"<>]/.test(contrasena)) {
        alert("Los campos no deben contener caracteres como comillas o signos de mayor/menor.");
        return false;
    }
    return true;
}
