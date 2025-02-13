package br.com.ifba.Funcionario.service;

// Importações necessárias
import br.com.ifba.Client.entity.Client; // A classe Client, que será manipulada em alguns métodos
import br.com.ifba.Funcionario.entity.Funcionario; // A classe Funcionario, que é o principal objeto manipulado na interface
import org.springframework.data.domain.Page; // Para manipulação de resultados paginados
import org.springframework.data.domain.Pageable; // Para fornecer a estrutura de paginação

import java.util.List; // Usado para listas de objetos
import java.util.Map; // Usado para retorno de um mapa com mensagens, por exemplo

// Interface de serviço para operações relacionadas ao Funcionario
public interface FuncionarioIService {

    // Método para buscar todos os funcionários, retornando uma página de resultados com base na paginação fornecida.
    // A paginação é fornecida pelo parâmetro 'Pageable'.
    Page<Funcionario> findAll(Pageable pageable);

    // Método para buscar funcionários pelo nome. Retorna uma lista de funcionários com o nome correspondente.
    List<Funcionario> findByNome(String nome);

    // Método para buscar um funcionário específico pelo seu ID. Retorna um único funcionário.
    Funcionario findById(Long id);

    // Método para salvar um novo funcionário no banco de dados. Retorna o funcionário salvo.
    Funcionario save(Funcionario funcionario);

    // Método para atualizar um funcionário existente. A implementação atualiza os dados do funcionário no banco de dados.
    void update(Funcionario funcionario);

    // Método para excluir um funcionário pelo seu ID. Retorna um mapa com uma mensagem sobre o status da exclusão.
    Map<String, String> delete(Long id);

    // Método para associar um cliente a um funcionário. O método requer os IDs do funcionário e do cliente.
    // Retorna o funcionário com a associação feita.
    Funcionario associarClient(Long funcionarioId, Long clientId);

    // Método para buscar todos os clientes associados a um funcionário específico. Retorna uma lista de clientes.
    List<Client> buscarClientesDoFuncionario(Long funcionarioId);
}
