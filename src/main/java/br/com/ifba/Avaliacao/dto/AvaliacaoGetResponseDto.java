package br.com.ifba.Avaliacao.dto;

// Importações das anotações necessárias
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) utilizado para representar a resposta da API ao buscar uma avaliação.
 * Contém apenas os campos necessários para exibição no frontend ou retorno ao cliente.
 */
@Data // Gera automaticamente os métodos getters, setters, toString, equals e hashCode
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class AvaliacaoGetResponseDto {

    @JsonProperty(value = "comentario") // Define o nome da propriedade no JSON de resposta
    private String comentario; // Comentário da avaliação

    @JsonProperty(value = "nota") // Define o nome da propriedade no JSON de resposta
    private int nota; // Nota atribuída na avaliação
}
