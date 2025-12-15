package br.edu.ifg.luziania.controller;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifg.luziania.dto.AvistamentoDTO;
import br.edu.ifg.luziania.dto.ModeracaoDTO;
import br.edu.ifg.luziania.entity.Avistamento;
import br.edu.ifg.luziania.entity.Usuario;
import br.edu.ifg.luziania.repository.AvistamentoRepository;
import br.edu.ifg.luziania.repository.UsuarioRepository;
import io.quarkus.security.identity.SecurityIdentity;
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

@Path("/api/avistamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvistamentoController {

    @Inject
    AvistamentoRepository avistamentoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @RolesAllowed({"user", "admin"})
    public List<Avistamento> listarTodos() {
        return avistamentoRepository.listAll();
    }

    @GET
    @Path("/meus")
    @RolesAllowed({"user", "admin"})
    public Response listarMeus() {
        String username = securityIdentity.getPrincipal().getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }
        return Response.ok(avistamentoRepository.findByUsuarioId(usuario.getId())).build();
    }

    @GET
    @Path("/pendentes")
    @RolesAllowed("admin")
    public List<Avistamento> listarPendentes() {
        return avistamentoRepository.findPendentes();
    }

    @GET
    @Path("/aprovados")
    public List<Avistamento> listarAprovados() {
        return avistamentoRepository.findAprovados();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Avistamento avistamento = avistamentoRepository.findById(id);
        if (avistamento == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(avistamento).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"user", "admin"})
    public Response criar(AvistamentoDTO dto) {
        String username = securityIdentity.getPrincipal().getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }

        Avistamento avistamento = new Avistamento();
        avistamento.setNomeEspecie(dto.getNomeEspecie());
        avistamento.setNomeCientifico(dto.getNomeCientifico());
        avistamento.setDataHoraAvistamento(dto.getDataHoraAvistamento());
        avistamento.setLatitude(dto.getLatitude());
        avistamento.setLongitude(dto.getLongitude());
        avistamento.setLocalDescricao(dto.getLocalDescricao());
        avistamento.setObservacoes(dto.getObservacoes());
        avistamento.setFotoUrl(dto.getFotoUrl());
        avistamento.setUsuario(usuario);
        avistamento.setStatus("PENDENTE");
        avistamento.setDataSubmissao(LocalDateTime.now());

        avistamentoRepository.persist(avistamento);
        return Response.status(Response.Status.CREATED).entity(avistamento).build();
    }

    @PUT
    @Path("/{id}/moderar")
    @Transactional
    @RolesAllowed("admin")
    public Response moderar(@PathParam("id") Long id, ModeracaoDTO dto) {
        Avistamento avistamento = avistamentoRepository.findById(id);
        if (avistamento == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        avistamento.setStatus(dto.getStatus());
        avistamento.setComentarioModeracao(dto.getComentario());
        avistamento.setModeradoPor(securityIdentity.getPrincipal().getName());
        avistamento.setDataModeracao(LocalDateTime.now());

        return Response.ok(avistamento).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"user", "admin"})
    public Response deletar(@PathParam("id") Long id) {
        Avistamento avistamento = avistamentoRepository.findById(id);
        if (avistamento == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String username = securityIdentity.getPrincipal().getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }
        
        // Admin pode deletar qualquer um, usuário só pode deletar os próprios
        if (!usuario.getRole().equals("admin") && !avistamento.getUsuario().getId().equals(usuario.getId())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        avistamentoRepository.delete(avistamento);
        return Response.noContent().build();
    }
}
