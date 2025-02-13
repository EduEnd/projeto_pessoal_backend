package br.com.ifba.Funcionario.entity;

// Importações necessárias
import br.com.ifba.Client.entity.Client; // A classe Client, que será relacionada com Funcionario
import br.com.ifba.User.entity.User; // A classe User, que será associada ao Funcionario
import br.com.ifba.infrastructure.entity.PersistenceEntity; // A classe base que proporciona funcionalidade de persistência
import jakarta.persistence.*; // Anotações do JPA para mapeamento ORM
import lombok.*; // Anotações do Lombok para geração automática de código (getters, setters, etc.)

import java.io.Serializable; // Interface de serialização necessária para entidades JPA
import java.util.ArrayList; // Classe utilitária para listas
import java.util.List; // Interface de lista para armazenar múltiplos objetos Client

@Entity // Anotação JPA que marca a classe como uma entidade que será mapeada para uma tabela no banco de dados
@Data // Anotação do Lombok que gera automaticamente os métodos getters, setters, equals, hashCode e toString
@Builder // Anotação do Lombok que permite o uso do padrão de projeto Builder para criar objetos dessa classe
@Table(name="Funcionario") // Define o nome da tabela que será mapeada para esta entidade
@AllArgsConstructor // Gera o construtor com todos os campos como parâmetros
@NoArgsConstructor // Gera o construtor sem parâmetros
@EqualsAndHashCode(callSuper = false) // Gera os métodos equals e hashCode, desconsiderando a classe pai (PersistenceEntity)
public class Funcionario extends PersistenceEntity implements Serializable {

    // Relacionamento com a entidade User: Um Funcionario está associado a um único User
    @ManyToOne // Relacionamento muitos-para-um com a entidade User
    @JoinColumn(name = "user_id", nullable = false, unique = true) // Define a coluna user_id como chave estrangeira, não nula e única
    private User user; // Atributo que armazena o usuário associado ao funcionário

    // Coluna que armazena o cargo do funcionário
    @Column(name = "cargo", nullable = false) // Define a coluna "cargo", que não pode ser nula
    private String cargo; // Atributo que armazena o cargo do funcionário

    // Coluna que armazena o salário do funcionário
    @Column(name = "salario", nullable = false) // Define a coluna "salario", que não pode ser nula
    private double salario; // Atributo que armazena o salário do funcionário

    // Relacionamento com a entidade Client: Um Funcionario pode ter múltiplos Clients
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL) // Relacionamento um-para-muitos com a entidade Client
    private List<Client> clients = new ArrayList<>(); // Atributo que armazena a lista de clientes associados ao funcionário
}
