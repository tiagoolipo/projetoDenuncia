$(document).ready(function() {
    function getSessionToken() {
        return localStorage.getItem('sessionToken');
    }
    $('#formulario').submit(function(event) {
        event.preventDefault();
        var sessionToken = getSessionToken();

        if (!sessionToken) {
            console.log('Token de sessão não encontrado.');
            return;
        }
        var dateOfOccurrence = $('#dateOfOccurrence').val();
        var description = $('#description').val();
        var anonimo = $('#anonimoCheckbox').prop('checked');

        if (!description) {
            alert('Por favor, preencha a descrição da ocorrência.');
            return;
        }
        var dados = {
            dateOfOccurrence: dateOfOccurrence,
            description: description,
        };

        $.ajax({
            url: 'http://localhost:8081/api/v1/reports',
            type: 'POST',
            headers: {
                'Authorization': sessionToken,
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(dados),
            success: function(response) {
                console.log('Informações enviadas com sucesso:', response);
                alert('Informações enviadas com sucesso!');
                window.location.href = 'minhas-denuncias.html';
            },
            error: function(err) {
                console.error('Erro ao enviar informações:', err);
                alert('Erro ao enviar informações. Por favor, tente novamente.');
            }
        });
    });
});