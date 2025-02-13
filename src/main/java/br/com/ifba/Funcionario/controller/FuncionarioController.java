package br.com.ifba.Funcionario.controller;

import br.com.ifba.Client.dto.ClientGetResponseDto;
import br.com.ifba.Client.dto.ClientPostRequestDto;
import br.com.ifba.Client.entity.Client;
import br.com.ifba.Funcionario.dto.FuncionarioGetResponseDto;
import br.com.ifba.Funcionario.dto.FuncionarioPostRequestDto;
import br.com.ifba.Funcionario.entity.Funcionario;
import br.com.ifba.Funcionario.service.FuncionarioIService;
import br.com.ifba.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionario") // Define a URL base para os endpoints desta classe
@CrossOrigin("*") // Permite requisições de qualquer origem (CORS), essencial para integração frontend-backend
@RequiredArgsConstructor // Gera um construtor com injeção de dependência para os campos finais
public class FuncionarioController {

    // Injeção de dependência para o serviço e o utilitário de mapeamento de objetos
    private final FuncionarioIService funcionarioService;
    private final ObjectMapperUtil objectMapperUtil;

    // Método para buscar todos os funcionários com paginação
    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FuncionarioGetResponseDto>> findAll(Pageable pageable) {
        // Chama o serviço para buscar todos os funcionários e mapeia para o DTO de resposta
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.funcionarioService.findAll(pageable).map(f -> objectMapperUtil
                        .map(f, FuncionarioGetResponseDto.class)));
    }

    // Método para buscar funcionários pelo nome
    @GetMapping(path = "/findByName/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FuncionarioGetResponseDto>> findByNome(@PathVariable String nome) {
        // Chama o serviço para buscar os funcionários com o nome informado
        List<Funcionario> funcionarios = this.funcionarioService.findByNome(nome);
        // Mapeia os funcionários para DTOs de resposta
        List<FuncionarioGetResponseDto> responseDto = objectMapperUtil.mapAll(funcionarios, FuncionarioGetResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Método para associar um cliente a um funcionário
    @PostMapping(path = "/associarClient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuncionarioGetResponseDto> associarClient(@RequestBody Map<String, Long> requestBody) {
        // Extrai os IDs de funcionário e cliente do corpo da requisição
        Long funcionarioId = requestBody.get("funcionarioId");
        Long clientId = requestBody.get("clientId");
        // Chama o serviço para associar o cliente ao funcionário
        Funcionario funcionario = funcionarioService.associarClient(funcionarioId, clientId);
        // Mapeia o funcionário para o DTO de resposta
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.map(funcionario, FuncionarioGetResponseDto.class));
    }

    // Método para listar os clientes de um funcionário específico
    @GetMapping("/{funcionarioId}/clientes")
    public ResponseEntity<List<ClientGetResponseDto>> listarClientes(@PathVariable Long funcionarioId) {
        // Chama o serviço para buscar os clientes associados ao funcionário
        List<Client> clientes = funcionarioService.buscarClientesDoFuncionario(funcionarioId);
        // Mapeia os clientes para os DTOs de resposta
        List<ClientGetResponseDto> clientesDto = clientes.stream()
                .map(client -> objectMapperUtil.map(client, ClientGetResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientesDto);
    }

    // Método para buscar um funcionário pelo ID
    @GetMapping("/funcionario/{id}")
    public ResponseEntity<FuncionarioGetResponseDto> findById(@PathVariable Long id) {
        // Chama o serviço para buscar o funcionário pelo ID
        Funcionario funcionario = funcionarioService.findById(id);
        // Mapeia o funcionário para o DTO de resposta
        FuncionarioGetResponseDto responseDto = objectMapperUtil.map(funcionario, FuncionarioGetResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    // Método para salvar um novo funcionário
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuncionarioGetResponseDto> save(@RequestBody @Valid FuncionarioPostRequestDto funcionarioPostRequestDto) {
        // Mapeia o DTO para a entidade Funcionario
        Funcionario funcionario = objectMapperUtil.map(funcionarioPostRequestDto, Funcionario.class);
        // Chama o serviço para salvar o funcionário
        Funcionario savedFuncionario = funcionarioService.save(funcionario);
        // Mapeia o funcionário salvo para o DTO de resposta
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(savedFuncionario, FuncionarioGetResponseDto.class));
    }

    // Método para atualizar um funcionário existente
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid FuncionarioPostRequestDto funcionarioPostRequestDto) {
        // Mapeia o DTO para a entidade Funcionario
        Funcionario funcionario = objectMapperUtil.map(funcionarioPostRequestDto, Funcionario.class);
        // Chama o serviço para atualizar o funcionário
        funcionarioService.update(funcionario);
        return ResponseEntity.noContent().build(); // Retorna resposta sem conteúdo, indicando sucesso
    }

    // Método para excluir um funcionário pelo ID
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // Chama o serviço para excluir o funcionário pelo ID
        return ResponseEntity.status(HttpStatus.OK)
                .body(funcionarioService.delete(id));
    }
}
