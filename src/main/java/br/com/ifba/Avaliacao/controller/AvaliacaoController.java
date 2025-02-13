package br.com.ifba.Avaliacao.controller;

// Importações das classes necessárias
import br.com.ifba.Avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.Avaliacao.dto.AvaliacaoPostRequestDto;
import br.com.ifba.Avaliacao.entity.Avaliacao;
import br.com.ifba.Avaliacao.service.AvaliacaoService;
import br.com.ifba.Client.dto.ClientGetResponseDto;
import br.com.ifba.Client.dto.ClientPostRequestDto;
import br.com.ifba.Client.entity.Client;
import br.com.ifba.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping // Define o mapeamento base para os endpoints deste controlador
@CrossOrigin // Permite requisições de diferentes origens (CORS)
@RequiredArgsConstructor // Gera um construtor com argumentos para os atributos finais (final)
public class AvaliacaoController {

    // Injeção de dependências
    private final AvaliacaoService avaliacaoService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Endpoint para buscar todas as avaliações paginadas.
     * @param pageable Parâmetro para paginação.
     * @return Uma página contendo os dados das avaliações no formato DTO.
     */
    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AvaliacaoGetResponseDto>> findAll(Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.avaliacaoService.findAll(pageable).map(c -> objectMapperUtil
                        .map(c, AvaliacaoGetResponseDto.class)));
    }

    /**
     * Endpoint para salvar uma nova avaliação associando-a a um cliente e funcionário.
     * @param avaliacaoPostRequestDto Objeto recebido na requisição contendo os dados da avaliação.
     * @return A avaliação criada convertida para DTO.
     */
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AvaliacaoGetResponseDto> avaliarFuncionario(@RequestBody @Valid AvaliacaoPostRequestDto avaliacaoPostRequestDto) {
        // Converte o DTO para a entidade Avaliacao
        Avaliacao avaliacao = objectMapperUtil.map(avaliacaoPostRequestDto, Avaliacao.class);

        // Chama o serviço para avaliar um funcionário, associando cliente e funcionário
        Avaliacao saved = avaliacaoService.avaliarFuncionario(
                avaliacao.getCliente().getId(), // Obtém o ID do cliente
                avaliacao.getFuncionario().getId(), // Obtém o ID do funcionário
                avaliacao.getComentario(), // Obtém o comentário
                avaliacao.getNota() // Obtém a nota
        );

        // Retorna a resposta com status 201 (CREATED) e a avaliação salva
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(saved, AvaliacaoGetResponseDto.class));
    }

    /**
     * Endpoint para atualizar uma avaliação existente.
     * @param avaliacaoPostRequestDto Objeto recebido na requisição contendo os novos dados da avaliação.
     * @return Retorna uma resposta sem conteúdo (HTTP 204) indicando sucesso.
     */
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid AvaliacaoPostRequestDto avaliacaoPostRequestDto) {
        // Converte o DTO para a entidade Avaliacao
        Avaliacao avaliacao = objectMapperUtil.map(avaliacaoPostRequestDto, Avaliacao.class);

        // Chama o serviço para atualizar a avaliação
        avaliacaoService.update(avaliacao);

        // Retorna uma resposta HTTP 204 (No Content) indicando que a atualização foi bem-sucedida
        return ResponseEntity.noContent().build();
    }

}
