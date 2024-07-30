package br.com.ifba.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetResponseDto {
    @JsonProperty(value = "nome")
    private String name;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "user_name")
    private String user_name;
}
