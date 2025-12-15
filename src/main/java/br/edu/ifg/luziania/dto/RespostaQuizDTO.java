package br.edu.ifg.luziania.dto;

public class RespostaQuizDTO {
    private Long questaoId;
    private String resposta; // A, B, C ou D

    // Construtores
    public RespostaQuizDTO() {
    }

    // Getters e Setters
    public Long getQuestaoId() {
        return questaoId;
    }

    public void setQuestaoId(Long questaoId) {
        this.questaoId = questaoId;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}
