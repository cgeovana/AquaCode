// Avistamentos JavaScript

// Obter localização atual do usuário
function obterLocalizacao() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                document.getElementById('latitude').value = position.coords.latitude.toFixed(6);
                document.getElementById('longitude').value = position.coords.longitude.toFixed(6);
                alert('Localização obtida com sucesso!');
            },
            (error) => {
                alert('Erro ao obter localização: ' + error.message);
            }
        );
    } else {
        alert('Geolocalização não é suportada pelo seu navegador.');
    }
}

// Enviar formulário de avistamento
document.getElementById('formAvistamento')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = {
        nomeEspecie: document.getElementById('nomeEspecie').value,
        nomeCientifico: document.getElementById('nomeCientifico').value,
        dataHoraAvistamento: document.getElementById('dataHoraAvistamento').value,
        latitude: parseFloat(document.getElementById('latitude').value),
        longitude: parseFloat(document.getElementById('longitude').value),
        localDescricao: document.getElementById('localDescricao').value,
        observacoes: document.getElementById('observacoes').value,
        fotoUrl: document.getElementById('fotoUrl').value
    };

    try {
        const response = await fetch('/api/avistamentos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            alert('Avistamento registrado com sucesso! Aguarde aprovação do moderador.');
            window.location.href = '/avistamentos/listar';
        } else {
            alert('Erro ao registrar avistamento.');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao comunicar com o servidor.');
    }
});

// Carregar lista de avistamentos
async function carregarAvistamentos() {
    try {
        const response = await fetch('/api/avistamentos/meus');
        const avistamentos = await response.json();

        const container = document.getElementById('listaAvistamentos');
        if (!container) return;

        if (avistamentos.length === 0) {
            container.innerHTML = '<p class="sem-dados">Você ainda não registrou nenhum avistamento.</p>';
            return;
        }

        container.innerHTML = avistamentos.map(a => `
            <div class="avistamento-card ${a.status.toLowerCase()}">
                <div class="avistamento-header">
                    <h3>${a.nomeEspecie}</h3>
                    <span class="status-badge ${a.status.toLowerCase()}">${a.status}</span>
                </div>
                ${a.nomeCientifico ? `<p class="cientifico"><em>${a.nomeCientifico}</em></p>` : ''}
                <p class="data">📅 ${new Date(a.dataHoraAvistamento).toLocaleString('pt-BR')}</p>
                <p class="local">📍 ${a.localDescricao || `Lat: ${a.latitude}, Long: ${a.longitude}`}</p>
                ${a.observacoes ? `<p class="observacoes">${a.observacoes}</p>` : ''}
                ${a.fotoUrl ? `<img src="${a.fotoUrl}" alt="${a.nomeEspecie}" class="foto-avistamento">` : ''}
                ${a.comentarioModeracao ? `<div class="comentario-mod">💬 Moderador: ${a.comentarioModeracao}</div>` : ''}
                <div class="avistamento-actions">
                    <button onclick="deletarAvistamento(${a.id})" class="btn-danger">Excluir</button>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Erro ao carregar avistamentos:', error);
    }
}

// Deletar avistamento
async function deletarAvistamento(id) {
    if (!confirm('Deseja realmente excluir este avistamento?')) return;

    try {
        const response = await fetch(`/api/avistamentos/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Avistamento excluído com sucesso!');
            carregarAvistamentos();
        } else {
            alert('Erro ao excluir avistamento.');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao comunicar com o servidor.');
    }
}

// Inicializar
if (document.getElementById('listaAvistamentos')) {
    carregarAvistamentos();
}
