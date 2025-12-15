package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.config.AuditLog;
import br.edu.ifg.luziania.dto.VacinaDTO;
import br.edu.ifg.luziania.entity.Vacina;
import br.edu.ifg.luziania.service.VacinaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/vacinas")
@AuditLog
public class VacinaController {

    @Inject
    VacinaService vacinaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Vacina> listar() {
        return vacinaService.listarTodas();
    }

    @GET
    @Path("/animal/{animalId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Vacina> listarPorAnimal(@PathParam("animalId") Long animalId) {
        return vacinaService.listarPorAnimal(animalId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response criar(@Valid VacinaDTO dto) {
        // Validar regras de negócio
        List<String> erros = vacinaService.validarCriacao(dto);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        Vacina vacina = vacinaService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(vacina).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id) {
        boolean deleted = vacinaService.deletar(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}