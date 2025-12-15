# 🚀 Quick Start - AquaCode

## Configuração Rápida (5 minutos)

### 1️⃣ Instalar PostgreSQL
Se ainda não tem, baixe em: https://www.postgresql.org/download/

### 2️⃣ Configurar Banco de Dados

Abra o terminal PostgreSQL (psql) e execute:

```sql
CREATE DATABASE aquacode_db;
CREATE USER aquacode WITH PASSWORD 'aquacode123';
GRANT ALL PRIVILEGES ON DATABASE aquacode_db TO aquacode;
```

Ou use o script pronto:
```bash
psql -U postgres -f database-setup.sql
```

### 3️⃣ Executar Aplicação

```bash
# Windows
mvnw.cmd quarkus:dev

# Linux/Mac
./mvnw quarkus:dev
```

Aguarde a mensagem: **"Listening on: http://localhost:8080"**

### 4️⃣ Testar Login

Abra outro terminal e execute:

```bash
curl -X POST http://localhost:8080/logar ^
  -H "Content-Type: application/json" ^
  -d "{\"usuario\":\"admin\",\"senha\":\"admin123\"}"
```

Você receberá um token JWT! 🎉

### 5️⃣ Criar Primeiro Animal

Copie o token recebido e execute:

```bash
curl -X POST http://localhost:8080/animais/api ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer SEU_TOKEN_AQUI" ^
  -d "{\"nome\":\"Nemo\",\"especie\":\"Peixe-palhaço\",\"idade\":2,\"status\":\"Em observação\"}"
```

## ✅ Pronto!

Seu projeto está rodando com:
- ✅ Banco de dados PostgreSQL
- ✅ Autenticação JWT
- ✅ Validação de dados
- ✅ API REST completa

## 📚 Próximos Passos

1. Abra `GUIA_USO.md` para documentação completa
2. Use `api-examples.http` para testar todos os endpoints
3. Leia `MELHORIAS_IMPLEMENTADAS.md` para entender as mudanças

## 🔐 Usuários Disponíveis

- **Admin**: `admin` / `admin123` (pode deletar)
- **User**: `user` / `user123` (operações normais)

## 🌐 Endpoints Principais

- **Login**: `POST /logar`
- **Animais**: `GET/POST/PUT/DELETE /animais/api`
- **Vacinas**: `GET/POST /vacinas`
- **Consultas**: `GET/POST /consultas`
- **Voluntários**: `GET/POST /voluntarios/api`

## ⚡ Comandos Úteis

```bash
# Parar aplicação
Ctrl + C

# Limpar e compilar
mvnw clean package

# Ver logs
mvnw quarkus:dev

# Acessar banco
psql -U aquacode -d aquacode_db
```

## 🆘 Problemas Comuns

### "Connection refused" ao PostgreSQL
```bash
# Windows: Inicie o serviço
net start postgresql-x64-XX

# Verifique se está rodando
pg_isready
```

### "Port 8080 already in use"
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <numero_do_processo> /F
```

### Banco não conecta
Verifique `src/main/resources/application.properties`:
- URL: `jdbc:postgresql://localhost:5432/aquacode_db`
- User: `aquacode`
- Password: `aquacode123`

## 📞 Mais Informações

- 📖 Documentação completa: `GUIA_USO.md`
- 🔧 Exemplos de API: `api-examples.http`
- 📝 Lista de melhorias: `MELHORIAS_IMPLEMENTADAS.md`

---

**Divirta-se codificando! 🐠🐢🐬**
