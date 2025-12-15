package br.edu.ifg.luziania.repository;

import java.util.List;

import br.edu.ifg.luziania.entity.Quiz;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuizRepository implements PanacheRepository<Quiz> {

    public List<Quiz> findAtivos() {
        return list("ativo = true ORDER BY titulo");
    }
}
