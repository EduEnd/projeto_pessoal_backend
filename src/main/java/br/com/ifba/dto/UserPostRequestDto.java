package br.com.ifba.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostRequestDto {
    @JsonProperty(value = "nome")
    @NotNull( message = "O nome é obrigatório!")
    @NotBlank(message = "O nome não pode ser vazio!")
    private String name;

    @JsonProperty(value = "email")
    @Email(message = " Email invalido!")
    private String email;

    @JsonProperty(value = "user_name")
    @NotNull(message = "O login é obrigatório!")
    @NotBlank(message= "O login não pode ser vazio!")
    @Size(min = 3, max = 150, message = "O login precisa" +
            "ter pelo menos 3 caracteres e 30 no máximo!")
    private String user_name;

    @JsonProperty(value = "password")
    @NotNull(message = "O password é obrigatório!")
    @NotBlank(message= "O password não pode ser vazio!")
    private String password;
}
