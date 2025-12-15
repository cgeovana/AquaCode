package br.edu.ifg.luziania.dto;

public class ModeracaoDTO {
    private String status; // APROVADO ou REJEITADO
    private String comentario;

    // Construtores
    public ModeracaoDTO() {
    }

    // Getters e Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
