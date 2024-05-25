$(document).ready(function() {
    function getSessionToken() {
        return localStorage.getItem('sessionToken');
    }
    var sessionToken = getSessionToken();
    if (sessionToken) {
        console.log('Token de sessão recuperado:', sessionToken);
        $.ajax({
            url: 'http://localhost:8081/api/v1/reports/all',
            type: 'GET',
            headers: {
                'Authorization': sessionToken
            },
            success: function(data) {
                console.log('Resposta da requisição:', data);
                exibirDenuncias(data);
            },
            error: function(err) {
                console.error('Erro na requisição:', err);
            }
        });
    } else {
        console.log('Token de sessão não encontrado.');
    }
    function criarLinhasTabela(dados) {
        var linhas = "";

        if (Array.isArray(dados)) {
            dados.forEach(function(item) {
                var userFirstName = item.user ? item.user.firstName : '';
                linhas += `<li class="table-row">
                    <div class="col col-1" data-label="Nome">${userFirstName}</div>
                    <div class="col col-2" data-label="Nº de Protocolo">${item.protocolNumber}</div>
                    <div class="col col-3" data-label="Denúncia">${item.description}</div>
                    <div class="col col-4" data-label="Data da Denúncia">${item.createdAt}</div>
                    <div class="col col-5" data-label="Data da Ocorrência">${item.dateOfOccurrence}</div>
                    <div class="col col-6" data-label="Editar">
                        ${(item.status === "Respondido") ? `<textarea class="resposta-textarea" readonly>${item.response}</textarea>` : `<button class="botao-visualizacao">Visualizar</button>
                            <div class="responder-form" style="display: none;">
                                <textarea id="response-${item.protocolNumber}" class="resposta-textarea" placeholder="Digite sua resposta"></textarea>
                                <button type="button" data-protocol="${item.protocolNumber}" data-report-id="${item.reportId}" class="btn-enviar-resposta">Enviar</button>
                            </div>`}
                    </div>
                </li>`;
            });
        } else {
            console.error('Os dados recebidos não são um array:', dados);
        }

        return linhas;
    }
    function exibirDenuncias(dados) {
        if (dados && dados.content && Array.isArray(dados.content)) {
            var linhas = criarLinhasTabela(dados.content);
            $('#table-container').append(linhas);
        } else {
            console.error('Dados inválidos recebidos:', dados);
        }
    }

    $('#table-container').on('click', '.botao-visualizacao', function() {
        $(this).hide();
        $(this).siblings('.responder-form').toggle();
    });

    $('#table-container').on('click', '.btn-enviar-resposta', function() {
        var protocolNumber = $(this).data('protocol');
        var reportId = $(this).data('report-id');
        var resposta = $('#response-' + protocolNumber).val();
        console.log("Resposta enviada ao banco de dados para a denúncia " + protocolNumber + ": ", resposta);

        enviarResposta(protocolNumber, resposta, reportId);
    });
    function enviarResposta(protocolNumber, resposta, reportId) {
        var userData = {
            protocolNumber: protocolNumber,
            reportId: reportId,
            response: resposta
        };
        $.ajax({
            url: 'http://localhost:8081/api/v1/reports/' + reportId,
            type: 'PUT',
            headers: {
                'Authorization': sessionToken,
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(userData),
            success: function(response) {
                console.log('Resposta enviada com sucesso:', response);
                alert('Resposta enviada com sucesso!');
            },
            error: function(err) {
                console.error('Erro ao enviar resposta:', err);
                if (err.responseJSON && err.responseJSON.errors && err.responseJSON.errors.length > 0) {
                    var errorMessage = err.responseJSON.errors[0].message;
                    alert('Erro ao enviar resposta: ' + errorMessage);
                } else {
                    alert('Erro ao enviar resposta. Por favor, tente novamente.');
                }
            }
        });
    }
});