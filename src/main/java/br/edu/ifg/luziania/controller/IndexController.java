package br.edu.ifg.luziania.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/home")
public class IndexController {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance home();
    }


    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return IndexController.Templates.home();
    }
}

