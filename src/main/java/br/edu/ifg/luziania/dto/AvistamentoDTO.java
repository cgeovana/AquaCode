package br.edu.ifg.luziania.dto;

import java.time.LocalDateTime;

public class AvistamentoDTO {
    private String nomeEspecie;
    private String nomeCientifico;
    private LocalDateTime dataHoraAvistamento;
    private Double latitude;
    private Double longitude;
    private String localDescricao;
    private String observacoes;
    private String fotoUrl;

    // Construtores
    public AvistamentoDTO() {
    }

    // Getters e Setters
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
}
