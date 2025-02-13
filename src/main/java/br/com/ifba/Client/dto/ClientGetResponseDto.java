package br.com.ifba.Client.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data  // Gera automaticamente getters, setters, equals, hashCode e toString
@AllArgsConstructor  // Gera um construtor com todos os argumentos
@NoArgsConstructor  // Gera um construtor sem argumentos
public class ClientGetResponseDto {

    @JsonProperty(value = "nome")  // Mapeia o campo 'name' para o nome JSON 'nome'
    private String name;

    @JsonProperty(value = "localidade")  // Mapeia o campo 'localidade' para o nome JSON 'localidade'
    private String endereco;

    @JsonProperty(value = "idade")  // Mapeia o campo 'idade' para o nome JSON 'idade'
    private int idade;

    @JsonProperty(value = "email") // Mapeia o campo 'email' para o nome JSON 'idade'
    private  String email;

    @JsonProperty(value = "nome de usuario") // Mapeia o campo 'user_name' para o nome JSON 'idade'
    private String user_name;
}


