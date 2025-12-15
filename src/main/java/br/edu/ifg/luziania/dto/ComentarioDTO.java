package br.edu.ifg.luziania.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ComentarioDTO {

    @NotNull(message = "ID do animal é obrigatório")
    private Long animalId;

    @NotBlank(message = "Comentário é obrigatório")
    @Size(min = 5, max = 1000, message = "Comentário deve ter entre 5 e 1000 caracteres")
    private String texto;

    // Getters e Setters
    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
