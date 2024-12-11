package br.com.ifba.Client.service;


import br.com.ifba.Client.entity.Client;
import br.com.ifba.Client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientIService {

    private final ClientRepository ClientRepository; // Repositório para persistência de cliente

    /**
     * Obtém todos os clientes com paginação.
     * @param pageable Informações de paginação
     * @return Uma página de clientes
     */
    public Page<Client> findAll(Pageable pageable) {
        return ClientRepository.findAll(pageable);
    }

    /**
     * Busca avaliações pelo nome.
     * @param name Nome do clientes
     * @return Lista de clientes com o nome especificado
     */
    public List<Client> findByName(String name) {
        return ClientRepository.findByName(name);
    }

    /**
     * Busca um cliente pelo ID.
     * @param id ID da cliente
     * @return cliente correspondente ao ID
     * @throws RuntimeException Se a cliente não for encontrada
     */
    public Client findById(Long id) {
        return ClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado!"));
    }

    /**
     * Salva um novo cliente.
     * @param client Dados da cliente a ser salva
     */
    @Transactional
    public Client save(Client client) {

        // Salva o cliente e retorna a instância salva
        return ClientRepository.save(client);
    }

    // Atualiza um cliente existente. Transacional garante atomicidade.
    @Transactional
    public void update(Client client) {
        // Salva um cliente atualizada
        ClientRepository.save(client);
    }

    /**
     * Exclui um cliente pelo ID.
     * @param id ID da cliente a ser excluída
     * @return Mensagem de sucesso
     */
    public Map<String, String> delete(Long id) {
        ClientRepository.deleteById(id); // Exclui a avaliação pelo ID
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cliente deletado com sucesso"); // Mensagem de sucesso
        return response;
    }
}




