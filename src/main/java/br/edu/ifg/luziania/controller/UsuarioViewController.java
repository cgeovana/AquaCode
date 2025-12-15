package br.edu.ifg.luziania.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/usuarios")
public class UsuarioViewController {

    @Inject
    @Location("UsuarioController/adminUsuarios.html")
    Template adminUsuarios;

    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance admin() {
        return adminUsuarios.instance();
    }
}
