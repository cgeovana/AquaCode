package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.dto.AuthResponseDTO;
import br.edu.ifg.luziania.dto.LoginDTO;
import br.edu.ifg.luziania.service.AuthService;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class LoginController {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance paginaLogin();
        public static native TemplateInstance login();
        public static native TemplateInstance cadastro();
        public static native TemplateInstance home();
    }


    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return LoginController.Templates.home();
    }

    @GET
    @Path("login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getLogin() {
        return LoginController.Templates.login();
    }

    @GET
    @Path("cadastro")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCadastro() {
        return LoginController.Templates.cadastro();
    }

    @Inject
    AuthService authService;

    @POST
    @Path("logar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logar(@Valid LoginDTO dto) {
        return authService.authenticate(dto)
                .map(authResponse -> Response.ok(authResponse).build())
                .orElse(Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new AuthResponseDTO("Usuário ou senha inválidos"))
                        .build());
    }

    @POST
    @Path("cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(@Valid br.edu.ifg.luziania.dto.UsuarioDTO dto) {
        return authService.register(dto);
    }

}