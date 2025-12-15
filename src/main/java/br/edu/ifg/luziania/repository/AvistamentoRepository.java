package br.edu.ifg.luziania.repository;

import java.util.List;

import br.edu.ifg.luziania.entity.Avistamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvistamentoRepository implements PanacheRepository<Avistamento> {

    public List<Avistamento> findByStatus(String status) {
        return list("status", status);
    }

    public List<Avistamento> findByUsuarioId(Long usuarioId) {
        return list("usuario.id", usuarioId);
    }

    public List<Avistamento> findByNomeEspecie(String nomeEspecie) {
        return list("LOWER(nomeEspecie) LIKE LOWER(?1)", "%" + nomeEspecie + "%");
    }

    public List<Avistamento> findPendentes() {
        return list("status = 'PENDENTE' ORDER BY dataSubmissao DESC");
    }

    public List<Avistamento> findAprovados() {
        return list("status = 'APROVADO' ORDER BY dataHoraAvistamento DESC");
    }

    public List<Avistamento> findRejeitados() {
        return list("status = 'REJEITADO' ORDER BY dataModeracao DESC");
    }
}
