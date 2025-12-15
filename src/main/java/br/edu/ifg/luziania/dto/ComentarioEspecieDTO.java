package br.edu.ifg.luziania.dto;

import java.time.LocalDateTime;

public class ComentarioEspecieDTO {
    
    private Long id;
    private Long especieId;
    private String especieNome;
    private Long usuarioId;
    private String usuarioNome;
    private String texto;
    private LocalDateTime dataCriacao;
    private Boolean ativo;

    public ComentarioEspecieDTO() {}

    public ComentarioEspecieDTO(Long id, Long especieId, String especieNome, 
                                 Long usuarioId, String usuarioNome, 
                                 String texto, LocalDateTime dataCriacao, Boolean ativo) {
        this.id = id;
        this.especieId = especieId;
        this.especieNome = especieNome;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.texto = texto;
        this.dataCriacao = dataCriacao;
        this.ativo = ativo;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEspecieId() {
        return especieId;
    }

    public void setEspecieId(Long especieId) {
        this.especieId = especieId;
    }

    public String getEspecieNome() {
        return especieNome;
    }

    public void setEspecieNome(String especieNome) {
        this.especieNome = especieNome;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
