package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.dto.CatalogoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/catalogo")
public class CatalogoController {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance paginaCatalogo();
    }


    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return CatalogoController.Templates.paginaCatalogo();
    }
}
