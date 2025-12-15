package br.edu.ifg.luziania.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifg.luziania.dto.RespostaQuizDTO;
import br.edu.ifg.luziania.entity.QuestaoQuiz;
import br.edu.ifg.luziania.entity.Quiz;
import br.edu.ifg.luziania.repository.QuestaoQuizRepository;
import br.edu.ifg.luziania.repository.QuizRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/quiz")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuizController {

    @Inject
    QuizRepository quizRepository;

    @Inject
    QuestaoQuizRepository questaoRepository;

    @GET
    public List<Quiz> listarQuizzes() {
        return quizRepository.findAtivos();
    }

    @GET
    @Path("/todos")
    @RolesAllowed("admin")
    public List<Quiz> listarTodosQuizzes() {
        return quizRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarQuiz(@PathParam("id") Long id) {
        Quiz quiz = quizRepository.findById(id);
        if (quiz == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(quiz).build();
    }

    @GET
    @Path("/{id}/questoes")
    public List<QuestaoQuiz> listarQuestoes(@PathParam("id") Long quizId) {
        return questaoRepository.findByQuizId(quizId);
    }

    @POST
    @Path("/verificar")
    public Response verificarResposta(RespostaQuizDTO dto) {
        QuestaoQuiz questao = questaoRepository.findById(dto.getQuestaoId());
        if (questao == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean correta = questao.getRespostaCorreta().equalsIgnoreCase(dto.getResposta());
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("correta", correta);
        resultado.put("respostaCorreta", questao.getRespostaCorreta());
        resultado.put("explicacao", questao.getExplicacao());

        return Response.ok(resultado).build();
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    public Response criarQuiz(Quiz quiz) {
        quizRepository.persist(quiz);
        return Response.status(Response.Status.CREATED).entity(quiz).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response atualizarQuiz(@PathParam("id") Long id, Quiz quizAtualizado) {
        Quiz quiz = quizRepository.findById(id);
        if (quiz == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        quiz.setTitulo(quizAtualizado.getTitulo());
        quiz.setDescricao(quizAtualizado.getDescricao());
        quiz.setAtivo(quizAtualizado.getAtivo());
        
        return Response.ok(quiz).build();
    }

    @POST
    @Path("/{id}/questoes")
    @Transactional
    @RolesAllowed("admin")
    public Response adicionarQuestao(@PathParam("id") Long quizId, QuestaoQuiz questao) {
        Quiz quiz = quizRepository.findById(quizId);
        if (quiz == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        questao.setQuiz(quiz);
        questaoRepository.persist(questao);
        return Response.status(Response.Status.CREATED).entity(questao).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletarQuiz(@PathParam("id") Long id) {
        Quiz quiz = quizRepository.findById(id);
        if (quiz == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        quizRepository.delete(quiz);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/questoes/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletarQuestao(@PathParam("id") Long id) {
        QuestaoQuiz questao = questaoRepository.findById(id);
        if (questao == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        questaoRepository.delete(questao);
        return Response.noContent().build();
    }
}
