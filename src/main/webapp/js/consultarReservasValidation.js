function validarFechas() {
    const fechaInicio = document.getElementById("fechaInicio").value;
    const fechaFin = document.getElementById("fechaFin").value;

    if (fechaInicio === "" || fechaFin === "") {
        alert("Por favor, completa ambos campos de fecha.");
        return false;
    }

    if (new Date(fechaInicio) > new Date(fechaFin)) {
        alert("La fecha de inicio no puede ser posterior a la fecha de fin.");
        return false;
    }

    return true;
}
