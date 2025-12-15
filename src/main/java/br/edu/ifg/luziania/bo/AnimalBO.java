package br.edu.ifg.luziania.bo;

import br.edu.ifg.luziania.dto.AnimalDTO;
import br.edu.ifg.luziania.entity.Animal;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AnimalBO {

    public List<String> validarRegrasNegocio(AnimalDTO dto) {
        List<String> erros = new ArrayList<>();

        // RN1: Nome do animal deve ter pelo menos 2 caracteres
        if (dto.getNome() != null && dto.getNome().trim().length() < 2) {
            erros.add("Nome do animal deve ter pelo menos 2 caracteres");
        }

        // RN2: Espécie deve ter pelo menos 3 caracteres
        if (dto.getEspecie() != null && dto.getEspecie().trim().length() < 3) {
            erros.add("Espécie deve ter pelo menos 3 caracteres");
        }

        // RN3: Idade não pode ser negativa e não pode ser maior que 200 anos
        if (dto.getIdade() != null && (dto.getIdade() < 0 || dto.getIdade() > 200)) {
            erros.add("Idade deve estar entre 0 e 200 anos");
        }

        // RN4: Status deve ser válido
        if (dto.getStatus() != null) {
            String status = dto.getStatus().toUpperCase();
            if (!status.equals("ATIVO") && !status.equals("EM TRATAMENTO") && 
                !status.equals("RECUPERADO") && !status.equals("FALECIDO")) {
                erros.add("Status deve ser: ATIVO, EM TRATAMENTO, RECUPERADO ou FALECIDO");
            }
        }

        // RN5: Habitat deve ser preenchido
        if (dto.getHabitat() == null || dto.getHabitat().trim().isEmpty()) {
            erros.add("Habitat é obrigatório");
        }

        return erros;
    }

    public boolean validarAnimalAtivo(Animal animal) {
        return animal.getStatus() != null && 
               (animal.getStatus().equalsIgnoreCase("ATIVO") || 
                animal.getStatus().equalsIgnoreCase("EM TRATAMENTO"));
    }

    public boolean podeReceberVacina(Animal animal) {
        // RN6: Apenas animais ativos ou em tratamento podem receber vacinas
        return validarAnimalAtivo(animal);
    }

    public boolean podeReceberConsulta(Animal animal) {
        // RN7: Apenas animais que não sejam falecidos podem receber consultas
        return animal.getStatus() != null && 
               !animal.getStatus().equalsIgnoreCase("FALECIDO");
    }

    public String normalizarStatus(String status) {
        if (status == null) return "ATIVO";
        
        String statusUpper = status.toUpperCase().trim();
        
        // RN8: Normalização de status
        switch (statusUpper) {
            case "ATIVO":
            case "OK":
            case "BOM":
                return "ATIVO";
            case "EM TRATAMENTO":
            case "TRATAMENTO":
            case "DOENTE":
                return "EM TRATAMENTO";
            case "RECUPERADO":
            case "CURADO":
                return "RECUPERADO";
            case "FALECIDO":
            case "MORTO":
            case "ÓBITO":
                return "FALECIDO";
            default:
                return "ATIVO";
        }
    }

    public boolean validarIdadeCoerente(String especie, Integer idade) {
        if (idade == null || especie == null) return true;

        // RN9: Validação de idade por espécie (exemplos)
        especie = especie.toLowerCase();
        
        if (especie.contains("tartaruga") && idade > 150) {
            return false;
        }
        if (especie.contains("golfinho") && idade > 50) {
            return false;
        }
        if (especie.contains("peixe") && idade > 30) {
            return false;
        }
        if (especie.contains("baleia") && idade > 100) {
            return false;
        }
        
        return true;
    }
}
