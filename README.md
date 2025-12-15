# 🐋 AquaCode - Sistema de Gerenciamento de Animais Marinhos

Sistema web completo para gerenciamento e catalogação de animais marinhos, desenvolvido como projeto prático da disciplina de Programação Web.

---

## 📋 Requisitos do Projeto

Este projeto atende aos requisitos funcionais e não funcionais definidos pelo professor:

### ✅ Requisitos Funcionais Implementados

| # | Requisito | Status | Implementação |
|---|-----------|--------|---------------|
| 1 | **Autenticar usuário** | ✅ | Login via e-mail e senha com JWT |
| 2 | **Manter usuário** | ✅ | CRUD completo de usuários |
| 3 | **Manter perfil de usuário** | ✅ | Roles: `admin` e `user` com permissões distintas |
| 4 | **Navegação de recursos** | ✅ | Menu de navegação em todas as páginas |
| 5 | **Casos de uso específicos** | ✅ | Avistamentos, Espécies, Quiz, Animais |
| 6 | **Rastreabilidade e Auditoria** | ✅ | Log completo de ações com usuário, data/hora e IP |

### ✅ Requisitos Não Funcionais Implementados

| Requisito | Status | Tecnologia |
|-----------|--------|------------|
| Linguagem Java EE (11+) | ✅ | Java 17 |
| Modelo MVC | ✅ | Controllers → Services → Repositories → Entities |
| JAX-RS | ✅ | Endpoints REST com `@Path`, `@GET`, `@POST`, etc. |
| Quarkus | ✅ | Versão 3.25.4 |
| Padrão DAO/Entity | ✅ | Panache Repositories + JPA Entities |
| Padrão BO | ✅ | AnimalBO, VacinaBO, ConsultaBO, VoluntarioBO |
| Comunicação via DTO | ✅ | DTOs para todas as entidades |

---

## 🏗️ Arquitetura do Projeto

```
src/main/java/br/edu/ifg/luziania/
├── bo/              # Business Objects (regras de negócio)
├── config/          # Configurações (Auditoria, Startup)
├── controller/      # Endpoints REST (Controllers)
├── dto/             # Data Transfer Objects
├── entity/          # Entidades JPA
├── repository/      # Repositórios (DAOs) com Panache
└── service/         # Serviços (lógica de aplicação)
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- **Java 17** ou superior
- **Maven 3.8+**
- **Docker** (opcional, para banco de dados)

### 1. Clonar o Repositório

```bash
git clone https://github.com/cgeovana/AquaCode.git
cd AquaCode
```

### 2. Executar em Modo de Desenvolvimento

```bash
./mvnw quarkus:dev
```

> **Windows:** Use `mvnw.cmd quarkus:dev`

### 3. Acessar a Aplicação

- **Página Inicial:** http://localhost:8080
- **Login:** http://localhost:8080/login
- **Dev UI (Quarkus):** http://localhost:8080/q/dev/

---

## 👤 Usuários Padrão para Teste

| E-mail | Senha | Perfil | Permissões |
|--------|-------|--------|------------|
| `admin@aquacode.com` | `123456` | Administrador | Acesso total |
| `user@aquacode.com` | `123456` | Usuário | Visualização e registro de avistamentos |
| `maria@aquacode.com` | `123456` | Usuário | Visualização e registro de avistamentos |
| `joao@aquacode.com` | `123456` | Usuário | Visualização e registro de avistamentos |

---

## 📦 Casos de Uso Implementados

### 1. **Registro de Avistamentos** (Usuários autenticados)
- Qualquer usuário pode registrar avistamentos de animais marinhos
- Avistamentos ficam pendentes até aprovação do admin
- Inclui coordenadas geográficas e descrição

### 2. **Moderação de Avistamentos** (Somente Admin)
- Aprovar ou rejeitar avistamentos
- Adicionar comentários de moderação

### 3. **Catálogo de Espécies Marinhas**
- Listagem completa com filtros
- Informações científicas detalhadas

### 4. **Quiz Interativo**
- Perguntas sobre identificação de espécies
- Sistema de pontuação

### 5. **Gestão de Animais** (Somente Admin)
- CRUD completo com validações de negócio
- Controle de status (Ativo, Em Tratamento, Recuperado, Falecido)

---

## 🔐 Sistema de Autenticação

- **Autenticação:** E-mail + Senha
- **Token:** JWT com validade de 8 horas
- **Criptografia:** BCrypt para senhas
- **Autorização:** `@RolesAllowed` para controle de acesso

---

## 📊 Sistema de Auditoria

Todas as ações são registradas contendo:
- ✅ Ação executada
- ✅ Usuário executor
- ✅ Data e hora
- ✅ Método HTTP
- ✅ Endpoint acessado
- ✅ IP de origem

---

## 🛠️ Tecnologias Utilizadas

### Back-End
- **Quarkus 3.25.4** - Framework Java
- **Hibernate ORM + Panache** - Persistência
- **SmallRye JWT** - Autenticação
- **PostgreSQL** - Banco de dados (produção)
- **H2 Database** - Banco de dados (desenvolvimento)

### Front-End
- **HTML5 + CSS3** - Estrutura e estilização
- **JavaScript (Vanilla)** - Interatividade
- **Qute** - Template engine

---

## 📁 Estrutura de Pastas

```
AquaCode/
├── src/
│   ├── main/
│   │   ├── java/br/edu/ifg/luziania/
│   │   │   ├── bo/           # Business Objects
│   │   │   ├── config/       # Configurações
│   │   │   ├── controller/   # REST Controllers
│   │   │   ├── dto/          # Data Transfer Objects
│   │   │   ├── entity/       # Entidades JPA
│   │   │   ├── repository/   # Repositórios
│   │   │   └── service/      # Serviços
│   │   └── resources/
│   │       ├── META-INF/resources/
│   │       │   ├── css/      # Estilos
│   │       │   └── js/       # Scripts
│   │       ├── templates/    # Templates Qute
│   │       ├── application.properties
│   │       └── import.sql    # Dados iniciais
│   └── test/                 # Testes
├── pom.xml
└── README.md
```

---

## 🧪 Executando Testes

```bash
./mvnw test
```

---

## 📦 Build para Produção

### Gerar JAR executável

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### Gerar Über-JAR (JAR único)

```bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
java -jar target/*-runner.jar
```

---

## 👥 Equipe de Desenvolvimento

- **Geovana** - Aluna

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos como parte da disciplina de Programação Web do IFG - Campus Luziânia.

---

## 📞 Suporte

Em caso de dúvidas ou problemas, entre em contato através do repositório do GitHub.

