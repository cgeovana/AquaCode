package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.bo.AnimalBO;
import br.edu.ifg.luziania.bo.ConsultaBO;
import br.edu.ifg.luziania.dto.ConsultaDTO;
import br.edu.ifg.luziania.entity.Animal;
import br.edu.ifg.luziania.entity.Consulta;
import br.edu.ifg.luziania.repository.AnimalRepository;
import br.edu.ifg.luziania.repository.ConsultaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaService {

    @Inject
    ConsultaRepository consultaRepository;

    @Inject
    AnimalRepository animalRepository;

    @Inject
    ConsultaBO consultaBO;

    @Inject
    AnimalBO animalBO;

    /**
     * Validar todas as regras de negócio para criação de consulta
     */
    public List<String> validarCriacao(ConsultaDTO dto) {
        List<String> erros = new ArrayList<>();

        // Verificar se animal existe
        Animal animal = animalRepository.findById(dto.getAnimalId());
        if (animal == null) {
            erros.add("Animal não encontrado");
            return erros;
        }

        // RN1: Validar se animal pode receber consulta
        if (!animalBO.podeReceberConsulta(animal)) {
            erros.add("Animal falecido não pode receber consultas");
        }

        // RN2: Validar regras de negócio específicas da consulta
        erros.addAll(consultaBO.validarRegrasNegocio(dto, animal));

        // RN3: Validar intervalo entre consultas
        List<Consulta> consultasAnteriores = consultaRepository.findByAnimalId(dto.getAnimalId());
        if (!consultaBO.validarIntervaloConsultas(consultasAnteriores, dto.getDataConsulta())) {
            erros.add("Intervalo mínimo de 1 dia entre consultas não respeitado");
        }

        // RN4: Validar horário comercial (warning, não bloqueia)
        if (!consultaBO.validarHorarioComercial(dto.getDataConsulta())) {
            erros.add("Atenção: Consulta fora do horário comercial (8h às 18h)");
        }

        return erros;
    }

    /**
     * Criar nova consulta
     */
    @Transactional
    public Consulta criar(ConsultaDTO dto) {
        Animal animal = animalRepository.findById(dto.getAnimalId());

        Consulta consulta = new Consulta();
        consulta.setAnimal(animal);
        consulta.setDataConsulta(dto.getDataConsulta());
        consulta.setMotivo(dto.getMotivo());
        consulta.setVeterinario(dto.getVeterinario());
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setTratamento(dto.getTratamento());

        consultaRepository.persist(consulta);
        return consulta;
    }

    /**
     * Listar todas as consultas
     */
    public List<Consulta> listarTodas() {
        return consultaRepository.listAll();
    }

    /**
     * Listar consultas por animal
     */
    public List<Consulta> listarPorAnimal(Long animalId) {
        return consultaRepository.findByAnimalId(animalId);
    }

    /**
     * Deletar consulta
     */
    @Transactional
    public boolean deletar(Long id) {
        return consultaRepository.deleteById(id);
    }
}
