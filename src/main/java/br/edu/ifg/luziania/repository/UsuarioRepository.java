package br.edu.ifg.luziania.repository;

import java.util.Optional;

import br.edu.ifg.luziania.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Optional<Usuario> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

    public boolean existsByUsername(String username) {
        return count("username", username) > 0;
    }

    public Optional<Usuario> findActiveByUsername(String username) {
        return find("username = ?1 and ativo = true", username).firstResultOptional();
    }

    public Optional<Usuario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public boolean existsByEmail(String email) {
        return count("email", email) > 0;
    }

    public Optional<Usuario> findActiveByEmail(String email) {
        return find("email = ?1 and ativo = true", email).firstResultOptional();
    }
}
