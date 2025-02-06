package br.com.ifba.Funcionario.controller;

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

@RestController
@RequestMapping("/funcionario")
@CrossOrigin("*")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioIService funcionarioService;
    private final ObjectMapperUtil objectMapperUtil;

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FuncionarioGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.funcionarioService.findAll(pageable).map(f -> objectMapperUtil
                        .map(f, FuncionarioGetResponseDto.class)));
    }

    @GetMapping(path = "/findByName/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FuncionarioGetResponseDto>> findByNome(@PathVariable String nome) {
        List<Funcionario> funcionarios = this.funcionarioService.findByNome(nome);
        List<FuncionarioGetResponseDto> responseDto = objectMapperUtil.mapAll(funcionarios, FuncionarioGetResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/funcionario/{id}")
    public ResponseEntity<FuncionarioGetResponseDto> findById(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.findById(id);
        FuncionarioGetResponseDto responseDto = objectMapperUtil.map(funcionario, FuncionarioGetResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuncionarioGetResponseDto> save(@RequestBody @Valid FuncionarioPostRequestDto funcionarioPostRequestDto) {
        Funcionario funcionario = objectMapperUtil.map(funcionarioPostRequestDto, Funcionario.class);
        Funcionario savedFuncionario = funcionarioService.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(savedFuncionario, FuncionarioGetResponseDto.class));
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid FuncionarioPostRequestDto funcionarioPostRequestDto) {
        Funcionario funcionario = objectMapperUtil.map(funcionarioPostRequestDto, Funcionario.class);
        funcionarioService.update(funcionario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(funcionarioService.delete(id));
    }
}
