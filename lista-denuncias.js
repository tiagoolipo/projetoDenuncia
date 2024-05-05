$(document).ready(function() {
    var dadosDoBancoDeDados = [
        { protocolo: "123974", status: "Pendente", denuncia: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec blandit est. Cras semper, eros consequat semper imperdiet, mauris est porttitor dui, sed egestas libero augue in mi. Integer fermentum fermentum mauris.", dataDenuncia: "10/10/2010", dataOcorrencia: "09/10/2010"},
        { protocolo: "128734", status: "Respondida", denuncia: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec blandit est. Cras semper, eros consequat semper imperdiet, mauris est porttitor dui, sed egestas libero augue in mi. Integer fermentum fermentum mauris.", dataDenuncia: "11/10/2010", dataOcorrencia: "11/10/2010", resposta: "teste" }
    ];

    function criarLinhasTabela(dados) {
        var linhas = "";
        dados.forEach(function(item) {
            linhas += `<li class="table-row">
                            <div class="col col-1" data-label="Nº de Protocolo">${item.protocolo}</div>
                            <div class="col col-2" data-label="Status">${item.status}</div>
                            <div class="col col-3" data-label="Denúncia">${item.denuncia}</div>
                            <div class="col col-4" data-label="Data da Denúncia">${item.dataDenuncia}</div>
                            <div class="col col-5" data-label="Data do Ocorrido">${item.dataOcorrencia}</div>
                            <div class="col col-6" data-label="Editar">
                                ${(item.status === "Respondida") ? `<textarea class="resposta-textarea" readonly>${item.resposta}</textarea>` : `<button class="botao-visualizacao"></button>
                                    <div class="responder-form" style="display: none;">
                                        <textarea class="resposta-textarea" placeholder="Digite sua resposta"></textarea>
                                        <button class="btn-enviar-resposta">Enviar</button>
                                    </div>`}
                            </div>
                        </li>`;
        });
        return linhas;
    }

    $('#table-container').append(criarLinhasTabela(dadosDoBancoDeDados));

    //botão de resposta
    $('.botao-visualizacao').click(function() {
        // Oculta botão
        $(this).hide();
        // abre o texto e o botão de enviar
        $(this).siblings('.responder-form').toggle();
    });

    //botão de enviar resposta
    $('.enviar-resposta').click(function() {
        // enviar resposta ao bd
        var resposta = $(this).siblings('.resposta-textarea').val();
        
        console.log("Edição Realizada com Sucesso");
    });
});