package br.com.ifba.Funcionario.repository;

// Importações necessárias
import br.com.ifba.Client.entity.Client; // A classe Client, que será relacionada com Funcionario
import br.com.ifba.Funcionario.entity.Funcionario; // A classe Funcionario, que será manipulada pelo repositório
import org.springframework.data.jpa.repository.JpaRepository; // Interface JPA para operação de persistência

import java.util.List; // Utilizado para manipular listas de objetos

// A interface FuncionarioRepository estende JpaRepository, que proporciona métodos de persistência para a entidade Funcionario
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // Método para buscar funcionários pelo nome. O Spring Data JPA gera a implementação automaticamente.
    List<Funcionario> findByNome(String nome);

    // Método para associar um Client a um Funcionario. O comportamento não é automático no JPA, então pode exigir uma implementação customizada.
    Funcionario associarClient(Long funcionarioId, Long clientId);

    // Método para buscar todos os clientes associados a um funcionário específico.
    List<Client> buscarClientesDoFuncionario(Long funcionarioId);
}
