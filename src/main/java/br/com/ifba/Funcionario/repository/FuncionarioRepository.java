package br.com.ifba.Funcionario.repository;

import br.com.ifba.Funcionario.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByNome(String nome);
}
