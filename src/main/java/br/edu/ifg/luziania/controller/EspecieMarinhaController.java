package br.edu.ifg.luziania.controller;

import java.util.List;

import br.edu.ifg.luziania.entity.EspecieMarinha;
import br.edu.ifg.luziania.repository.EspecieMarinhaRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/especies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecieMarinhaController {

    @Inject
    EspecieMarinhaRepository especieRepository;

    @GET
    public List<EspecieMarinha> listarTodas() {
        return especieRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        EspecieMarinha especie = especieRepository.findById(id);
        if (especie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(especie).build();
    }

    @GET
    @Path("/buscar")
    public List<EspecieMarinha> buscar(@QueryParam("termo") String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return especieRepository.listAll();
        }
        return especieRepository.searchByNameOrScientificName(termo);
    }

    @GET
    @Path("/categoria/{categoria}")
    public List<EspecieMarinha> buscarPorCategoria(@PathParam("categoria") String categoria) {
        return especieRepository.findByCategoria(categoria);
    }

    @GET
    @Path("/categorias")
    public List<String> listarCategorias() {
        return especieRepository.findAllCategorias();
    }

    @GET
    @Path("/habitat/{habitat}")
    public List<EspecieMarinha> buscarPorHabitat(@PathParam("habitat") String habitat) {
        return especieRepository.findByHabitat(habitat);
    }

    @GET
    @Path("/conservacao/{status}")
    public List<EspecieMarinha> buscarPorStatusConservacao(@PathParam("status") String status) {
        return especieRepository.findByStatusConservacao(status);
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    public Response criar(EspecieMarinha especie) {
        especieRepository.persist(especie);
        return Response.status(Response.Status.CREATED).entity(especie).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response atualizar(@PathParam("id") Long id, EspecieMarinha especieAtualizada) {
        EspecieMarinha especie = especieRepository.findById(id);
        if (especie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        especie.setNomeComum(especieAtualizada.getNomeComum());
        especie.setNomeCientifico(especieAtualizada.getNomeCientifico());
        especie.setReino(especieAtualizada.getReino());
        especie.setFilo(especieAtualizada.getFilo());
        especie.setClasse(especieAtualizada.getClasse());
        especie.setOrdem(especieAtualizada.getOrdem());
        especie.setFamilia(especieAtualizada.getFamilia());
        especie.setGenero(especieAtualizada.getGenero());
        especie.setDescricao(especieAtualizada.getDescricao());
        especie.setHabitat(especieAtualizada.getHabitat());
        especie.setDistribuicaoGeografica(especieAtualizada.getDistribuicaoGeografica());
        especie.setDieta(especieAtualizada.getDieta());
        especie.setStatusConservacao(especieAtualizada.getStatusConservacao());
        especie.setImagemUrl(especieAtualizada.getImagemUrl());
        especie.setCategoria(especieAtualizada.getCategoria());
        especie.setCaracteristicasDistintivas(especieAtualizada.getCaracteristicasDistintivas());

        return Response.ok(especie).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id) {
        EspecieMarinha especie = especieRepository.findById(id);
        if (especie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        especieRepository.delete(especie);
        return Response.noContent().build();
    }
}
