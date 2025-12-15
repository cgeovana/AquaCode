package br.edu.ifg.luziania.repository;

import br.edu.ifg.luziania.entity.Consulta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ConsultaRepository implements PanacheRepository<Consulta> {

    public List<Consulta> findByAnimalId(Long animalId) {
        return list("animal.id = ?1 ORDER BY dataConsulta DESC", animalId);
    }

    public List<Consulta> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return list("dataConsulta BETWEEN ?1 AND ?2 ORDER BY dataConsulta DESC", inicio, fim);
    }

    public List<Consulta> findByVeterinario(String veterinario) {
        return list("LOWER(veterinario) = LOWER(?1) ORDER BY dataConsulta DESC", veterinario);
    }
}
