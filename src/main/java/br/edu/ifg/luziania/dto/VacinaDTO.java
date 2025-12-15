package br.edu.ifg.luziania.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class VacinaDTO {

    @NotBlank(message = "Nome da vacina é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotNull(message = "ID do animal é obrigatório")
    private Long animalId;

    @NotNull(message = "Data da aplicação é obrigatória")
    private LocalDate dataAplicacao;

    @NotBlank(message = "Lote é obrigatório")
    private String lote;

    private LocalDate validade;

    @NotBlank(message = "Dosagem é obrigatória")
    private String dosagem;

    @Future(message = "Próxima dose deve ser uma data futura")
    private LocalDate proximaDose;

    @NotBlank(message = "Veterinário responsável é obrigatório")
    @Size(min = 2, max = 100, message = "Nome do veterinário deve ter entre 2 e 100 caracteres")
    private String veterinario;

    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public LocalDate getProximaDose() {
        return proximaDose;
    }

    public void setProximaDose(LocalDate proximaDose) {
        this.proximaDose = proximaDose;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
