package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.config.AuditLog;
import br.edu.ifg.luziania.dto.UsuarioDTO;
import br.edu.ifg.luziania.entity.Usuario;
import br.edu.ifg.luziania.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/usuarios")
@RolesAllowed("admin")
@AuditLog
public class UsuarioController {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> Response.ok(usuario).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado")
                        .build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criar(@Valid UsuarioDTO dto) {
        // Validar regras de negócio
        List<String> erros = usuarioService.validarCriacao(dto);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        Usuario usuario = usuarioService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") Long id, @Valid UsuarioDTO dto) {
        // Validar regras de negócio
        List<String> erros = usuarioService.validarAtualizacao(id, dto);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        Usuario usuario = usuarioService.atualizar(id, dto);
        return Response.ok(usuario).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        // Validar regras de negócio
        List<String> erros = usuarioService.validarDelecao(id);
        if (!erros.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.join("; ", erros))
                    .build();
        }

        usuarioService.deletar(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarRole(@PathParam("id") Long id, @QueryParam("role") String role) {
        // Validar role
        if (!role.equals("admin") && !role.equals("user")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Role deve ser 'admin' ou 'user'")
                    .build();
        }

        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    Usuario usuarioAtualizado = usuarioService.alterarRole(id, role);
                    return Response.ok(usuarioAtualizado).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado")
                        .build());
    }
}
