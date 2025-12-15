package br.edu.ifg.luziania.service;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import br.edu.ifg.luziania.dto.AuthResponseDTO;
import br.edu.ifg.luziania.dto.LoginDTO;
import br.edu.ifg.luziania.entity.Usuario;
import br.edu.ifg.luziania.repository.UsuarioRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {

    @Inject
    UsuarioRepository usuarioRepository;

    public Optional<AuthResponseDTO> authenticate(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findActiveByEmail(loginDTO.getEmail());
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (BcryptUtil.matches(loginDTO.getSenha(), usuario.getPassword())) {
                String token = generateToken(usuario);
                AuthResponseDTO response = new AuthResponseDTO(token, usuario.getUsername(), usuario.getRole());
                return Optional.of(response);
            }
        }
        
        return Optional.empty();
    }

    private String generateToken(Usuario usuario) {
        Set<String> roles = new HashSet<>();
        roles.add(usuario.getRole());

        return Jwt.issuer("https://aquacode.ifg.edu.br")
                .upn(usuario.getUsername())
                .groups(roles)
                .expiresIn(Duration.ofHours(8))
                .sign();
    }

    @Inject
    UsuarioService usuarioService;

    @Transactional
    public jakarta.ws.rs.core.Response register(br.edu.ifg.luziania.dto.UsuarioDTO dto) {
        // Validar regras de negócio
        java.util.List<String> erros = usuarioService.validarCriacao(dto);
        if (!erros.isEmpty()) {
            return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        Usuario usuario = usuarioService.criar(dto);
        return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.CREATED)
                .entity(usuario)
                .build();
    }

    @Transactional
    public Usuario register(String username, String password, String role, String email) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Usuário já existe");
        }
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario(username, password, role, email);
        usuarioRepository.persist(usuario);
        return usuario;
    }

    @Transactional
    public void createDefaultUsers() {
        if (!usuarioRepository.existsByUsername("admin")) {
            register("admin", "admin123", "admin", "admin@aquacode.com");
        }
        if (!usuarioRepository.existsByUsername("user")) {
            register("user", "user123", "user", "user@aquacode.com");
        }
    }
}
