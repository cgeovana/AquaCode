package br.edu.ifg.luziania.repository;

import br.edu.ifg.luziania.entity.Voluntario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VoluntarioRepository implements PanacheRepository<Voluntario> {

    public Optional<Voluntario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public List<Voluntario> findAtivos() {
        return list("ativo = true");
    }

    public List<Voluntario> findByAreaAtuacao(String areaAtuacao) {
        return list("areaAtuacao = ?1 and ativo = true", areaAtuacao);
    }
}
