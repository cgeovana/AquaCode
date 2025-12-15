// Verificar se já está logado ao carregar a página de login
(function checkAlreadyLogged() {
    var token = localStorage.getItem('token') || localStorage.getItem('jwt_token');
    var username = localStorage.getItem('username');
    if (token && username) {
        // Usuário já está logado, redirecionar para home
        window.location.href = '/';
    }
})();

function login(){

    let domain = "http://localhost:8080"
    let path = "/logar";
    let url = domain + path;
    console.log(url);

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            usuario: document.getElementById("usuario").value,
            senha: document.getElementById("senha").value
        }),
    }).then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
        .then(data => {
            // Salvar token no localStorage
            localStorage.setItem('token', data.token);
            localStorage.setItem('username', data.username);
            localStorage.setItem('role', data.role);
            
            // Redirecionar para página do catálogo
            alert(data.message);
            location.href = '/especies/listar';
        })
        .catch(error => {
            // Handle any errors during the fetch or parsing
            console.error('There was a problem with the fetch operation:', error);
        });

}

// Função para a nova página de login
async function fazerLogin(event) {
    event.preventDefault();
    
    const errorDiv = document.getElementById('errorMessage');
    const successDiv = document.getElementById('successMessage');
    
    if (errorDiv) errorDiv.style.display = 'none';
    if (successDiv) successDiv.style.display = 'none';
    
    const usuario = document.getElementById('usuario').value;
    const senha = document.getElementById('senha').value;
    
    try {
        const response = await fetch('/logar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                usuario: usuario,
                senha: senha
            })
        });
        
        if (response.ok) {
            const data = await response.json();
            
            // Salvar dados no localStorage
            localStorage.setItem('token', data.token);
            localStorage.setItem('username', data.username);
            localStorage.setItem('role', data.role);
            
            if (successDiv) {
                successDiv.textContent = 'Login realizado com sucesso! Redirecionando...';
                successDiv.style.display = 'block';
            }
            
            // Redirecionar após 1 segundo
            setTimeout(() => {
                window.location.href = '/especies/listar';
            }, 1000);
        } else {
            const errorData = await response.json();
            if (errorDiv) {
                errorDiv.textContent = errorData.message || 'Usuário ou senha inválidos!';
                errorDiv.style.display = 'block';
            }
        }
    } catch (error) {
        console.error('Erro ao fazer login:', error);
        if (errorDiv) {
            errorDiv.textContent = 'Erro ao conectar com o servidor. Tente novamente.';
            errorDiv.style.display = 'block';
        }
    }
}