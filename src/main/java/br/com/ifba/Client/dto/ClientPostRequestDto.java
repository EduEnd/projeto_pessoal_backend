package br.com.ifba.Client.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data  // Gera automaticamente getters, setters, equals, hashCode e toString
@AllArgsConstructor  // Gera um construtor com todos os argumentos
@NoArgsConstructor  // Gera um construtor sem argumentos
public class ClientPostRequestDto {

    @JsonProperty(value = "nome")  // Mapeia o campo 'name' para o nome JSON 'nome'
    @NotNull(message = "O nome é obrigatório!")  // Validação: o campo não pode ser nulo
    @NotBlank(message = "O nome não pode ser vazio!")  // Validação: o campo não pode ser vazio ou conter apenas espaços
    private String name;

    @JsonProperty(value = "localidade")  // Mapeia o campo 'localidade' para o nome JSON 'localidade'
    @NotNull(message = "A localidade é obrigatória!")  // Validação: o campo não pode ser nulo
    @NotBlank(message = "A localidade não pode ser vazia!")  // Validação: o campo não pode ser vazio
    @Size(min = 5, max = 150, message = "A localidade precisa ter pelo menos 5 caracteres e no máximo 150!")  // Validação: tamanho mínimo e máximo da string
    private String localidade;

    @JsonProperty(value = "idade")  // Mapeia o campo 'idade' para o nome JSON 'idade'
    @NotNull(message = "A idade é obrigatória!")  // Validação: o campo não pode ser nulo
    private int idade;
}


