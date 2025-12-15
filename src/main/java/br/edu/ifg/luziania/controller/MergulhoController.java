package br.edu.ifg.luziania.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/mergulho")
public class MergulhoController {

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance listarMergulhos();
    }

    @GET
    @Path("/lista")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listarMergulhos() {
        return MergulhoController.Templates.listarMergulhos();
    }
}
