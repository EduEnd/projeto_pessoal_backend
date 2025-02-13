package br.com.ifba.Funcionario.dto;

// Importações necessárias
import com.fasterxml.jackson.annotation.JsonProperty; // Anotação para mapeamento de JSON
import lombok.AllArgsConstructor; // Gera o construtor com todos os argumentos
import lombok.Data; // Gera automaticamente os métodos getters, setters, equals, hashCode e toString
import lombok.NoArgsConstructor; // Gera o construtor sem argumentos

@Data // Anotação do Lombok que gera automaticamente métodos como getter, setter, equals, hashCode e toString
@AllArgsConstructor // Gera o construtor com todos os campos como parâmetros
@NoArgsConstructor // Gera o construtor sem parâmetros
public class FuncionarioGetResponseDto {

    // A anotação @JsonProperty permite personalizar o nome do campo JSON que será utilizado no JSON final
    @JsonProperty(value = "nome")
    private String nome; // Atributo que armazena o nome do funcionário

    @JsonProperty(value = "cargo")
    private String cargo; // Atributo que armazena o cargo do funcionário

    @JsonProperty(value = "salario")
    private double salario; // Atributo que armazena o salário do funcionário

    @JsonProperty(value = "email")
    private String email; // Atributo que armazena o e-mail do funcionário

    @JsonProperty(value = "idade")
    private int idade; // Atributo que armazena a idade do funcionário

    @JsonProperty(value = "endereco")
    private String endereco; // Atributo que armazena o endereço do funcionário
}
