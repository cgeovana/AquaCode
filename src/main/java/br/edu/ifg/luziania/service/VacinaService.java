package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.bo.AnimalBO;
import br.edu.ifg.luziania.bo.VacinaBO;
import br.edu.ifg.luziania.dto.VacinaDTO;
import br.edu.ifg.luziania.entity.Animal;
import br.edu.ifg.luziania.entity.Vacina;
import br.edu.ifg.luziania.repository.AnimalRepository;
import br.edu.ifg.luziania.repository.VacinaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VacinaService {

    @Inject
    VacinaRepository vacinaRepository;

    @Inject
    AnimalRepository animalRepository;

    @Inject
    VacinaBO vacinaBO;

    @Inject
    AnimalBO animalBO;

    /**
     * Validar todas as regras de negócio para criação de vacina
     */
    public List<String> validarCriacao(VacinaDTO dto) {
        List<String> erros = new ArrayList<>();

        // Verificar se animal existe
        Animal animal = animalRepository.findById(dto.getAnimalId());
        if (animal == null) {
            erros.add("Animal não encontrado");
            return erros;
        }

        // RN1: Validar se animal pode receber vacina
        if (!animalBO.podeReceberVacina(animal)) {
            erros.add("Animal não está apto a receber vacinas");
        }

        // RN2: Validar regras de negócio específicas da vacina
        erros.addAll(vacinaBO.validarRegrasNegocio(dto, animal));

        // RN3: Validar validade da vacina
        if (dto.getValidade() != null && !vacinaBO.validarValidade(dto.getValidade())) {
            erros.add("Vacina com validade expirada não pode ser aplicada");
        }

        // RN4: Validar intervalo de reaplicação
        List<Vacina> vacinasAnteriores = vacinaRepository.findByAnimalId(dto.getAnimalId());
        if (!vacinaBO.validarIntervaloReaplicacao(vacinasAnteriores, dto.getDataAplicacao(), dto.getNome())) {
            erros.add("Intervalo mínimo entre aplicações não respeitado (30 dias)");
        }

        return erros;
    }

    /**
     * Criar nova vacina
     */
    @Transactional
    public Vacina criar(VacinaDTO dto) {
        Animal animal = animalRepository.findById(dto.getAnimalId());

        Vacina vacina = new Vacina();
        vacina.setNome(dto.getNome());
        vacina.setLote(dto.getLote());
        vacina.setValidade(dto.getValidade());
        vacina.setDosagem(dto.getDosagem());
        vacina.setAnimal(animal);
        vacina.setDataAplicacao(dto.getDataAplicacao());
        vacina.setProximaDose(dto.getProximaDose());
        vacina.setVeterinario(dto.getVeterinario());
        vacina.setObservacoes(dto.getObservacoes());

        vacinaRepository.persist(vacina);
        return vacina;
    }

    /**
     * Listar todas as vacinas
     */
    public List<Vacina> listarTodas() {
        return vacinaRepository.listAll();
    }

    /**
     * Listar vacinas por animal
     */
    public List<Vacina> listarPorAnimal(Long animalId) {
        return vacinaRepository.findByAnimalId(animalId);
    }

    /**
     * Deletar vacina
     */
    @Transactional
    public boolean deletar(Long id) {
        return vacinaRepository.deleteById(id);
    }
}
