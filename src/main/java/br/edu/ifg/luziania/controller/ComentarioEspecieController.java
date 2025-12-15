package br.edu.ifg.luziania.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.edu.ifg.luziania.dto.ComentarioEspecieDTO;
import br.edu.ifg.luziania.entity.ComentarioEspecie;
import br.edu.ifg.luziania.entity.EspecieMarinha;
import br.edu.ifg.luziania.entity.Usuario;
import br.edu.ifg.luziania.repository.ComentarioEspecieDAO;
import br.edu.ifg.luziania.repository.EspecieMarinhaRepository;
import br.edu.ifg.luziania.repository.UsuarioRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/especies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComentarioEspecieController {

    @Inject
    ComentarioEspecieDAO comentarioDAO;

    @Inject
    EspecieMarinhaRepository especieRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JsonWebToken jwt;

    // Listar comentários de uma espécie (público)
    @GET
    @Path("/{especieId}/comentarios")
    @RolesAllowed({"user", "admin"})
    public Response listarComentarios(@PathParam("especieId") Long especieId) {
        List<ComentarioEspecie> comentarios = comentarioDAO.buscarPorEspecie(especieId);
        List<ComentarioEspecieDTO> dtos = comentarios.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    // Adicionar comentário (requer autenticação)
    @POST
    @Path("/{especieId}/comentarios")
    @RolesAllowed({"user", "admin"})
    @Transactional
    public Response adicionarComentario(@PathParam("especieId") Long especieId, ComentarioEspecieDTO dto) {
        // Validar texto
        if (dto.getTexto() == null || dto.getTexto().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"O texto do comentário é obrigatório\"}")
                .build();
        }

        if (dto.getTexto().length() > 1000) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"O comentário deve ter no máximo 1000 caracteres\"}")
                .build();
        }

        // Buscar espécie
        EspecieMarinha especie = especieRepository.findById(especieId);
        if (especie == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Espécie não encontrada\"}")
                .build();
        }

        // Buscar usuário pelo login do token JWT
        String login = jwt.getName();
        Usuario usuario = usuarioRepository.findByUsername(login).orElse(null);
        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"Usuário não encontrado\"}")
                .build();
        }

        // Criar comentário
        ComentarioEspecie comentario = new ComentarioEspecie();
        comentario.setEspecie(especie);
        comentario.setUsuario(usuario);
        comentario.setTexto(dto.getTexto().trim());
        comentario.setDataCriacao(LocalDateTime.now());
        comentario.setAtivo(true);

        comentarioDAO.persist(comentario);

        return Response.status(Response.Status.CREATED)
            .entity(toDTO(comentario))
            .build();
    }

    // Deletar comentário (apenas admin ou autor)
    @DELETE
    @Path("/{especieId}/comentarios/{comentarioId}")
    @RolesAllowed({"user", "admin"})
    @Transactional
    public Response deletarComentario(
            @PathParam("especieId") Long especieId,
            @PathParam("comentarioId") Long comentarioId) {
        
        ComentarioEspecie comentario = comentarioDAO.findById(comentarioId);
        if (comentario == null || !comentario.getEspecie().getId().equals(especieId)) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Comentário não encontrado\"}")
                .build();
        }

        String login = jwt.getName();
        Usuario usuario = usuarioRepository.findByUsername(login).orElse(null);
        boolean isAdmin = jwt.getGroups().contains("admin");

        // Verificar se é admin ou autor do comentário
        if (!isAdmin && !comentario.getUsuario().getId().equals(usuario.getId())) {
            return Response.status(Response.Status.FORBIDDEN)
                .entity("{\"error\": \"Você não tem permissão para deletar este comentário\"}")
                .build();
        }

        // Soft delete
        comentario.setAtivo(false);
        comentarioDAO.persist(comentario);

        return Response.ok("{\"message\": \"Comentário removido com sucesso\"}").build();
    }

    // Contar comentários de uma espécie
    @GET
    @Path("/{especieId}/comentarios/count")
    @RolesAllowed({"user", "admin"})
    public Response contarComentarios(@PathParam("especieId") Long especieId) {
        long count = comentarioDAO.contarPorEspecie(especieId);
        return Response.ok("{\"count\": " + count + "}").build();
    }

    private ComentarioEspecieDTO toDTO(ComentarioEspecie comentario) {
        return new ComentarioEspecieDTO(
            comentario.getId(),
            comentario.getEspecie().getId(),
            comentario.getEspecie().getNomeComum(),
            comentario.getUsuario().getId(),
            comentario.getUsuario().getNome(),
            comentario.getTexto(),
            comentario.getDataCriacao(),
            comentario.getAtivo()
        );
    }
}
