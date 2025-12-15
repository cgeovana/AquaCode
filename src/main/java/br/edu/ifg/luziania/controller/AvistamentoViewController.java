package br.edu.ifg.luziania.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/avistamentos")
public class AvistamentoViewController {

    @Inject
    @Location("AvistamentoController/registrarAvistamento.html")
    Template registrarAvistamento;

    @Inject
    @Location("AvistamentoController/listarAvistamentos.html")
    Template listarAvistamentos;

    @Inject
    @Location("AvistamentoController/adminAvistamentos.html")
    Template adminAvistamentos;

    @GET
    @Path("/registrar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance registrar() {
        return registrarAvistamento.instance();
    }

    @GET
    @Path("/listar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listar() {
        return listarAvistamentos.instance();
    }

    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance admin() {
        return adminAvistamentos.instance();
    }
}
