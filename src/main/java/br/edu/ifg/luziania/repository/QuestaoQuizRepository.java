package br.edu.ifg.luziania.repository;

import java.util.List;

import br.edu.ifg.luziania.entity.QuestaoQuiz;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuestaoQuizRepository implements PanacheRepository<QuestaoQuiz> {

    public List<QuestaoQuiz> findByQuizId(Long quizId) {
        return list("quiz.id = ?1 ORDER BY ordem", quizId);
    }
}
