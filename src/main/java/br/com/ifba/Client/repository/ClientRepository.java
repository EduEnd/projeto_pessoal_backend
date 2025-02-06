package br.com.ifba.Client.repository;


import br.com.ifba.Client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Long> {

    // Método para buscar uma lista de clientes por nome
        List<Client> findByName(String name);

}





