package br.com.ifba.Funcionario.service;

// Importações necessárias para o funcionamento do serviço
import br.com.ifba.Client.entity.Client; // A classe Client, que é manipulada em alguns métodos
import br.com.ifba.Client.repository.ClientRepository; // Repositório para acessar dados dos clientes
import br.com.ifba.Funcionario.entity.Funcionario; // A classe Funcionario, manipulada nesta classe de serviço
import br.com.ifba.Funcionario.repository.FuncionarioRepository; // Repositório para acessar dados dos funcionários
import lombok.RequiredArgsConstructor; // Anotação do Lombok que cria o construtor com os parâmetros finais
import org.springframework.data.domain.Page; // Para operações com dados paginados
import org.springframework.data.domain.Pageable; // Para fornecer informações sobre a paginação
import org.springframework.stereotype.Service; // Anotação que marca a classe como um serviço do Spring
import org.springframework.transaction.annotation.Transactional; // Para indicar que os métodos devem ser executados em uma transação

import java.util.HashMap; // Usado para criar mapas de resposta
import java.util.List; // Para trabalhar com listas de objetos
import java.util.Map; // Para trabalhar com mapas de dados

// Definindo o serviço de funcionário com as operações que interagem com a entidade Funcionario
@Service // Marca a classe como um serviço do Spring
@RequiredArgsConstructor // Gera um construtor com os campos finais, fornecendo injeção de dependência
public class FuncionarioService implements FuncionarioIService {

    // Declaração das dependências de repositórios
    private final FuncionarioRepository funcionarioRepository; // Repositório para operações com a entidade Funcionario
    private final ClientRepository clientRepository; // Repositório para operações com a entidade Client

    // Método para buscar todos os funcionários com suporte à paginação
    @Override
    public Page<Funcionario> findAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable); // Retorna uma página de funcionários com base nas configurações de página
    }

    // Método para buscar funcionários pelo nome
    @Override
    public List<Funcionario> findByNome(String nome) {
        return funcionarioRepository.findByNome(nome); // Retorna uma lista de funcionários com o nome correspondente
    }

    // Método para buscar um único funcionário pelo ID
    @Override
    public Funcionario findById(Long id) {
        // Caso o funcionário não seja encontrado, lança uma exceção
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado!"));
    }

    // Método para salvar um novo funcionário ou atualizar um existente, em uma transação
    @Override
    @Transactional
    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario); // Persiste o funcionário no banco de dados
    }

    // Método para atualizar um funcionário existente
    @Override
    @Transactional
    public void update(Funcionario funcionario) {
        funcionarioRepository.save(funcionario); // Atualiza o funcionário no banco de dados
    }

    // Método para deletar um funcionário com base no ID
    @Override
    public Map<String, String> delete(Long id) {
        funcionarioRepository.deleteById(id); // Deleta o funcionário do banco de dados
        Map<String, String> response = new HashMap<>(); // Cria um mapa para retornar uma mensagem
        response.put("message", "Funcionário deletado com sucesso"); // Adiciona a mensagem de sucesso
        return response;
    }

    // Método para associar um cliente a um funcionário
    @Override
    @Transactional
    public Funcionario associarClient(Long funcionarioId, Long clientId) {
        // Busca o funcionário pelo ID
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // Busca o cliente pelo ID
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Adiciona o cliente à lista de clientes do funcionário
        funcionario.getClients().add(client);

        // Persiste a alteração no banco de dados
        funcionarioRepository.save(funcionario);

        return funcionario; // Retorna o funcionário com a nova associação
    }

    // Método para buscar todos os clientes associados a um funcionário específico
    @Override
    public List<Client> buscarClientesDoFuncionario(Long funcionarioId) {
        // Busca o funcionário pelo ID
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        // Retorna a lista de clientes associados ao funcionário
        return funcionario.getClients();
    }
}
