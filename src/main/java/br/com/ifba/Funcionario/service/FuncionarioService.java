package br.com.ifba.Funcionario.service;

import br.com.ifba.Funcionario.entity.Funcionario;
import br.com.ifba.Funcionario.repository.FuncionarioRepository;
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
public class FuncionarioService implements br.com.ifba.Funcionario.service.FuncionarioIService {

    private final FuncionarioRepository funcionarioRepository;

    public Page<Funcionario> findAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
    }

    public List<Funcionario> findByNome(String nome) {
        return funcionarioRepository.findByNome(nome);
    }

    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado!"));
    }

    @Transactional
    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public void update(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    public Map<String, String> delete(Long id) {
        funcionarioRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Funcionário deletado com sucesso");
        return response;
    }
}
