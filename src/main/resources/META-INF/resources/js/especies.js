// Espécies JavaScript

let todasEspecies = [];

// Carregar todas as espécies
async function carregarEspecies() {
    try {
        const response = await fetch('/api/especies');
        todasEspecies = await response.json();
        exibirEspecies(todasEspecies);
    } catch (error) {
        console.error('Erro ao carregar espécies:', error);
    }
}

// Exibir espécies na grade
function exibirEspecies(especies) {
    const container = document.getElementById('listaEspecies');
    if (!container) return;

    if (especies.length === 0) {
        container.innerHTML = '<p class="sem-dados">Nenhuma espécie encontrada.</p>';
        return;
    }

    container.innerHTML = especies.map(e => `
        <div class="especie-card" onclick="abrirDetalheEspecie(${e.id})">
            ${e.imagemUrl ? `<img src="${e.imagemUrl}" alt="${e.nomeComum}" class="especie-imagem">` : '<div class="sem-imagem">🐠</div>'}
            <div class="especie-info">
                <h3>${e.nomeComum}</h3>
                <p class="cientifico"><em>${e.nomeCientifico}</em></p>
                <span class="categoria-badge">${e.categoria || 'N/A'}</span>
                ${e.statusConservacao ? `<span class="conservacao-badge ${e.statusConservacao.toLowerCase()}">${e.statusConservacao}</span>` : ''}
            </div>
        </div>
    `).join('');
}

// Buscar espécies
async function buscarEspecies() {
    const termo = document.getElementById('busca').value;
    
    if (termo.trim() === '') {
        exibirEspecies(todasEspecies);
        return;
    }

    try {
        const response = await fetch(`/api/especies/buscar?termo=${encodeURIComponent(termo)}`);
        const especies = await response.json();
        exibirEspecies(especies);
    } catch (error) {
        console.error('Erro ao buscar espécies:', error);
    }
}

// Filtrar por categoria
async function filtrarPorCategoria() {
    const categoria = document.getElementById('filtroCategoria').value;
    
    if (categoria === '') {
        exibirEspecies(todasEspecies);
        return;
    }

    try {
        const response = await fetch(`/api/especies/categoria/${encodeURIComponent(categoria)}`);
        const especies = await response.json();
        exibirEspecies(especies);
    } catch (error) {
        console.error('Erro ao filtrar por categoria:', error);
    }
}

// Filtrar por status de conservação
async function filtrarPorConservacao() {
    const status = document.getElementById('filtroConservacao').value;
    
    if (status === '') {
        exibirEspecies(todasEspecies);
        return;
    }

    try {
        const response = await fetch(`/api/especies/conservacao/${encodeURIComponent(status)}`);
        const especies = await response.json();
        exibirEspecies(especies);
    } catch (error) {
        console.error('Erro ao filtrar por conservação:', error);
    }
}

// Abrir detalhes da espécie
async function abrirDetalheEspecie(id) {
    try {
        const response = await fetch(`/api/especies/${id}`);
        const especie = await response.json();

        // Criar modal com detalhes
        const modal = document.createElement('div');
        modal.className = 'modal';
        modal.innerHTML = `
            <div class="modal-content">
                <span class="close" onclick="this.parentElement.parentElement.remove()">&times;</span>
                <h2>${especie.nomeComum}</h2>
                <p class="cientifico-grande"><em>${especie.nomeCientifico}</em></p>
                
                ${especie.imagemUrl ? `<img src="${especie.imagemUrl}" alt="${especie.nomeComum}" class="especie-imagem-grande">` : ''}
                
                <div class="taxonomia">
                    <h3>Classificação Taxonômica</h3>
                    <p><strong>Reino:</strong> ${especie.reino || 'N/A'}</p>
                    <p><strong>Filo:</strong> ${especie.filo || 'N/A'}</p>
                    <p><strong>Classe:</strong> ${especie.classe || 'N/A'}</p>
                    <p><strong>Ordem:</strong> ${especie.ordem || 'N/A'}</p>
                    <p><strong>Família:</strong> ${especie.familia || 'N/A'}</p>
                    <p><strong>Gênero:</strong> ${especie.genero || 'N/A'}</p>
                </div>

                <div class="info-detalhada">
                    <h3>Informações</h3>
                    <p><strong>Descrição:</strong> ${especie.descricao || 'N/A'}</p>
                    <p><strong>Habitat:</strong> ${especie.habitat || 'N/A'}</p>
                    <p><strong>Distribuição Geográfica:</strong> ${especie.distribuicaoGeografica || 'N/A'}</p>
                    <p><strong>Dieta:</strong> ${especie.dieta || 'N/A'}</p>
                    <p><strong>Status de Conservação:</strong> <span class="conservacao-badge ${especie.statusConservacao?.toLowerCase()}">${especie.statusConservacao || 'N/A'}</span></p>
                    ${especie.caracteristicasDistintivas ? `<p><strong>Características Distintivas:</strong> ${especie.caracteristicasDistintivas}</p>` : ''}
                </div>
            </div>
        `;
        document.body.appendChild(modal);
    } catch (error) {
        console.error('Erro ao carregar detalhes da espécie:', error);
    }
}

// Inicializar
if (document.getElementById('listaEspecies')) {
    carregarEspecies();
}
