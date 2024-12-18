document.addEventListener("DOMContentLoaded", function () {
    const btnEliminar = document.querySelectorAll('.btn-eliminar');
    const modal = document.getElementById('modalConfirmacion');
    const btnConfirmar = document.getElementById('btn-confirmar');
    const btnCancelar = document.getElementById('btn-cancelar');

    // Mostrar modal de confirmación al hacer clic en el botón eliminar
    btnEliminar.forEach(function(button) {
        button.addEventListener("click", function (event) {
            event.preventDefault(); // Evita que se envíe el formulario directamente
            modal.style.display = "flex"; // Muestra el modal
            const form = button.closest("form"); // Obtiene el formulario relacionado con el botón
            const idReserva = form.querySelector('input[name="idReserva"]').value; // Obtiene el id de la reserva
            // Al confirmar, envía el formulario
            btnConfirmar.onclick = function() {
                form.submit(); // Envía el formulario para eliminar la reserva
            };
            // Al cancelar, cierra el modal
            btnCancelar.onclick = function() {
                modal.style.display = "none"; // Cierra el modal
            };
        });
    });

    // Cerrar el modal si se hace clic fuera de la ventana del modal
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none"; // Cierra el modal si se hace clic fuera de él
        }
    };
});
