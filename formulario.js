$(document).ready(function() {
    $('#formulario').submit(function(e) {
        e.preventDefault();

        
        var formData = new FormData(this);

        $.ajax({
            url: '#',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                alert('Dados enviados com sucesso!');
            },
            error: function(xhr, status, error) {
                console.error(error);
                alert('Erro ao enviar os dados, tente novamente.');
            }
        });
    });

    
    $('#anonimoCheckbox').change(function() {
        if ($(this).is(':checked')) {
            $('#nome, #funcao').prop('disabled', true);
        } else {
            $('#nome, #funcao').prop('disabled', false);
        }
    });
});