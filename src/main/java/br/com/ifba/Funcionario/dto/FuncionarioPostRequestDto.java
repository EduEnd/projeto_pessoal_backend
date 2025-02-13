package br.com.ifba.Funcionario.dto;

// Importações necessárias
import com.fasterxml.jackson.annotation.JsonProperty; // Anotação para mapeamento de JSON
import jakarta.validation.constraints.NotBlank; // Validação para garantir que o campo não esteja vazio
import jakarta.validation.constraints.NotNull; // Validação para garantir que o campo não seja nulo
import jakarta.validation.constraints.Size; // Validação para definir o tamanho mínimo e máximo de uma string
import lombok.AllArgsConstructor; // Gera o construtor com todos os parâmetros
import lombok.Data; // Gera automaticamente os métodos getters, setters, equals, hashCode e toString
import lombok.NoArgsConstructor; // Gera o construtor sem parâmetros

@Data // Anotação do Lombok que gera automaticamente os métodos getters, setters, equals, hashCode e toString
@AllArgsConstructor // Gera o construtor com todos os campos como parâmetros
@NoArgsConstructor // Gera o construtor sem parâmetros
public class FuncionarioPostRequestDto {

    // A anotação @JsonProperty permite personalizar o nome do campo JSON que será utilizado no JSON final
    @JsonProperty(value = "nome")
    @NotNull(message = "O nome é obrigatório!") // Validação para garantir que o nome não seja nulo
    @NotBlank(message = "O nome não pode ser vazio!") // Validação para garantir que o nome não seja vazio
    private String nome; // Atributo que armazena o nome do funcionário

    @JsonProperty(value = "cargo")
    @NotNull(message = "O cargo é obrigatório!") // Validação para garantir que o cargo não seja nulo
    @NotBlank(message = "O cargo não pode ser vazio!") // Validação para garantir que o cargo não seja vazio
    private String cargo; // Atributo que armazena o cargo do funcionário

    @JsonProperty(value = "salario")
    @NotNull(message = "O salário é obrigatório!") // Validação para garantir que o salário não seja nulo
    private double salario; // Atributo que armazena o salário do funcionário

    @JsonProperty(value = "email")
    @NotNull(message = "O email é obrigatorio!") // Validação para garantir que o email não seja nulo
    @NotBlank(message = "O email não pode ser vazio!") // Validação para garantir que o email não seja vazio
    private String email; // Atributo que armazena o email do funcionário

    @JsonProperty(value = "idade")
    @NotNull(message = "A idade é obrigatoria!") // Validação para garantir que a idade não seja nula
    @NotBlank(message = "A idade não pode ser vazia!") // Validação para garantir que a idade não seja vazia
    private int idade; // Atributo que armazena a idade do funcionário

    @JsonProperty(value = "endereco")
    @NotNull(message = "Endereco é obrigatorio!") // Validação para garantir que o endereço não seja nulo
    @NotBlank(message = "Endereco não pode ser vazio") // Validação para garantir que o endereço não seja vazio
    @Size(min = 5, max = 150, message = "O endereco precisa ter pelo menos 5 caracteres e no máximo 150!") // Validação de tamanho do endereço
    private String endereco; // Atributo que armazena o endereço do funcionário
}
