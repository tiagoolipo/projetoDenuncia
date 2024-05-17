const itemsPerPage = 5; // Número de itens por página
let currentPage = 1;

function displayPage(page) {
    currentPage = page;
    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const pageItems = denuncias.slice(start, end);

    const tableContainer = document.getElementById("table-container");
    tableContainer.innerHTML = `
        <li class="table-header">
            <div class="col col-1">Nome</div>
            <div class="col col-2">Nº de Protocolo</div>
            <div class="col col-3">Denúncia</div>
            <div class="col col-4">Data da Denúncia</div>
            <div class="col col-5">Data da Ocorrência</div>
            <div class="col col-6"></div>
        </li>`;

    pageItems.forEach(item => {
        tableContainer.innerHTML += `
            <li class="table-row">
                <div class="col col-1">${item.nome}</div>
                <div class="col col-2">${item.protocolo}</div>
                <div class="col col-3">${item.denuncia}</div>
                <div class="col col-4">${item.dataDenuncia}</div>
                <div class="col col-5">${item.dataOcorrencia}</div>
                <div class="col col-6"></div>
            </li>`;
    });

    updatePagination();
}

function updatePagination() {
    const totalPages = Math.ceil(denuncias.length / itemsPerPage);
    const paginationContainer = document.querySelector(".pagination");
    paginationContainer.innerHTML = `
        <li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" tabindex="-1" onclick="displayPage(${currentPage - 1})">Anterior</a>
        </li>`;

    for (let i = 1; i <= totalPages; i++) {
        paginationContainer.innerHTML += `
            <li class="page-item ${currentPage === i ? 'active' : ''}" onclick="displayPage(${i})">
                <a class="page-link" href="#">${i}</a>
            </li>`;
    }

    paginationContainer.innerHTML += `
        <li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="displayPage(${currentPage + 1})">Próximo</a>
        </li>`;
}

document.addEventListener('DOMContentLoaded', (event) => {
    if (!localStorage.getItem('sessionToken')) {
        window.location.href = 'login.html';
    } else {
        displayPage(1);
    }
});

function logoff() {
    localStorage.removeItem('sessionToken');
    window.location.href = 'login.html';
}
