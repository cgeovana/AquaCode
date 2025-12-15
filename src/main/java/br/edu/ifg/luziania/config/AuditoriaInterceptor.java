package br.edu.ifg.luziania.config;

import br.edu.ifg.luziania.service.AuditoriaService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Interceptor
@AuditLog
@Priority(Interceptor.Priority.APPLICATION)
public class AuditoriaInterceptor {

    @Inject
    AuditoriaService auditoriaService;

    @Inject
    JsonWebToken jwt;

    @Context
    SecurityContext securityContext;

    @AroundInvoke
    public Object auditMethod(InvocationContext context) throws Exception {
        String metodo = context.getMethod().getName();
        String classe = context.getTarget().getClass().getSimpleName();
        String usuario = "anonimo";

        try {
            if (jwt != null && jwt.getName() != null) {
                usuario = jwt.getName();
            }
        } catch (Exception e) {
            // JWT não disponível, usuário permanece como "anonimo"
        }

        String acao = String.format("%s.%s", classe, metodo);
        String detalhes = construirDetalhes(context);

        try {
            Object resultado = context.proceed();
            auditoriaService.registrar(acao, usuario, "INTERNAL", classe, detalhes);
            return resultado;
        } catch (Exception e) {
            auditoriaService.registrar(acao + " [ERRO]", usuario, "INTERNAL", classe, 
                    detalhes + " | Erro: " + e.getMessage());
            throw e;
        }
    }

    private String construirDetalhes(InvocationContext context) {
        Object[] params = context.getParameters();
        if (params == null || params.length == 0) {
            return "Sem parâmetros";
        }
        
        StringBuilder sb = new StringBuilder("Params: ");
        for (int i = 0; i < params.length && i < 3; i++) {
            if (params[i] != null) {
                sb.append(params[i].getClass().getSimpleName());
                if (i < params.length - 1 && i < 2) sb.append(", ");
            }
        }
        return sb.toString();
    }
}
