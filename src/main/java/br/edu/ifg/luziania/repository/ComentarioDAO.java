package br.edu.ifg.luziania.repository;

import br.edu.ifg.luziania.entity.Comentario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ComentarioDAO implements PanacheRepository<Comentario> {

    public List<Comentario> buscarPorAnimal(Long animalId) {
        return find("animal.id = ?1 AND ativo = true ORDER BY dataCriacao DESC", animalId).list();
    }

    public List<Comentario> buscarPorUsuario(String usuario) {
        return find("usuario = ?1 AND ativo = true ORDER BY dataCriacao DESC", usuario).list();
    }

    public List<Comentario> buscarAtivos() {
        return find("ativo = true ORDER BY dataCriacao DESC").list();
    }
}
