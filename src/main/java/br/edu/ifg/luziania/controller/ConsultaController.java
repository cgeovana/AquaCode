package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.config.AuditLog;
import br.edu.ifg.luziania.dto.ConsultaDTO;
import br.edu.ifg.luziania.entity.Consulta;
import br.edu.ifg.luziania.service.ConsultaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/consultas")
@AuditLog
public class ConsultaController {

    @Inject
    ConsultaService consultaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Consulta> listar() {
        return consultaService.listarTodas();
    }

    @GET
    @Path("/animal/{animalId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Consulta> listarPorAnimal(@PathParam("animalId") Long animalId) {
        return consultaService.listarPorAnimal(animalId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response criar(@Valid ConsultaDTO dto) {
        // Validar regras de negócio
        List<String> erros = consultaService.validarCriacao(dto);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        Consulta consulta = consultaService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(consulta).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id) {
        boolean deleted = consultaService.deletar(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}