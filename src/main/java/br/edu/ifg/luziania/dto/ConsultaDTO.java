package br.edu.ifg.luziania.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class ConsultaDTO {

    @NotNull(message = "ID do animal é obrigatório")
    private Long animalId;

    @NotNull(message = "Data da consulta é obrigatória")
    private LocalDateTime dataConsulta;

    @NotBlank(message = "Veterinário é obrigatório")
    @Size(min = 2, max = 100, message = "Nome do veterinário deve ter entre 2 e 100 caracteres")
    private String veterinario;

    @NotBlank(message = "Motivo da consulta é obrigatório")
    @Size(min = 5, max = 200, message = "Motivo deve ter entre 5 e 200 caracteres")
    private String motivo;

    @Size(max = 1000, message = "Diagnóstico não pode exceder 1000 caracteres")
    private String diagnostico;

    @Size(max = 500, message = "Tratamento prescrito não pode exceder 500 caracteres")
    private String tratamento;

    private Double peso; // Peso do animal na consulta
    
    private Double temperatura; // Temperatura do animal

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }
}
