package br.edu.ifg.luziania.repository;

import java.util.List;

import br.edu.ifg.luziania.entity.EspecieMarinha;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EspecieMarinhaRepository implements PanacheRepository<EspecieMarinha> {

    public List<EspecieMarinha> findByNomeComum(String nomeComum) {
        return list("LOWER(nomeComum) LIKE LOWER(?1)", "%" + nomeComum + "%");
    }

    public List<EspecieMarinha> findByNomeCientifico(String nomeCientifico) {
        return list("LOWER(nomeCientifico) LIKE LOWER(?1)", "%" + nomeCientifico + "%");
    }

    public EspecieMarinha findByNomeCientificoExato(String nomeCientifico) {
        return find("nomeCientifico", nomeCientifico).firstResult();
    }

    public List<EspecieMarinha> findByCategoria(String categoria) {
        return list("categoria", categoria);
    }

    public List<EspecieMarinha> findByHabitat(String habitat) {
        return list("LOWER(habitat) LIKE LOWER(?1)", "%" + habitat + "%");
    }

    public List<EspecieMarinha> findByStatusConservacao(String status) {
        return list("statusConservacao", status);
    }

    public List<EspecieMarinha> searchByNameOrScientificName(String termo) {
        return list("LOWER(nomeComum) LIKE LOWER(?1) OR LOWER(nomeCientifico) LIKE LOWER(?1)", 
                    "%" + termo + "%");
    }

    public List<String> findAllCategorias() {
        return getEntityManager()
            .createQuery("SELECT DISTINCT e.categoria FROM EspecieMarinha e ORDER BY e.categoria", String.class)
            .getResultList();
    }
}
