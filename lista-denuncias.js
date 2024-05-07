$(document).ready(function() {
    function getSessionToken() {
        return localStorage.getItem('sessionToken');
    }
    var sessionToken = getSessionToken();
    if (sessionToken) {
        console.log('Token de sessão recuperado:', sessionToken);
        $.ajax({
            url: 'http://localhost:8081/api/v1/reports',
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
                linhas += `<li class="table-row">
                        <div class="col col-1" data-label="Nº de Protocolo">${item.protocolNumber}</div>
                        <div class="col col-2" data-label="Função">${item.office}</div>
                        <div class="col col-3" data-label="Denúncia">${item.description}</div>
                        <div class="col col-4" data-label="Data da Denúncia">${item.createdAt}</div>
                        <div class="col col-5" data-label="Data da Ocorrência">${item.dateOfOccurrence}</div>
                        <div class="col col-6" data-label="Editar">
                            ${(item.status === "Respondido") ? `<textarea class="resposta-textarea" readonly>${item.response}</textarea>` : `<button class="botao-visualizacao">Visualizar</button>
                                <div class="responder-form" style="display: none;">
                                    <textarea class="resposta-textarea" placeholder="Digite sua resposta"></textarea>
                                    <button class="btn-enviar-resposta">Enviar</button>
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
        var resposta = $(this).siblings('.resposta-textarea').val();
        var protocolNumber = $(this).closest('.table-row').find('.col-1').text();
        console.log("Resposta enviada ao banco de dados para a denúncia " + protocolNumber + ": ", resposta);
    });
});
