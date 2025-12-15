package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.bo.VoluntarioBO;
import br.edu.ifg.luziania.dto.VoluntarioDTO;
import br.edu.ifg.luziania.entity.Voluntario;
import br.edu.ifg.luziania.repository.VoluntarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VoluntarioService {

    @Inject
    VoluntarioRepository voluntarioRepository;

    @Inject
    VoluntarioBO voluntarioBO;

    /**
     * Validar regras de negócio para criação
     */
    public List<String> validarCriacao(VoluntarioDTO dto) {
        return voluntarioBO.validarRegrasNegocio(dto);
    }

    /**
     * Validar regras de negócio para atualização
     */
    public List<String> validarAtualizacao(Long id, VoluntarioDTO dto) {
        List<String> erros = voluntarioBO.validarRegrasNegocio(dto);
        
        if (voluntarioRepository.findById(id) == null) {
            erros.add("Voluntário não encontrado");
        }
        
        return erros;
    }

    /**
     * Criar voluntário
     */
    @Transactional
    public Voluntario criar(VoluntarioDTO dto) {
        Voluntario voluntario = new Voluntario();
        voluntario.setNome(dto.getNome());
        voluntario.setEmail(dto.getEmail());
        voluntario.setTelefone(dto.getTelefone());
        voluntario.setDisponibilidade(dto.getDisponibilidade());
        voluntario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        voluntarioRepository.persist(voluntario);
        return voluntario;
    }

    /**
     * Atualizar voluntário
     */
    @Transactional
    public Voluntario atualizar(Long id, VoluntarioDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id);
        
        voluntario.setNome(dto.getNome());
        voluntario.setEmail(dto.getEmail());
        voluntario.setTelefone(dto.getTelefone());
        voluntario.setDisponibilidade(dto.getDisponibilidade());
        if (dto.getAtivo() != null) {
            voluntario.setAtivo(dto.getAtivo());
        }

        return voluntario;
    }

    /**
     * Listar todos
     */
    public List<Voluntario> listarTodos() {
        return voluntarioRepository.listAll();
    }

    /**
     * Buscar por ID
     */
    public Optional<Voluntario> buscarPorId(Long id) {
        return Optional.ofNullable(voluntarioRepository.findById(id));
    }

    /**
     * Deletar
     */
    @Transactional
    public boolean deletar(Long id) {
        return voluntarioRepository.deleteById(id);
    }
}
