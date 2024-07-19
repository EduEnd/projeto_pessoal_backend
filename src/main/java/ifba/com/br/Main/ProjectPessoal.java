package ifba.com.br.Main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectPessoal {
    public static void main(String[] args) {
        SpringApplication.run(ProjectPessoal.class,args);
    }
    @Bean
    public CommandLineRunner initialization() {
        return args -> System.out.println("Hello world");
    }

}

