package br.com.ifba.Client.service;

import br.com.ifba.Client.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ClientIService {

    // Retorna uma página de client com base na paginação fornecida
    Page<Client> findAll(Pageable pageable);

    // Retorna uma lista de Client filtradas pelo nome
    List<Client> findByName(String name);

    // Retorna um Client específica com base no ID
    Client findById(Long id);

    // Salva um novo Client
    Client save(Client client);

    // Atualiza um Client existente com novos dados
    void update(Client client);

    // Exclui um Client com base no ID e retorna um mapa com informações sobre a exclusão
    Map<String, String> delete(Long id);

}

