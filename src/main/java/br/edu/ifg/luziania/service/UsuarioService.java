package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.dto.UsuarioDTO;
import br.edu.ifg.luziania.entity.Usuario;
import br.edu.ifg.luziania.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    /**
     * Validar regras de negócio para criação de usuário
     */
    public List<String> validarCriacao(UsuarioDTO dto) {
        List<String> erros = new ArrayList<>();

        // RN1: Username deve ser único
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            erros.add("Usuário já existe");
        }

        // RN2: Role deve ser válida
        if (!validarRole(dto.getRole())) {
            erros.add("Role deve ser 'admin' ou 'user'");
        }

        // RN3: Username deve ter no mínimo 3 caracteres
        if (dto.getUsername() == null || dto.getUsername().trim().length() < 3) {
            erros.add("Username deve ter no mínimo 3 caracteres");
        }

        // RN4: Senha deve ter no mínimo 6 caracteres
        if (dto.getPassword() == null || dto.getPassword().length() < 6) {
            erros.add("Senha deve ter no mínimo 6 caracteres");
        }

        // RN5: Email deve ser válido
        if (dto.getEmail() != null && !dto.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            erros.add("Email inválido");
        }

        return erros;
    }

    /**
     * Validar regras de negócio para atualização de usuário
     */
    public List<String> validarAtualizacao(Long id, UsuarioDTO dto) {
        List<String> erros = new ArrayList<>();

        Usuario usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente == null) {
            erros.add("Usuário não encontrado");
            return erros;
        }

        // RN6: Novo username deve ser único (se mudou)
        if (!usuarioExistente.getUsername().equals(dto.getUsername())) {
            if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
                erros.add("Nome de usuário já existe");
            }
        }

        // RN7: Role deve ser válida
        if (!validarRole(dto.getRole())) {
            erros.add("Role deve ser 'admin' ou 'user'");
        }

        return erros;
    }

    /**
     * Validar se usuário pode ser deletado
     */
    public List<String> validarDelecao(Long id) {
        List<String> erros = new ArrayList<>();

        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            erros.add("Usuário não encontrado");
            return erros;
        }

        // RN8: Não permitir deletar o usuário admin principal
        if (usuario.getUsername().equals("admin")) {
            erros.add("Não é permitido deletar o usuário admin");
        }

        return erros;
    }

    /**
     * Criar usuário com validações
     */
    @Transactional
    public Usuario criar(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword()); // BCrypt aplicado no setter
        usuario.setRole(dto.getRole());
        usuario.setEmail(dto.getEmail());
        usuario.setNome(dto.getNome());

        usuarioRepository.persist(usuario);
        return usuario;
    }

    /**
     * Atualizar usuário
     */
    @Transactional
    public Usuario atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id);
        
        if (!usuario.getUsername().equals(dto.getUsername())) {
            usuario.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(dto.getPassword());
        }
        
        usuario.setRole(dto.getRole());
        usuario.setEmail(dto.getEmail());
        usuario.setNome(dto.getNome());

        return usuario;
    }

    /**
     * Deletar usuário
     */
    @Transactional
    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        usuarioRepository.delete(usuario);
    }

    /**
     * Alterar role do usuário
     */
    @Transactional
    public Usuario alterarRole(Long id, String role) {
        Usuario usuario = usuarioRepository.findById(id);
        usuario.setRole(role);
        return usuario;
    }

    /**
     * Validar se role é válida
     */
    private boolean validarRole(String role) {
        return role != null && (role.equals("admin") || role.equals("user"));
    }

    /**
     * Buscar usuário por ID
     */
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(usuarioRepository.findById(id));
    }

    /**
     * Listar todos os usuários
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.listAll();
    }

    /**
     * Alterar status do usuário (ativar/banir)
     */
    @Transactional
    public Usuario alterarStatus(Long id, Boolean ativo) {
        Usuario usuario = usuarioRepository.findById(id);
        usuario.setAtivo(ativo);
        return usuario;
    }
}
