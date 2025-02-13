package br.com.ifba.Avaliacao.service;

// Importações das classes necessárias
import br.com.ifba.Avaliacao.entity.Avaliacao;
import br.com.ifba.Avaliacao.repository.AvaliacaoRepository;
import br.com.ifba.Client.entity.Client;
import br.com.ifba.Client.repository.ClientRepository;
import br.com.ifba.Funcionario.entity.Funcionario;
import br.com.ifba.Funcionario.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviço para a entidade Avaliacao.
 * Implementa os métodos definidos na interface AvaliacaoIService.
 */
@Service  // Indica que essa classe é um componente de serviço gerenciado pelo Spring.
@RequiredArgsConstructor  // Gera automaticamente um construtor com os atributos marcados como "final".
public class AvaliacaoService implements AvaliacaoIService {

    // Dependências injetadas automaticamente pelo Lombok (@RequiredArgsConstructor)
    private final AvaliacaoRepository avaliacaoRepository;
    private final ClientRepository clientRepository;
    private final FuncionarioRepository funcionarioRepository;

    /*
    Método comentado: poderia ser usado para salvar uma avaliação sem verificar cliente e funcionário.

    @Transactional
    public Avaliacao save(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }
    */

    /**
     * Atualiza uma avaliação existente.
     *
     * @param avaliacao Objeto Avaliacao com os novos dados a serem atualizados.
     */
    @Transactional  // Garante que a operação seja atômica (ou tudo ocorre, ou nada ocorre).
    public void update(Avaliacao avaliacao) {
        avaliacaoRepository.save(avaliacao);
    }

    /**
     * Busca todas as avaliações com suporte a paginação.
     *
     * @param pageable Objeto que define a paginação e ordenação dos resultados.
     * @return Página contendo as avaliações encontradas.
     */
    public Page<Avaliacao> findAll(Pageable pageable) {
        return avaliacaoRepository.findAll(pageable);
    }

    /**
     * Cria e salva uma nova avaliação para um funcionário feita por um cliente.
     *
     * @param clientId      ID do cliente que está avaliando.
     * @param funcionarioId ID do funcionário que está sendo avaliado.
     * @param comentario    Comentário da avaliação.
     * @param nota          Nota atribuída ao funcionário (deve estar no intervalo válido).
     * @return A entidade Avaliacao salva no banco de dados.
     */
    @Transactional  // Garante consistência da transação ao salvar a avaliação.
    public Avaliacao avaliarFuncionario(Long clientId, Long funcionarioId, String comentario, int nota) {
        // Busca o cliente no banco de dados. Se não existir, lança uma exceção.
        Client cliente = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Busca o funcionário no banco de dados. Se não existir, lança uma exceção.
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // Cria uma nova instância de Avaliacao e preenche os campos.
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setCliente(cliente);
        avaliacao.setFuncionario(funcionario);
        avaliacao.setComentario(comentario);
        avaliacao.setNota(nota);

        // Mensagem de log simples (pode ser substituída por um Logger).
        System.out.println("Avaliacao salva com sucesso!!");

        // Salva a avaliação no banco de dados e retorna a entidade salva.
        return avaliacaoRepository.save(avaliacao);
    }
}
