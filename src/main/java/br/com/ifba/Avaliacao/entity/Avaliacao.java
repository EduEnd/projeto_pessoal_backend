package br.com.ifba.Avaliacao.entity;

// Importação das classes necessárias
import br.com.ifba.Client.entity.Client;
import br.com.ifba.Funcionario.entity.Funcionario;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que representa uma avaliação feita por um cliente sobre um funcionário.
 * Essa entidade é armazenada no banco de dados na tabela "avaliacao_funcionario".
 */
@Entity
@Table(name = "avaliacao_funcionario")
@Getter // Gera automaticamente os métodos getters para todos os campos
@Setter // Gera automaticamente os métodos setters para todos os campos
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class Avaliacao {

    /**
     * Identificador único da avaliação.
     * - Gerado automaticamente pelo banco de dados com a estratégia IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cliente que fez a avaliação.
     * - Relacionamento ManyToOne: Muitos registros de avaliação podem estar associados a um único cliente.
     * - JoinColumn define o nome da chave estrangeira "cliente_id" na tabela.
     * - nullable = false indica que esse campo é obrigatório.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Client cliente;

    /**
     * Funcionário que foi avaliado.
     * - Relacionamento ManyToOne: Muitos registros de avaliação podem estar associados a um único funcionário.
     * - JoinColumn define o nome da chave estrangeira "funcionario_id" na tabela.
     * - nullable = false indica que esse campo é obrigatório.
     */
    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    /**
     * Comentário do cliente sobre o funcionário.
     * - Esse campo é obrigatório (nullable = false).
     */
    @Column(nullable = false)
    private String comentario;

    /**
     * Nota atribuída ao funcionário pelo cliente.
     * - Esse campo é obrigatório (nullable = false).
     */
    @Column(nullable = false)
    private int nota;
}
