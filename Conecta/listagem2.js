$(document).ready(function() {
    var dadosDoBancoDeDados = [
        { nome: "Teste", funcao: "Auxiliar", denuncia: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec blandit est. Cras semper, eros consequat semper imperdiet, mauris est porttitor dui, sed egestas libero augue in mi. Integer fermentum fermentum mauris.", dataDenuncia: "10/10/2010", dataOcorrencia: "09/10/2010", status: "Pendente" },
        { nome: "Teste 2", funcao: "Secretario", denuncia: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec blandit est. Cras semper, eros consequat semper imperdiet, mauris est porttitor dui, sed egestas libero augue in mi. Integer fermentum fermentum mauris.", dataDenuncia: "11/10/2010", dataOcorrencia: "11/10/2010", status: "Pendente" },
        { nome: "Test 3", funcao: "Lojista", denuncia: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec blandit est. Cras semper, eros consequat semper imperdiet, mauris est porttitor dui, sed egestas libero augue in mi. Integer fermentum fermentum mauris.", dataDenuncia: "11/10/2010", dataOcorrencia: "11/10/2010", status: "Respondida" }
    ];

    function criarLinhasTabela(dados) {
        var linhas = "";
        dados.forEach(function(item) {
            if (item.status !== "Respondida") {
                linhas += `<li class="table-row">
                                <div class="col col-1" data-label="Nome">${item.nome}</div>
                                <div class="col col-2" data-label="Função">${item.funcao}</div>
                                <div class="col col-3" data-label="Denúncia">${item.denuncia}</div>
                                <div class="col col-4" data-label="Data da Denúncia">${item.dataDenuncia}</div>
                                <div class="col col-5" data-label="Data do Ocorrido">${item.dataOcorrencia}</div>
                                <div class="col col-6" data-label="Responder">
                                    <button class="botao-visualizacao"></button>
                                    <div class="responder-form" style="display: none;">
                                        <textarea class="resposta-textarea" placeholder="Digite sua resposta"></textarea>
                                        <button class="btn-enviar-resposta">Enviar</button>
                                    </div>
                                </div>
                            </li>`;
            }
        });
        return linhas;
    }

    $('#table-container').append(criarLinhasTabela(dadosDoBancoDeDados));

    // Botão de resposta
    $('.botao-visualizacao').click(function() {
        
        $(this).hide();
        
        $(this).siblings('.responder-form').toggle();
    });

    // Botão de enviar resposta
    $('.enviar-resposta').click(function() {
        
        var resposta = $(this).siblings('.resposta-textarea').val();
        
        console.log("Resposta enviada: " + resposta);
    });
});