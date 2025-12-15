package br.edu.ifg.luziania.repository;

import br.edu.ifg.luziania.entity.LogAuditoria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AuditoriaDAO implements PanacheRepository<LogAuditoria> {

    public List<LogAuditoria> buscarPorUsuario(String usuario) {
        return find("usuario", usuario).list();
    }

    public List<LogAuditoria> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return find("dataHora BETWEEN ?1 AND ?2", inicio, fim).list();
    }

    public List<LogAuditoria> buscarPorAcao(String acao) {
        return find("acao LIKE ?1", "%" + acao + "%").list();
    }

    public List<LogAuditoria> buscarUltimosRegistros(int quantidade) {
        return find("ORDER BY dataHora DESC").page(0, quantidade).list();
    }
}
