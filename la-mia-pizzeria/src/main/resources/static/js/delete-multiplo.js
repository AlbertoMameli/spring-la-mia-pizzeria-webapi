document.addEventListener('DOMContentLoaded', function () {
    const selectAllCheckbox = document.getElementById('select-all');
    const itemCheckboxes = document.querySelectorAll('input[name="ids"]');
    const bulkDeleteButton = document.getElementById('bulk-delete-btn');

    // Funzione per aggiornare lo stato del pulsante bulk delete
    function updateBulkDeleteButton() {
        // Se almeno una checkbox è selezionata, abilita il pulsante
        const anyChecked = Array.from(itemCheckboxes).some(cb => cb.checked);
        bulkDeleteButton.disabled = !anyChecked;
    }

    // Quando cambia la checkbox "select all"
    selectAllCheckbox.addEventListener('change', function () {
        itemCheckboxes.forEach(cb => cb.checked = selectAllCheckbox.checked);
        updateBulkDeleteButton();
    });

    // Quando cambia una qualsiasi checkbox singola
    itemCheckboxes.forEach(cb => {
        cb.addEventListener('change', function () {
            // Se tutte le checkbox singole sono selezionate, seleziona anche "select all"
            const allChecked = Array.from(itemCheckboxes).every(cb => cb.checked);
            selectAllCheckbox.checked = allChecked;

            updateBulkDeleteButton();
        });
    });

    // Imposta lo stato iniziale del pulsante (disabilitato se nessuno selezionato)
    updateBulkDeleteButton();

    bulkDeleteForm.addEventListener('submit', function (event) {
        const anyChecked = Array.from(itemCheckboxes).some(cb => cb.checked);
        if (!anyChecked) {
            event.preventDefault();
            alert('Seleziona almeno un ingrediente prima di eliminare!');
        }
    });
});

