function validateLogin() {
	// Validar el elemento 'correo'
	const correoElement = document.getElementById("correo");
	if (!correoElement) {
	    console.error("El elemento con ID 'correo' no existe.");
	    alert("Error interno: No se encuentra el campo 'correo'.");
	    return false;
	}
	const correo = correoElement.value.trim();

	// Validar el elemento 'contrasena'
	const contrasenaElement = document.getElementById("contrasena");
	if (!contrasenaElement) {
	    console.error("El elemento con ID 'contrasena' no existe.");
	    alert("Error interno: No se encuentra el campo 'contraseña'.");
	    return false;
	}
	const contrasena = contrasenaElement.value.trim();

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
