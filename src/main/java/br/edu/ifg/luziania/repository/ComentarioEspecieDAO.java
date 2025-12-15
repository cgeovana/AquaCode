package br.edu.ifg.luziania.repository;

import java.util.List;

import br.edu.ifg.luziania.entity.ComentarioEspecie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ComentarioEspecieDAO implements PanacheRepository<ComentarioEspecie> {

    public List<ComentarioEspecie> buscarPorEspecie(Long especieId) {
        return find("especie.id = ?1 AND ativo = true ORDER BY dataCriacao DESC", especieId).list();
    }

    public List<ComentarioEspecie> buscarPorUsuario(Long usuarioId) {
        return find("usuario.id = ?1 AND ativo = true ORDER BY dataCriacao DESC", usuarioId).list();
    }

    public List<ComentarioEspecie> buscarAtivos() {
        return find("ativo = true ORDER BY dataCriacao DESC").list();
    }

    public long contarPorEspecie(Long especieId) {
        return count("especie.id = ?1 AND ativo = true", especieId);
    }
}
