package br.edu.ifg.luziania.config;

import br.edu.ifg.luziania.service.AuthService;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class StartupConfig {

    private static final Logger LOG = Logger.getLogger(StartupConfig.class);

    @Inject
    AuthService authService;

    void onStart(@Observes StartupEvent ev) {
        LOG.info("Inicializando aplicação AquaCode...");
        try {
            authService.createDefaultUsers();
            LOG.info("Usuários padrão criados: admin/admin123 e user/user123");
        } catch (Exception e) {
            LOG.warn("Usuários padrão já existem ou erro ao criar: " + e.getMessage());
        }
    }
}
