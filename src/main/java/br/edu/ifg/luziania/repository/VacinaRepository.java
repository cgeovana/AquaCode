package br.edu.ifg.luziania.repository;

import br.edu.ifg.luziania.entity.Vacina;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class VacinaRepository implements PanacheRepository<Vacina> {

    public List<Vacina> findByAnimalId(Long animalId) {
        return list("animal.id", animalId);
    }

    public List<Vacina> findProximasDoses(LocalDate dataInicio, LocalDate dataFim) {
        return list("proximaDose BETWEEN ?1 AND ?2", dataInicio, dataFim);
    }
}
