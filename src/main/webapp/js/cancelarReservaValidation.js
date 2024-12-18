document.addEventListener("DOMContentLoaded", function () {
    const btnCancelarReserva = document.querySelectorAll('.btn-cancelar-reserva');
    const modal = document.getElementById('modalConfirmacion');
    const btnConfirmar = document.getElementById('btn-confirmar');
    const btnCancelar = document.getElementById('btn-cancelar');

    // Mostrar el modal al hacer clic en el botón cancelar
    btnCancelarReserva.forEach(function (button) {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            modal.style.display = "flex";

            const form = button.closest("form"); // Selecciona el formulario asociado al botón

            // Confirmar y enviar el formulario
            btnConfirmar.onclick = function () {
                form.submit();
            };

            // Cancelar y cerrar el modal
            btnCancelar.onclick = function () {
                modal.style.display = "none";
            };
        });
    });

    // Cierra el modal si se hace clic fuera del contenido
    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
});
