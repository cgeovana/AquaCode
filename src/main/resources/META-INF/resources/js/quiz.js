// Quiz JavaScript

let quizAtual = null;
let questoes = [];
let questaoAtualIndex = 0;
let pontuacao = 0;
let acertos = 0;

// Carregar lista de quizzes
async function carregarQuizzes() {
    try {
        const response = await fetch('/api/quiz');
        const quizzes = await response.json();

        const container = document.getElementById('listaQuizzes');
        if (!container) return;

        if (quizzes.length === 0) {
            container.innerHTML = '<p class="sem-dados">Nenhum quiz disponível no momento.</p>';
            return;
        }

        container.innerHTML = quizzes.map(q => `
            <div class="quiz-card" onclick="iniciarQuiz(${q.id})">
                <h3>${q.titulo}</h3>
                <p>${q.descricao || 'Teste seus conhecimentos sobre espécies marinhas!'}</p>
                <button class="btn-primary">Iniciar Quiz</button>
            </div>
        `).join('');
    } catch (error) {
        console.error('Erro ao carregar quizzes:', error);
    }
}

// Iniciar quiz
async function iniciarQuiz(quizId) {
    try {
        const [quizResponse, questoesResponse] = await Promise.all([
            fetch(`/api/quiz/${quizId}`),
            fetch(`/api/quiz/${quizId}/questoes`)
        ]);

        quizAtual = await quizResponse.json();
        questoes = await questoesResponse.json();

        if (questoes.length === 0) {
            alert('Este quiz não possui questões.');
            return;
        }

        questaoAtualIndex = 0;
        pontuacao = 0;
        acertos = 0;

        document.getElementById('selecaoQuiz').style.display = 'none';
        document.getElementById('quizAtivo').style.display = 'block';
        document.getElementById('tituloQuiz').textContent = quizAtual.titulo;
        document.getElementById('totalQuestoes').textContent = questoes.length;

        mostrarQuestao();
    } catch (error) {
        console.error('Erro ao iniciar quiz:', error);
        alert('Erro ao carregar quiz.');
    }
}

// Mostrar questão atual
function mostrarQuestao() {
    const questao = questoes[questaoAtualIndex];
    
    document.getElementById('questaoAtual').textContent = questaoAtualIndex + 1;
    document.getElementById('pontuacao').textContent = pontuacao;
    document.getElementById('imagemQuestao').src = questao.imagemUrl || questao.especie?.imagemUrl || '/images/placeholder.jpg';
    document.getElementById('pergunta').textContent = questao.pergunta;
    document.getElementById('opcaoA').textContent = questao.opcaoA;
    document.getElementById('opcaoB').textContent = questao.opcaoB;
    document.getElementById('opcaoC').textContent = questao.opcaoC;
    document.getElementById('opcaoD').textContent = questao.opcaoD;

    // Resetar estado das opções
    document.querySelectorAll('.opcao').forEach(btn => {
        btn.disabled = false;
        btn.classList.remove('correta', 'incorreta', 'selected');
    });

    document.getElementById('feedback').style.display = 'none';
}

// Responder questão
async function responder(opcao) {
    const questao = questoes[questaoAtualIndex];

    try {
        const response = await fetch('/api/quiz/verificar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                questaoId: questao.id,
                resposta: opcao
            })
        });

        const resultado = await response.json();

        // Desabilitar todas as opções
        document.querySelectorAll('.opcao').forEach(btn => {
            btn.disabled = true;
        });

        // Marcar resposta selecionada
        const opcaoSelecionada = document.querySelector(`[data-opcao="${opcao}"]`);
        opcaoSelecionada.classList.add('selected');

        if (resultado.correta) {
            opcaoSelecionada.classList.add('correta');
            pontuacao += 10;
            acertos++;
            document.getElementById('feedbackTexto').textContent = '✅ Resposta correta!';
            document.getElementById('feedbackTexto').className = 'feedback-correto';
        } else {
            opcaoSelecionada.classList.add('incorreta');
            document.querySelector(`[data-opcao="${resultado.respostaCorreta}"]`).classList.add('correta');
            document.getElementById('feedbackTexto').textContent = '❌ Resposta incorreta!';
            document.getElementById('feedbackTexto').className = 'feedback-incorreto';
        }

        document.getElementById('explicacao').textContent = resultado.explicacao || '';
        document.getElementById('pontuacao').textContent = pontuacao;
        document.getElementById('feedback').style.display = 'block';

    } catch (error) {
        console.error('Erro ao verificar resposta:', error);
        alert('Erro ao verificar resposta.');
    }
}

// Próxima questão
function proximaQuestao() {
    questaoAtualIndex++;

    if (questaoAtualIndex < questoes.length) {
        mostrarQuestao();
    } else {
        mostrarResultadoFinal();
    }
}

// Mostrar resultado final
function mostrarResultadoFinal() {
    document.getElementById('quizAtivo').style.display = 'none';
    document.getElementById('resultadoFinal').style.display = 'block';

    document.getElementById('acertos').textContent = acertos;
    document.getElementById('totalFinal').textContent = questoes.length;
    
    const percentual = Math.round((acertos / questoes.length) * 100);
    document.getElementById('percentual').textContent = percentual;
}

// Reiniciar quiz
function reiniciarQuiz() {
    questaoAtualIndex = 0;
    pontuacao = 0;
    acertos = 0;
    
    document.getElementById('resultadoFinal').style.display = 'none';
    document.getElementById('quizAtivo').style.display = 'block';
    
    mostrarQuestao();
}

// Voltar ao menu
function voltarMenu() {
    document.getElementById('resultadoFinal').style.display = 'none';
    document.getElementById('quizAtivo').style.display = 'none';
    document.getElementById('selecaoQuiz').style.display = 'block';
}

// Inicializar
if (document.getElementById('listaQuizzes')) {
    carregarQuizzes();
}
