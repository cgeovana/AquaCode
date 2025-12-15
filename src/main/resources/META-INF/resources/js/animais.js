// Funções de autenticação e controle de acesso
function getToken() {
    return localStorage.getItem('token');
}

function getUserRole() {
    return localStorage.getItem('role');
}

function isAdmin() {
    return getUserRole() === 'admin';
}

function isLoggedIn() {
    return !!getToken();
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    location.href = '/';
}

function checkAuth() {
    if (!isLoggedIn()) {
        location.href = '/';
        return false;
    }
    return true;
}

// Atualizar UI baseado no role
function updateUIByRole() {
    const isUserAdmin = isAdmin();
    const username = localStorage.getItem('username') || 'Usuário';
    
    // Atualizar header
    const userInfo = document.getElementById('userInfo');
    if (userInfo) {
        userInfo.innerHTML = `
            <span class="user-badge ${isUserAdmin ? 'admin' : 'user'}">
                ${isUserAdmin ? '👑' : '👤'} ${username}
            </span>
            <button onclick="logout()" class="btn-logout">Sair</button>
        `;
    }
    
    // Mostrar/ocultar elementos admin-only
    const adminElements = document.querySelectorAll('.admin-only');
    adminElements.forEach(element => {
        element.style.display = isUserAdmin ? '' : 'none';
    });
    
    // Mostrar/ocultar avisos
    const adminNotice = document.getElementById('adminNotice');
    const userNotice = document.getElementById('userNotice');
    
    if (adminNotice && userNotice) {
        if (isUserAdmin) {
            adminNotice.style.display = 'block';
            userNotice.style.display = 'none';
        } else {
            adminNotice.style.display = 'none';
            userNotice.style.display = 'block';
        }
    }
}

// Função genérica de fetch com autenticação
async function authenticatedFetch(url, options = {}) {
    const token = getToken();
    
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    };
    
    const mergedOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...options.headers
        }
    };
    
    try {
        const response = await fetch(url, mergedOptions);
        
        if (response.status === 401) {
            alert('Sessão expirada. Faça login novamente.');
            logout();
            return null;
        }
        
        if (response.status === 403) {
            alert('Você não tem permissão para realizar esta ação.');
            return null;
        }
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }
        
        // Se for 204 No Content, retornar objeto vazio
        if (response.status === 204) {
            return {};
        }
        
        return await response.json();
    } catch (error) {
        console.error('Erro na requisição:', error);
        throw error;
    }
}

// Carregar animais
async function carregarAnimais() {
    try {
        const animais = await authenticatedFetch('/animais/api');
        
        if (!animais) return;
        
        const container = document.getElementById('animaisContainer');
        if (!container) return;
        
        if (animais.length === 0) {
            container.innerHTML = '<p class="no-data">Nenhum animal cadastrado ainda.</p>';
            return;
        }
        
        container.innerHTML = animais.map(animal => `
            <div class="animal-card">
                <div class="animal-header">
                    <h3>${animal.nome}</h3>
                    <span class="status-badge status-${animal.status?.toLowerCase().replace(' ', '-')}">${animal.status || 'Ativo'}</span>
                </div>
                <div class="animal-body">
                    <p><strong>Espécie:</strong> ${animal.especie}</p>
                    <p><strong>Idade:</strong> ${animal.idade} anos</p>
                    <p><strong>Habitat:</strong> ${animal.habitat}</p>
                    ${animal.descricao ? `<p><strong>Descrição:</strong> ${animal.descricao}</p>` : ''}
                </div>
                <div class="animal-actions">
                    <button onclick="verDetalhes(${animal.id})" class="btn-view">Ver Detalhes</button>
                    <button onclick="editarAnimal(${animal.id})" class="btn-edit admin-only">Editar</button>
                    <button onclick="deletarAnimal(${animal.id})" class="btn-delete admin-only">Deletar</button>
                </div>
            </div>
        `).join('');
        
        updateUIByRole();
    } catch (error) {
        console.error('Erro ao carregar animais:', error);
        alert('Erro ao carregar animais: ' + error.message);
    }
}

// Criar novo animal
async function criarAnimal(event) {
    event.preventDefault();
    
    if (!isAdmin()) {
        alert('Apenas administradores podem criar animais.');
        return;
    }
    
    const formData = {
        nome: document.getElementById('nome').value,
        especie: document.getElementById('especie').value,
        idade: parseInt(document.getElementById('idade').value),
        habitat: document.getElementById('habitat').value,
        descricao: document.getElementById('descricao').value,
        status: document.getElementById('status').value
    };
    
    try {
        const result = await authenticatedFetch('/animais/api', {
            method: 'POST',
            body: JSON.stringify(formData)
        });
        
        if (result) {
            alert('Animal cadastrado com sucesso!');
            document.getElementById('formAnimal').reset();
            carregarAnimais();
        }
    } catch (error) {
        alert('Erro ao cadastrar animal: ' + error.message);
    }
}

// Editar animal
async function editarAnimal(id) {
    if (!isAdmin()) {
        alert('Apenas administradores podem editar animais.');
        return;
    }
    
    const animal = await authenticatedFetch(`/animais/api/${id}`);
    if (!animal) return;
    
    // Preencher formulário com dados do animal
    document.getElementById('animalId').value = animal.id;
    document.getElementById('nome').value = animal.nome;
    document.getElementById('especie').value = animal.especie;
    document.getElementById('idade').value = animal.idade;
    document.getElementById('habitat').value = animal.habitat;
    document.getElementById('descricao').value = animal.descricao || '';
    document.getElementById('status').value = animal.status || 'ATIVO';
    
    // Mudar botão para "Atualizar"
    const submitBtn = document.querySelector('#formAnimal button[type="submit"]');
    submitBtn.textContent = 'Atualizar Animal';
    submitBtn.onclick = (e) => atualizarAnimal(e, id);
    
    // Scroll para o formulário
    document.getElementById('formAnimal').scrollIntoView({ behavior: 'smooth' });
}

// Atualizar animal
async function atualizarAnimal(event, id) {
    event.preventDefault();
    
    if (!isAdmin()) {
        alert('Apenas administradores podem atualizar animais.');
        return;
    }
    
    const formData = {
        nome: document.getElementById('nome').value,
        especie: document.getElementById('especie').value,
        idade: parseInt(document.getElementById('idade').value),
        habitat: document.getElementById('habitat').value,
        descricao: document.getElementById('descricao').value,
        status: document.getElementById('status').value
    };
    
    try {
        const result = await authenticatedFetch(`/animais/api/${id}`, {
            method: 'PUT',
            body: JSON.stringify(formData)
        });
        
        if (result) {
            alert('Animal atualizado com sucesso!');
            cancelarEdicao();
            carregarAnimais();
        }
    } catch (error) {
        alert('Erro ao atualizar animal: ' + error.message);
    }
}

// Cancelar edição
function cancelarEdicao() {
    document.getElementById('formAnimal').reset();
    document.getElementById('animalId').value = '';
    const submitBtn = document.querySelector('#formAnimal button[type="submit"]');
    submitBtn.textContent = 'Cadastrar Animal';
    submitBtn.onclick = criarAnimal;
}

// Deletar animal
async function deletarAnimal(id) {
    if (!isAdmin()) {
        alert('Apenas administradores podem deletar animais.');
        return;
    }
    
    if (!confirm('Tem certeza que deseja deletar este animal?')) {
        return;
    }
    
    try {
        await authenticatedFetch(`/animais/api/${id}`, {
            method: 'DELETE'
        });
        
        alert('Animal deletado com sucesso!');
        carregarAnimais();
    } catch (error) {
        alert('Erro ao deletar animal: ' + error.message);
    }
}

// Ver detalhes
async function verDetalhes(id) {
    const animal = await authenticatedFetch(`/animais/api/${id}`);
    if (!animal) return;
    
    // Carregar comentários
    const comentarios = await authenticatedFetch(`/api/comentarios/animal/${id}`);
    
    // Criar modal de detalhes
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h2>${animal.nome}</h2>
                <span class="modal-close" onclick="this.closest('.modal').remove()">&times;</span>
            </div>
            <div class="modal-body">
                <div class="detail-section">
                    <h3>Informações do Animal</h3>
                    <p><strong>Espécie:</strong> ${animal.especie}</p>
                    <p><strong>Idade:</strong> ${animal.idade} anos</p>
                    <p><strong>Habitat:</strong> ${animal.habitat}</p>
                    <p><strong>Status:</strong> <span class="status-badge status-${animal.status?.toLowerCase().replace(' ', '-')}">${animal.status || 'Ativo'}</span></p>
                    ${animal.descricao ? `<p><strong>Descrição:</strong> ${animal.descricao}</p>` : ''}
                </div>
                
                <div class="comments-section">
                    <h3>Comentários (${comentarios.length})</h3>
                    
                    <div class="comment-form">
                        <textarea id="novoComentario" placeholder="Escreva seu comentário..." rows="3"></textarea>
                        <button onclick="adicionarComentario(${id})" class="btn-submit">Enviar Comentário</button>
                    </div>
                    
                    <div class="comments-list" id="comentariosList">
                        ${comentarios.length === 0 ? '<p class="no-data">Nenhum comentário ainda. Seja o primeiro!</p>' : 
                            comentarios.map(c => `
                                <div class="comment-item">
                                    <div class="comment-header">
                                        <strong>${c.usuario}</strong>
                                        <span class="comment-date">${new Date(c.dataCriacao).toLocaleString('pt-BR')}</span>
                                    </div>
                                    <div class="comment-text">${c.texto}</div>
                                    ${isAdmin() ? `<button onclick="deletarComentario(${c.id}, ${id})" class="btn-delete-comment admin-only">Deletar</button>` : ''}
                                </div>
                            `).join('')
                        }
                    </div>
                </div>
            </div>
        </div>
    `;
    
    document.body.appendChild(modal);
    updateUIByRole();
}

// Adicionar comentário
async function adicionarComentario(animalId) {
    const texto = document.getElementById('novoComentario').value.trim();
    
    if (!texto) {
        alert('Digite um comentário antes de enviar.');
        return;
    }
    
    if (texto.length < 5) {
        alert('O comentário deve ter pelo menos 5 caracteres.');
        return;
    }
    
    try {
        await authenticatedFetch('/api/comentarios', {
            method: 'POST',
            body: JSON.stringify({
                animalId: animalId,
                texto: texto
            })
        });
        
        // Recarregar detalhes
        document.querySelector('.modal').remove();
        verDetalhes(animalId);
        alert('Comentário adicionado com sucesso!');
    } catch (error) {
        alert('Erro ao adicionar comentário: ' + error.message);
    }
}

// Deletar comentário
async function deletarComentario(comentarioId, animalId) {
    if (!isAdmin()) {
        alert('Apenas administradores podem deletar comentários.');
        return;
    }
    
    if (!confirm('Tem certeza que deseja deletar este comentário?')) {
        return;
    }
    
    try {
        await authenticatedFetch(`/api/comentarios/${comentarioId}`, {
            method: 'DELETE'
        });
        
        // Recarregar detalhes
        document.querySelector('.modal').remove();
        verDetalhes(animalId);
        alert('Comentário deletado com sucesso!');
    } catch (error) {
        alert('Erro ao deletar comentário: ' + error.message);
    }
}

// Inicializar página
document.addEventListener('DOMContentLoaded', () => {
    if (checkAuth()) {
        updateUIByRole();
        carregarAnimais();
        
        const form = document.getElementById('formAnimal');
        if (form) {
            form.onsubmit = criarAnimal;
        }
    }
});