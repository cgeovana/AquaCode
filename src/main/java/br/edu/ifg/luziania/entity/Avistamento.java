package br.edu.ifg.luziania.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "avistamentos")
public class Avistamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeEspecie;

    @Column(length = 100)
    private String nomeCientifico;

    @Column(nullable = false)
    private LocalDateTime dataHoraAvistamento;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 200)
    private String localDescricao;

    @Column(length = 1000)
    private String observacoes;

    @Column(length = 500)
    private String fotoUrl;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 20)
    private String status = "PENDENTE"; // PENDENTE, APROVADO, REJEITADO

    @Column(name = "data_submissao", nullable = false)
    private LocalDateTime dataSubmissao = LocalDateTime.now();

    @Column(name = "moderado_por")
    private String moderadoPor;

    @Column(name = "data_moderacao")
    private LocalDateTime dataModeracao;

    @Column(length = 500)
    private String comentarioModeracao;

    // Construtores
    public Avistamento() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEspecie() {
        return nomeEspecie;
    }

    public void setNomeEspecie(String nomeEspecie) {
        this.nomeEspecie = nomeEspecie;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public LocalDateTime getDataHoraAvistamento() {
        return dataHoraAvistamento;
    }

    public void setDataHoraAvistamento(LocalDateTime dataHoraAvistamento) {
        this.dataHoraAvistamento = dataHoraAvistamento;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocalDescricao() {
        return localDescricao;
    }

    public void setLocalDescricao(String localDescricao) {
        this.localDescricao = localDescricao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataSubmissao() {
        return dataSubmissao;
    }

    public void setDataSubmissao(LocalDateTime dataSubmissao) {
        this.dataSubmissao = dataSubmissao;
    }

    public String getModeradoPor() {
        return moderadoPor;
    }

    public void setModeradoPor(String moderadoPor) {
        this.moderadoPor = moderadoPor;
    }

    public LocalDateTime getDataModeracao() {
        return dataModeracao;
    }

    public void setDataModeracao(LocalDateTime dataModeracao) {
        this.dataModeracao = dataModeracao;
    }

    public String getComentarioModeracao() {
        return comentarioModeracao;
    }

    public void setComentarioModeracao(String comentarioModeracao) {
        this.comentarioModeracao = comentarioModeracao;
    }
}
