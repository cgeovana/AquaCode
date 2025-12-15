package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.config.AuditLog;
import br.edu.ifg.luziania.dto.ComentarioDTO;
import br.edu.ifg.luziania.entity.Animal;
import br.edu.ifg.luziania.entity.Comentario;
import br.edu.ifg.luziania.repository.AnimalRepository;
import br.edu.ifg.luziania.repository.ComentarioDAO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/api/comentarios")
@AuditLog
public class ComentarioController {

    @Inject
    ComentarioDAO comentarioDAO;

    @Inject
    AnimalRepository animalRepository;

    @Inject
    JsonWebToken jwt;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/animal/{animalId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Comentario> listarPorAnimal(@PathParam("animalId") Long animalId) {
        return comentarioDAO.buscarPorAnimal(animalId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Comentario> listarTodos() {
        return comentarioDAO.buscarAtivos();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"user", "admin"})
    public Response criar(@Valid ComentarioDTO dto) {
        // Verificar se animal existe
        Animal animal = animalRepository.findById(dto.getAnimalId());
        if (animal == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Animal não encontrado")
                    .build();
        }

        // Obter usuário autenticado do JWT
        String usuario = jwt.getName();

        // Criar comentário
        Comentario comentario = new Comentario();
        comentario.setAnimal(animal);
        comentario.setUsuario(usuario);
        comentario.setTexto(dto.getTexto());

        comentarioDAO.persist(comentario);

        return Response.status(Response.Status.CREATED).entity(comentario).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id) {
        Comentario comentario = comentarioDAO.findById(id);
        if (comentario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Soft delete
        comentario.setAtivo(false);

        return Response.noContent().build();
    }

    @GET
    @Path("/meus")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Comentario> meusComentarios() {
        String usuario = jwt.getName();
        return comentarioDAO.buscarPorUsuario(usuario);
    }
}
