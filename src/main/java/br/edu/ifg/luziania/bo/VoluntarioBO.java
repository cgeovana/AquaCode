package br.edu.ifg.luziania.bo;

import br.edu.ifg.luziania.dto.VoluntarioDTO;
import br.edu.ifg.luziania.entity.Voluntario;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class VoluntarioBO {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern TELEFONE_PATTERN = Pattern.compile(
        "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$"
    );

    public List<String> validarRegrasNegocio(VoluntarioDTO dto) {
        List<String> erros = new ArrayList<>();

        // RN1: Nome deve ter pelo menos 3 caracteres
        if (dto.getNome() != null && dto.getNome().trim().length() < 3) {
            erros.add("Nome deve ter pelo menos 3 caracteres");
        }

        // RN2: Email deve ter formato válido
        if (dto.getEmail() != null && !EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            erros.add("Email deve ter formato válido");
        }

        // RN3: Telefone deve ter formato válido
        if (dto.getTelefone() != null && !TELEFONE_PATTERN.matcher(dto.getTelefone()).matches()) {
            erros.add("Telefone deve ter formato válido (ex: (62) 99999-9999)");
        }

        // RN4: Data de nascimento não pode ser futura
        if (dto.getDataNascimento() != null && dto.getDataNascimento().isAfter(LocalDate.now())) {
            erros.add("Data de nascimento não pode ser futura");
        }

        // RN5: Voluntário deve ter idade mínima de 16 anos
        if (dto.getDataNascimento() != null) {
            int idade = calcularIdade(dto.getDataNascimento());
            if (idade < 16) {
                erros.add("Voluntário deve ter pelo menos 16 anos");
            }
        }

        // RN6: Especialidade deve ter pelo menos 3 caracteres
        if (dto.getEspecialidade() != null && dto.getEspecialidade().trim().length() < 3) {
            erros.add("Especialidade deve ter pelo menos 3 caracteres");
        }

        // RN7: Disponibilidade deve ser informada
        if (dto.getDisponibilidade() != null && dto.getDisponibilidade().trim().isEmpty()) {
            erros.add("Disponibilidade deve ser informada");
        }

        return erros;
    }

    public int calcularIdade(LocalDate dataNascimento) {
        // RN8: Cálculo de idade
        if (dataNascimento == null) return 0;
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public boolean validarIdadeMinima(LocalDate dataNascimento, int idadeMinima) {
        // RN9: Validação de idade mínima
        return calcularIdade(dataNascimento) >= idadeMinima;
    }

    public boolean validarIdadeMaxima(LocalDate dataNascimento, int idadeMaxima) {
        // RN10: Validação de idade máxima
        return calcularIdade(dataNascimento) <= idadeMaxima;
    }

    public boolean validarDisponibilidadeMinima(String disponibilidade) {
        // RN11: Voluntário deve ter pelo menos 4 horas/semana disponíveis
        if (disponibilidade == null) return false;

        disponibilidade = disponibilidade.toLowerCase();
        
        // Procura por padrões como "4h", "8 horas", etc.
        if (disponibilidade.matches(".*\\d+.*")) {
            String numeros = disponibilidade.replaceAll("[^0-9]", "");
            if (!numeros.isEmpty()) {
                int horas = Integer.parseInt(numeros);
                return horas >= 4;
            }
        }
        
        return true; // Se não conseguir extrair, aceita
    }

    public String determinarCategoria(int idade, String especialidade) {
        // RN12: Categorização de voluntário
        if (especialidade != null) {
            especialidade = especialidade.toLowerCase();
            if (especialidade.contains("veterinário") || especialidade.contains("médico")) {
                return "ESPECIALIZADO";
            }
        }

        if (idade >= 18 && idade <= 25) {
            return "JOVEM";
        } else if (idade > 25 && idade <= 60) {
            return "ADULTO";
        } else if (idade > 60) {
            return "SÊNIOR";
        } else {
            return "MENOR";
        }
    }

    public boolean validarEmailUnico(String email, List<Voluntario> voluntariosExistentes) {
        // RN13: Email deve ser único
        if (email == null || voluntariosExistentes == null) return true;

        return voluntariosExistentes.stream()
            .noneMatch(v -> email.equalsIgnoreCase(v.getEmail()));
    }

    public boolean validarTelefoneUnico(String telefone, List<Voluntario> voluntariosExistentes) {
        // RN14: Telefone deve ser único
        if (telefone == null || voluntariosExistentes == null) return true;

        String telefoneNormalizado = normalizarTelefone(telefone);
        
        return voluntariosExistentes.stream()
            .noneMatch(v -> {
                String telExistente = normalizarTelefone(v.getTelefone());
                return telefoneNormalizado.equals(telExistente);
            });
    }

    private String normalizarTelefone(String telefone) {
        // Remove todos os caracteres não numéricos
        return telefone.replaceAll("[^0-9]", "");
    }

    public boolean validarEspecialidadePermitida(String especialidade) {
        // RN15: Especialidades permitidas
        if (especialidade == null) return false;

        String esp = especialidade.toLowerCase();
        
        List<String> especialidadesPermitidas = List.of(
            "veterinário", "biólogo", "oceanógrafo", "tratador", 
            "educador", "administrativo", "limpeza", "manutenção", 
            "fotógrafo", "pesquisador", "estudante", "geral"
        );

        return especialidadesPermitidas.stream()
            .anyMatch(e -> esp.contains(e));
    }
}
