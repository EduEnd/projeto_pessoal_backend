package br.com.ifba.Funcionario.service;

import br.com.ifba.Funcionario.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface FuncionarioIService {

    Page<Funcionario> findAll(Pageable pageable);

    List<Funcionario> findByNome(String nome);

    Funcionario findById(Long id);

    Funcionario save(Funcionario funcionario);

    void update(Funcionario funcionario);

    Map<String, String> delete(Long id);
}
