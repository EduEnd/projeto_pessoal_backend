package br.com.ifba.Avaliacao.service;

// Importação das classes necessárias
import br.com.ifba.Avaliacao.entity.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface de serviço para a entidade Avaliacao.
 * Define os métodos que serão implementados na camada de serviço.
 */
public interface AvaliacaoIService {

    /**
     * Atualiza uma avaliação existente no banco de dados.
     *
     * @param avaliacao Objeto Avaliacao atualizado que será salvo.
     */
    void update(Avaliacao avaliacao);

    /**
     * Retorna uma página de avaliações, permitindo paginação.
     *
     * @param pageable Objeto de paginação que define o tamanho da página e a ordenação.
     * @return Página contendo as avaliações encontradas.
     */
    Page<Avaliacao> findAll(Pageable pageable);

    /**
     * Cria uma nova avaliação para um funcionário com base no ID do cliente e do funcionário.
     *
     * @param clientId      ID do cliente que está realizando a avaliação.
     * @param funcionarioId ID do funcionário que está sendo avaliado.
     * @param comentario    Comentário sobre o funcionário.
     * @param nota          Nota atribuída ao funcionário.
     * @return A entidade Avaliacao recém-criada.
     */
    Avaliacao avaliarFuncionario(Long clientId, Long funcionarioId, String comentario, int nota);
}
