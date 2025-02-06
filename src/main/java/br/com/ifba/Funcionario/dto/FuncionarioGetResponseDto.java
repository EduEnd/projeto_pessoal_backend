package br.com.ifba.Funcionario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioGetResponseDto {

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "cargo")
    private String cargo;

    @JsonProperty(value = "salario")
    private double salario;
}
