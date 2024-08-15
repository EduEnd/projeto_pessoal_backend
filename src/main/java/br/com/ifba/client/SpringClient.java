package br.com.ifba.client;




import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/users/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .build();

        String response = webClient.get()
                .uri("/findAll")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info(response);
    }

}