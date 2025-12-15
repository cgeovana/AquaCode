package br.edu.ifg.luziania.bo;

import br.edu.ifg.luziania.dto.VacinaDTO;
import br.edu.ifg.luziania.entity.Animal;
import br.edu.ifg.luziania.entity.Vacina;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VacinaBO {

    public List<String> validarRegrasNegocio(VacinaDTO dto, Animal animal) {
        List<String> erros = new ArrayList<>();

        // RN1: Nome da vacina deve ter pelo menos 3 caracteres
        if (dto.getNome() != null && dto.getNome().trim().length() < 3) {
            erros.add("Nome da vacina deve ter pelo menos 3 caracteres");
        }

        // RN2: Lote deve ter formato válido (exemplo: mínimo 5 caracteres)
        if (dto.getLote() != null && dto.getLote().trim().length() < 5) {
            erros.add("Lote deve ter pelo menos 5 caracteres");
        }

        // RN3: Data de aplicação não pode ser futura
        if (dto.getDataAplicacao() != null && dto.getDataAplicacao().isAfter(LocalDate.now())) {
            erros.add("Data de aplicação não pode ser futura");
        }

        // RN4: Validade deve ser posterior à data de aplicação
        if (dto.getDataAplicacao() != null && dto.getValidade() != null) {
            if (dto.getValidade().isBefore(dto.getDataAplicacao())) {
                erros.add("Data de validade deve ser posterior à data de aplicação");
            }
        }

        // RN5: Dosagem deve ser positiva
        if (dto.getDosagem() != null && dto.getDosagem().trim().isEmpty()) {
            erros.add("Dosagem deve ser informada");
        }

        // RN6: Animal deve estar apto a receber vacina
        if (animal != null && animal.getStatus() != null) {
            if (animal.getStatus().equalsIgnoreCase("FALECIDO")) {
                erros.add("Animal falecido não pode receber vacinas");
            }
        }

        return erros;
    }

    public boolean validarIntervaloReaplicacao(List<Vacina> vacinasAnteriores, LocalDate novaData, String nomeVacina) {
        // RN7: Intervalo mínimo de 30 dias entre aplicações da mesma vacina
        for (Vacina v : vacinasAnteriores) {
            if (v.getNome().equalsIgnoreCase(nomeVacina)) {
                long diasDiferenca = java.time.temporal.ChronoUnit.DAYS.between(
                    v.getDataAplicacao(), novaData
                );
                if (diasDiferenca < 30) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validarValidade(LocalDate validade) {
        // RN8: Vacina com validade expirada não pode ser aplicada
        return validade != null && !validade.isBefore(LocalDate.now());
    }

    public String calcularProximaDose(LocalDate dataAplicacao, String tipoVacina) {
        // RN9: Cálculo de próxima dose baseado no tipo de vacina
        if (tipoVacina == null || dataAplicacao == null) {
            return "Não calculado";
        }

        tipoVacina = tipoVacina.toLowerCase();
        LocalDate proximaDose;

        if (tipoVacina.contains("anual")) {
            proximaDose = dataAplicacao.plusYears(1);
        } else if (tipoVacina.contains("semestral")) {
            proximaDose = dataAplicacao.plusMonths(6);
        } else if (tipoVacina.contains("trimestral")) {
            proximaDose = dataAplicacao.plusMonths(3);
        } else {
            proximaDose = dataAplicacao.plusMonths(1);
        }

        return proximaDose.toString();
    }

    public boolean validarDosagemPorIdade(Integer idadeAnimal, String dosagem) {
        // RN10: Validação de dosagem adequada por idade do animal
        if (idadeAnimal == null || dosagem == null) return true;

        dosagem = dosagem.toLowerCase();
        
        if (idadeAnimal < 1) {
            // Filhotes devem receber meia dose
            return dosagem.contains("0.5") || dosagem.contains("meia");
        } else if (idadeAnimal > 20) {
            // Animais idosos podem precisar dose reduzida
            return !dosagem.contains("dose dupla");
        }
        
        return true;
    }
}
