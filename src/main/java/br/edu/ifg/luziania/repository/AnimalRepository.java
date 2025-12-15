package br.edu.ifg.luziania.repository;

import br.edu.ifg.luziania.entity.Animal;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AnimalRepository implements PanacheRepository<Animal> {

    public List<Animal> findByEspecie(String especie) {
        return list("especie", especie);
    }

    public List<Animal> findByStatus(String status) {
        return list("status", status);
    }

    public List<Animal> searchByNome(String nome) {
        return list("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
    }
}
