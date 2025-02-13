package br.com.ifba.Main;




import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2 // Anotação do Lombok para habilitar o uso do Log4j2 no código, permitindo o log de informações de forma simples

public class SpringClient {
    public static void main(String[] args) {

        // Criação de um cliente WebClient para fazer requisições HTTP
        WebClient webClient = WebClient.builder() // Utiliza o padrão Builder para configurar o WebClient
                .baseUrl("http://localhost:8080/users/") // Define a URL base para as requisições
                .defaultHeader(HttpHeaders.CONTENT_TYPE, // Configura o cabeçalho 'Content-Type' para indicar que o conteúdo enviado e recebido será em formato JSON
                        MediaType.APPLICATION_JSON_VALUE) // Especifica o tipo de mídia JSON
                .build(); // Constrói a instância do WebClient com as configurações acima

        // Fazendo uma requisição GET para o endpoint "/findAll"
        String response = webClient.get() // Inicia uma requisição GET
                .uri("/findAll") // Especifica o URI do endpoint a ser acessado
                .retrieve() // Executa a requisição
                .bodyToMono(String.class) // Define que o corpo da resposta será convertido para um tipo String
                .block(); // Bloqueia a execução até que a resposta seja recebida (espera a resposta sincrona)

        log.info(response); // Usa o log para registrar a resposta recebida da API
    }
}
