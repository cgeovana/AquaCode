package br.edu.ifg.luziania.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/primeiro")
public class PrimeiroController {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance primeiro();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return Templates.primeiro();
    }


    @Path("/ola")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ola(){
        return "ola.";
    }

}
