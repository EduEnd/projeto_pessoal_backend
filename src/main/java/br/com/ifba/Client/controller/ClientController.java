package br.com.ifba.Client.controller;

// Importações necessárias para funcionamento do controlador e dependências
import br.com.ifba.Client.dto.ClientGetResponseDto;  // DTO para respostas de cliente
import br.com.ifba.Client.dto.ClientPostRequestDto;  // DTO para requisições de cliente
import br.com.ifba.Client.entity.Client;  // Modelo da entidade Cliente
import br.com.ifba.Client.service.ClientIService;  // Interface do serviço de cliente
import br.com.ifba.infrastructure.mapper.ObjectMapperUtil;  // Utilitário para mapeamento de objetos
import jakarta.validation.Valid;  // Validação de entrada
import lombok.RequiredArgsConstructor;  // Geração automática de construtores
import org.springframework.data.domain.Page;  // Suporte para paginação
import org.springframework.data.domain.Pageable;  // Objeto de paginação
import org.springframework.http.HttpStatus;  // Status HTTP
import org.springframework.http.MediaType;  // Tipos de mídia (JSON, XML, etc.)
import org.springframework.http.ResponseEntity;  // Classe para respostas HTTP
import org.springframework.web.bind.annotation.*;  // Anotações para controle REST

import java.util.List;  // Classe para trabalhar com listas

// Define que esta classe é um controlador REST
@RestController
// Mapeia a rota base para todos os endpoints deste controlador
@RequestMapping("/evaluation")
// Permite requisições de qualquer origem (Configuração de CORS)
@CrossOrigin("*")
// Gera automaticamente o construtor para dependências declaradas como finais
@RequiredArgsConstructor
public class ClientController {

    // Dependência para o serviço que gerencia operações com clientes
    private final ClientIService clientService;

    // Dependência para utilitário de mapeamento de objetos
    private final ObjectMapperUtil objectMapperUtil;

    // Endpoint para buscar todos os clientes de forma paginada
    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ClientGetResponseDto>> findAll(Pageable pageable) {
        // Busca todos os clientes e os mapeia para DTOs usando paginação
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.clientService.findAll(pageable).map(c -> objectMapperUtil
                        .map(c, ClientGetResponseDto.class)));
    }

    // Endpoint para buscar clientes pelo nome
    @GetMapping(path = "/findByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClientGetResponseDto>> findByName(@PathVariable String name) {
        // Busca clientes pelo nome
        List<Client> Clients = this.clientService.findByName(name);
        // Converte a lista de entidades para uma lista de DTOs
        List<ClientGetResponseDto> responseDto = objectMapperUtil.mapAll(Clients, ClientGetResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Endpoint para buscar um cliente pelo ID
    @GetMapping("/evaluation/{id}")
    public ResponseEntity<ClientGetResponseDto> findById(@PathVariable Long id) {
        // Busca um cliente pelo ID
        Client client = clientService.findById(id);
        // Converte a entidade encontrada para um DTO
        ClientGetResponseDto responseDto = objectMapperUtil.map(client, ClientGetResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    // Endpoint para salvar um novo cliente
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientGetResponseDto> save(@RequestBody @Valid ClientPostRequestDto evaluationPostRequestDto) {
        // Converte o DTO recebido para uma entidade
        Client client = objectMapperUtil.map(evaluationPostRequestDto, Client.class);

        // Salva a entidade no banco de dados
        Client savedClient = clientService.save(client);

        // Retorna a entidade salva convertida para DTO
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(savedClient, ClientGetResponseDto.class));
    }

    // Endpoint para atualizar um cliente existente
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid ClientPostRequestDto evaluationPostRequestDto) {
        // Converte o DTO para uma entidade
        Client client = objectMapperUtil.map(evaluationPostRequestDto, Client.class);

        // Atualiza a entidade no banco de dados
        clientService.update(client);

        // Retorna uma resposta sem conteúdo indicando sucesso
        return ResponseEntity.noContent().build();
    }

    // Endpoint para deletar um cliente pelo ID
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // Deleta a entidade pelo ID e retorna a resposta
        return ResponseEntity.status(HttpStatus.OK)
                .body(clientService.delete(id));
    }
}
