package br.edu.ifg.luziania.repository;

import br.edu.ifg.luziania.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

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
}
