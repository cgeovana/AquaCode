package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.bo.AnimalBO;
import br.edu.ifg.luziania.config.AuditLog;
import br.edu.ifg.luziania.dto.AnimalDTO;
import br.edu.ifg.luziania.entity.Animal;
import br.edu.ifg.luziania.repository.AnimalRepository;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/animais")
@AuditLog
public class AnimaisController {

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance paginaAnimal();
        public static native TemplateInstance listarAnimais();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return AnimaisController.Templates.paginaAnimal();
    }

    @GET
    @Path("/lista")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listarAnimais() {
        return AnimaisController.Templates.listarAnimais();
    }

    @Inject
    AnimalRepository animalRepository;

    @Inject
    AnimalBO animalBO;

    @GET
    @Path("/api")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Animal> listar() {
        return animalRepository.listAll();
    }

    @GET
    @Path("/api/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response buscarPorId(@PathParam("id") Long id) {
        Animal animal = animalRepository.findById(id);
        if (animal == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(animal).build();
    }

    @POST
    @Path("/api")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"user", "admin"})
    public Response criar(@Valid AnimalDTO dto) {
        // Validação de regras de negócio via BO
        List<String> erros = animalBO.validarRegrasNegocio(dto);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        // Validação de idade coerente
        if (!animalBO.validarIdadeCoerente(dto.getEspecie(), dto.getIdade())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Idade não coerente com a espécie informada")
                    .build();
        }

        Animal animal = new Animal();
        animal.setNome(dto.getNome());
        animal.setEspecie(dto.getEspecie());
        animal.setIdade(dto.getIdade());
        animal.setDescricao(dto.getDescricao());
        animal.setHabitat(dto.getHabitat());
        animal.setStatus(animalBO.normalizarStatus(dto.getStatus()));
        
        animalRepository.persist(animal);
        return Response.status(Response.Status.CREATED).entity(animal).build();
    }

    @PUT
    @Path("/api/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"user", "admin"})
    public Response atualizar(@PathParam("id") Long id, @Valid AnimalDTO dto) {
        Animal animal = animalRepository.findById(id);
        if (animal == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Validação de regras de negócio via BO
        List<String> erros = animalBO.validarRegrasNegocio(dto);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        // Validação de idade coerente
        if (!animalBO.validarIdadeCoerente(dto.getEspecie(), dto.getIdade())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Idade não coerente com a espécie informada")
                    .build();
        }
        
        animal.setNome(dto.getNome());
        animal.setEspecie(dto.getEspecie());
        animal.setIdade(dto.getIdade());
        animal.setDescricao(dto.getDescricao());
        animal.setHabitat(dto.getHabitat());
        animal.setStatus(animalBO.normalizarStatus(dto.getStatus()));
        
        return Response.ok(animal).build();
    }

    @DELETE
    @Path("/api/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id) {
        boolean deleted = animalRepository.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }


}
