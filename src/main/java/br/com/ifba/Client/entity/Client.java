package br.com.ifba.Client.entity;



import br.com.ifba.Funcionario.entity.Funcionario;
import br.com.ifba.User.entity.User;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;


@Entity  // Indica que esta classe é uma entidade JPA mapeada para uma tabela no banco de dados
@Data  // Gera automaticamente getters, setters, equals, hashCode e toString
@Builder  // Permite o uso do padrão de projeto Builder para criar instâncias da classe
@Table(name="Client")  // Define o nome da tabela correspondente a esta entidade no banco de dados
@AllArgsConstructor  // Gera um construtor com todos os argumentos
@NoArgsConstructor  // Gera um construtor sem argumentos
@EqualsAndHashCode(callSuper = false)  // Define como o método equals e hashCode serão gerados, sem incluir a superclasse
public class Client  extends PersistenceEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false, unique = true)
    private Funcionario funcionario;


}


