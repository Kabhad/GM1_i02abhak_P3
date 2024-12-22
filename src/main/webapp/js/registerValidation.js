document.addEventListener("DOMContentLoaded", function () {
    function validateRegister() {

		// Validar el elemento 'nombre'
		const nombreElement = document.getElementById("nombre");
		if (!nombreElement) {
		    console.error("El elemento con ID 'nombre' no existe.");
		    alert("Error interno: No se encuentra el campo 'nombre'.");
		    return false;
		}
		const nombre = nombreElement.value.trim();

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

		// Validar el elemento 'fechaNacimiento'
		const fechaNacimientoElement = document.getElementById("fechaNacimiento");
		if (!fechaNacimientoElement) {
		    console.error("El elemento con ID 'fechaNacimiento' no existe.");
		    alert("Error interno: No se encuentra el campo 'fecha de nacimiento'.");
		    return false;
		}
		const fechaNacimiento = fechaNacimientoElement.value;

        if (nombre === "" || correo === "" || contrasena === "" || fechaNacimiento === "") {
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

        const fechaActual = new Date();
        const fechaNacimientoDate = new Date(fechaNacimiento);

        if (fechaNacimientoDate >= fechaActual) {
            alert("La fecha de nacimiento no puede ser en el futuro.");
            return false;
        }

        let edad = fechaActual.getFullYear() - fechaNacimientoDate.getFullYear();
        const mesActual = fechaActual.getMonth();
        const diaActual = fechaActual.getDate();
        const mesNacimiento = fechaNacimientoDate.getMonth();
        const diaNacimiento = fechaNacimientoDate.getDate();

        if (mesNacimiento > mesActual || (mesNacimiento === mesActual && diaNacimiento > diaActual)) {
            edad--;
        }

        if (edad < 18) {
            alert("Debes tener al menos 18 años para registrarte.");
            return false;
        }

        return true;
    }

    document.getElementById("registerForm").onsubmit = validateRegister;
});
