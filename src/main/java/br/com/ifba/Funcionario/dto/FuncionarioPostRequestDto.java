package br.com.ifba.Funcionario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioPostRequestDto {

    @JsonProperty(value = "nome")
    @NotNull(message = "O nome é obrigatório!")
    @NotBlank(message = "O nome não pode ser vazio!")
    private String nome;

    @JsonProperty(value = "cargo")
    @NotNull(message = "O cargo é obrigatório!")
    @NotBlank(message = "O cargo não pode ser vazio!")
    private String cargo;

    @JsonProperty(value = "salario")
    @NotNull(message = "O salário é obrigatório!")
    private double salario;
}
