package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.entity.LogAuditoria;
import br.edu.ifg.luziania.service.AuditoriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("/api/auditoria")
@RolesAllowed("admin")
public class AuditoriaController {

    @Inject
    AuditoriaService auditoriaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogAuditoria> listarTodos() {
        return auditoriaService.listarTodos();
    }

    @GET
    @Path("/usuario/{usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogAuditoria> buscarPorUsuario(@PathParam("usuario") String usuario) {
        return auditoriaService.buscarPorUsuario(usuario);
    }

    @GET
    @Path("/acao/{acao}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogAuditoria> buscarPorAcao(@PathParam("acao") String acao) {
        return auditoriaService.buscarPorAcao(acao);
    }

    @GET
    @Path("/periodo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorPeriodo(
            @QueryParam("inicio") String inicio,
            @QueryParam("fim") String fim) {
        
        try {
            LocalDateTime dataInicio = LocalDateTime.parse(inicio);
            LocalDateTime dataFim = LocalDateTime.parse(fim);
            
            List<LogAuditoria> logs = auditoriaService.buscarPorPeriodo(dataInicio, dataFim);
            return Response.ok(logs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Formato de data inválido. Use: yyyy-MM-ddTHH:mm:ss")
                    .build();
        }
    }

    @GET
    @Path("/ultimos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogAuditoria> buscarUltimos(@QueryParam("quantidade") @DefaultValue("50") int quantidade) {
        return auditoriaService.buscarUltimos(quantidade);
    }
}
