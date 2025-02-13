package br.com.ifba.Avaliacao.dto;

// Importações das anotações necessárias
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) utilizado para receber os dados de uma avaliação no momento da criação ou atualização.
 * Contém validações para garantir a integridade dos dados enviados pelo cliente.
 */
@Data // Gera automaticamente getters, setters, equals, hashCode e toString
@AllArgsConstructor // Gera um construtor com todos os argumentos
@NoArgsConstructor // Gera um construtor sem argumentos
public class AvaliacaoPostRequestDto {

    /**
     * Campo que representa o comentário da avaliação.
     * - É serializado/deserializado com o nome "comentario" no JSON.
     * - Deve ter no máximo 200 caracteres.
     */
    @JsonProperty(value = "comentario")
    @Size(min = 0, max = 200, message = "Máximo de caracteres é 200")
    private String comentario;

    /**
     * Campo que representa a nota atribuída na avaliação.
     * - É serializado/deserializado com o nome "nota" no JSON.
     * - Deve ter um valor mínimo de 1 e máximo de 5.
     */
    @JsonProperty(value = "nota")
    @Min(value = 1, message = "A nota deve ser no mínimo 1") // Garante que a nota seja pelo menos 1
    @Max(value = 5, message = "A nota deve ser no máximo 5") // Garante que a nota não ultrapasse 5
    private int nota;
}
