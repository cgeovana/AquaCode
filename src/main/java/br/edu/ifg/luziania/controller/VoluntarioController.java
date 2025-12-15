package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.config.AuditLog;
import br.edu.ifg.luziania.dto.VoluntarioDTO;
import br.edu.ifg.luziania.entity.Voluntario;
import br.edu.ifg.luziania.service.VoluntarioService;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/voluntarios")
@AuditLog
public class VoluntarioController {

    @Inject
    VoluntarioService voluntarioService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance paginaVoluntario();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return VoluntarioController.Templates.paginaVoluntario();
    }

    @GET
    @Path("/api")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public List<Voluntario> listar() {
        return voluntarioService.listarTodos();
    }

    @POST
    @Path("/api")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criar(@Valid VoluntarioDTO dto) {
        // Validar regras de negócio
        List<String> erros = voluntarioService.validarCriacao(dto);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        Voluntario voluntario = voluntarioService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(voluntario).build();
    }

    @PUT
    @Path("/api/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response atualizar(@PathParam("id") Long id, @Valid VoluntarioDTO dto) {
        // Validar regras de negócio
        List<String> erros = voluntarioService.validarAtualizacao(id, dto);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        Voluntario voluntario = voluntarioService.atualizar(id, dto);
        return Response.ok(voluntario).build();
    }

    @DELETE
    @Path("/api/{id}")
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id) {
        boolean deleted = voluntarioService.deletar(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}