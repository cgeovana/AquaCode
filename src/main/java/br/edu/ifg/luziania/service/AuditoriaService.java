package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.entity.LogAuditoria;
import br.edu.ifg.luziania.repository.AuditoriaDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AuditoriaService {

    @Inject
    AuditoriaDAO auditoriaDAO;

    @Transactional
    public void registrar(String acao, String usuario, String metodoHttp, String endpoint) {
        LogAuditoria log = new LogAuditoria(acao, usuario, metodoHttp, endpoint);
        auditoriaDAO.persist(log);
    }

    @Transactional
    public void registrar(String acao, String usuario, String metodoHttp, String endpoint, String detalhes) {
        LogAuditoria log = new LogAuditoria(acao, usuario, metodoHttp, endpoint);
        log.setDetalhes(detalhes);
        auditoriaDAO.persist(log);
    }

    @Transactional
    public void registrar(String acao, String usuario, String metodoHttp, String endpoint, String detalhes, String ipOrigem) {
        LogAuditoria log = new LogAuditoria(acao, usuario, metodoHttp, endpoint);
        log.setDetalhes(detalhes);
        log.setIpOrigem(ipOrigem);
        auditoriaDAO.persist(log);
    }

    public List<LogAuditoria> buscarPorUsuario(String usuario) {
        return auditoriaDAO.buscarPorUsuario(usuario);
    }

    public List<LogAuditoria> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return auditoriaDAO.buscarPorPeriodo(inicio, fim);
    }

    public List<LogAuditoria> buscarPorAcao(String acao) {
        return auditoriaDAO.buscarPorAcao(acao);
    }

    public List<LogAuditoria> buscarUltimos(int quantidade) {
        return auditoriaDAO.buscarUltimosRegistros(quantidade);
    }

    public List<LogAuditoria> listarTodos() {
        return auditoriaDAO.listAll();
    }
}
