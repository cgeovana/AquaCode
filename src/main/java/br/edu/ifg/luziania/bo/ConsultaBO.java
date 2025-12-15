package br.edu.ifg.luziania.bo;

import br.edu.ifg.luziania.dto.ConsultaDTO;
import br.edu.ifg.luziania.entity.Animal;
import br.edu.ifg.luziania.entity.Consulta;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaBO {

    public List<String> validarRegrasNegocio(ConsultaDTO dto, Animal animal) {
        List<String> erros = new ArrayList<>();

        // RN1: Data da consulta não pode ser muito antiga (máximo 1 ano no passado)
        if (dto.getDataConsulta() != null) {
            LocalDateTime umAnoAtras = LocalDateTime.now().minusYears(1);
            if (dto.getDataConsulta().isBefore(umAnoAtras)) {
                erros.add("Data da consulta não pode ser anterior a 1 ano");
            }
        }

        // RN2: Motivo deve ter pelo menos 10 caracteres
        if (dto.getMotivo() != null && dto.getMotivo().trim().length() < 10) {
            erros.add("Motivo da consulta deve ter pelo menos 10 caracteres");
        }

        // RN3: Veterinário deve ter pelo menos 3 caracteres
        if (dto.getVeterinario() != null && dto.getVeterinario().trim().length() < 3) {
            erros.add("Nome do veterinário deve ter pelo menos 3 caracteres");
        }

        // RN4: Diagnóstico deve ser informativo (mínimo 5 caracteres)
        if (dto.getDiagnostico() != null && dto.getDiagnostico().trim().length() < 5) {
            erros.add("Diagnóstico deve ter pelo menos 5 caracteres");
        }

        // RN5: Tratamento deve ser descrito (mínimo 5 caracteres)
        if (dto.getTratamento() != null && dto.getTratamento().trim().length() < 5) {
            erros.add("Tratamento deve ter pelo menos 5 caracteres");
        }

        // RN6: Animal deve estar vivo para receber consulta
        if (animal != null && animal.getStatus() != null) {
            if (animal.getStatus().equalsIgnoreCase("FALECIDO")) {
                erros.add("Animal falecido não pode receber consultas");
            }
        }

        return erros;
    }

    public boolean validarIntervaloConsultas(List<Consulta> consultasAnteriores, LocalDateTime novaData) {
        // RN7: Intervalo mínimo de 1 dia entre consultas do mesmo animal
        for (Consulta c : consultasAnteriores) {
            long horasDiferenca = java.time.temporal.ChronoUnit.HOURS.between(
                c.getDataConsulta(), novaData
            );
            if (Math.abs(horasDiferenca) < 24) {
                return false;
            }
        }
        return true;
    }

    public boolean validarHorarioComercial(LocalDateTime dataConsulta) {
        // RN8: Consultas devem ser agendadas em horário comercial (8h às 18h)
        if (dataConsulta == null) return true;
        
        int hora = dataConsulta.getHour();
        return hora >= 8 && hora < 18;
    }

    public boolean validarDiaUtil(LocalDateTime dataConsulta) {
        // RN9: Consultas preferencialmente em dias úteis
        if (dataConsulta == null) return true;
        
        int diaSemana = dataConsulta.getDayOfWeek().getValue();
        return diaSemana >= 1 && diaSemana <= 5; // Segunda a Sexta
    }

    public String determinarPrioridade(String diagnostico, String sintomas) {
        // RN10: Determinação de prioridade baseada em diagnóstico
        if (diagnostico == null && sintomas == null) {
            return "NORMAL";
        }

        String texto = (diagnostico + " " + sintomas).toLowerCase();

        if (texto.contains("emergência") || texto.contains("grave") || 
            texto.contains("crítico") || texto.contains("urgente")) {
            return "ALTA";
        } else if (texto.contains("moderado") || texto.contains("atenção")) {
            return "MÉDIA";
        } else {
            return "NORMAL";
        }
    }

    public boolean validarRetornoNecessario(String tratamento, String diagnostico) {
        // RN11: Verificar se consulta de retorno é necessária
        if (tratamento == null && diagnostico == null) {
            return false;
        }

        String texto = (tratamento + " " + diagnostico).toLowerCase();

        return texto.contains("retorno") || texto.contains("acompanhamento") || 
               texto.contains("revisão") || texto.contains("monitorar");
    }

    public LocalDateTime calcularDataRetorno(LocalDateTime dataConsulta, String tipoTratamento) {
        // RN12: Calcular data sugerida para retorno
        if (dataConsulta == null || tipoTratamento == null) {
            return dataConsulta.plusDays(7); // Padrão: 7 dias
        }

        tipoTratamento = tipoTratamento.toLowerCase();

        if (tipoTratamento.contains("cirurgia")) {
            return dataConsulta.plusDays(3);
        } else if (tipoTratamento.contains("medicação")) {
            return dataConsulta.plusDays(7);
        } else if (tipoTratamento.contains("fisioterapia")) {
            return dataConsulta.plusDays(2);
        } else {
            return dataConsulta.plusDays(14);
        }
    }
}
