package br.com.ifba.Funcionario.service;

// Importações necessárias para testes unitários
import br.com.ifba.Funcionario.entity.Funcionario;
import br.com.ifba.Funcionario.repository.FuncionarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// Classe de teste para o serviço de funcionários
public class FuncionarioServiceTest {

    // Mock do repositório para simular o acesso ao banco de dados
    @Mock
    private FuncionarioRepository funcionarioRepository;

    // Injeta o mock do repositório dentro do serviço que será testado
    @InjectMocks
    private FuncionarioService funcionarioService;

    // Método executado antes de cada teste para inicializar os mocks do Mockito
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para verificar a listagem paginada de funcionários
    @Test
    public void RetornarTodosOsFuncionariosPaginados() {
        // Simula uma lista de funcionários
        List<Funcionario> funcionarios = Arrays.asList(new Funcionario(), new Funcionario());

        // Cria um objeto Page contendo os funcionários
        Page<Funcionario> funcionarioPage = new PageImpl<>(funcionarios);

        // Define a página 0 com tamanho 2
        Pageable pageable = PageRequest.of(0, 2);

        // Configura o mock para retornar a página de funcionários ao chamar findAll
        when(funcionarioRepository.findAll(any(Pageable.class))).thenReturn(funcionarioPage);

        // Chama o serviço para buscar os funcionários paginados
        Page<Funcionario> resultado = funcionarioService.findAll(pageable);

        // Verifica se o resultado não é nulo e se o total de funcionários é 2
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
    }

    // Teste para buscar um funcionário pelo nome
    @Test
    public void BuscarFuncionarioPorNome() {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome("Rodrigo");

        // Simula uma lista contendo apenas um funcionário chamado Rodrigo
        List<Funcionario> funcionariosMock = List.of(funcionario1);

        // Configura o mock para retornar a lista quando o método findByNome for chamado
        when(funcionarioRepository.findByNome("Rodrigo")).thenReturn(funcionariosMock);

        // Chama o serviço para buscar funcionários pelo nome
        List<Funcionario> resultado = funcionarioService.findByNome("Rodrigo");

        // Verifica se o resultado não é nulo, se contém apenas um funcionário e se o nome está correto
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Rodrigo", resultado.get(0).getNome());

        System.out.println("Funcionario encontrado: " + resultado.get(0).getNome());
    }

    // Teste para buscar um funcionário pelo ID
    @Test
    public void BuscarFuncionarioPorId() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);

        // Configura o mock para retornar um funcionário ao buscar pelo ID 1
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        // Chama o serviço para buscar o funcionário pelo ID
        Funcionario resultado = funcionarioService.findById(1L);

        // Verifica se o resultado não é nulo e se o ID está correto
        assertNotNull(resultado);
        assertEquals(Long.valueOf(1), resultado.getId());
    }

    // Teste para verificar o lançamento de exceção quando o funcionário não existe
    @Test
    public void LancarExcecaoQuandoFuncionarioNaoExiste() {
        // Configura o mock para retornar um Optional vazio ao buscar um ID inexistente
        when(funcionarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Verifica se ao tentar buscar um ID inexistente, uma exceção é lançada com a mensagem correta
        Exception exception = assertThrows(RuntimeException.class, () -> funcionarioService.findById(99L));
        assertEquals("Recurso não encontrado!", exception.getMessage());
    }

    // Teste para salvar um novo funcionário
    @Test
    public void SalvarNovoFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Jorge");
        funcionario.setCargo("Atendente");
        funcionario.setSalario(1350.0);

        // Configura o mock para retornar o mesmo funcionário ao chamar save
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        // Chama o serviço para salvar o funcionário
        Funcionario resultado = funcionarioService.save(funcionario);

        // Verifica se os dados foram salvos corretamente
        assertNotNull(resultado);
        assertEquals("Jorge", resultado.getNome());
        assertEquals("Atendente", resultado.getCargo());
        assertEquals(1350.0, resultado.getSalario(), 1); // O terceiro parâmetro é a margem de erro para comparação de valores double

        // Verifica se o método save foi chamado exatamente uma vez
        verify(funcionarioRepository, times(1)).save(funcionario);

        System.out.println("Funcionario Salvo: " + resultado.getNome() +
                " Cargo:" + resultado.getCargo() +
                " Salario: " + resultado.getSalario() );
    }

    // Teste para atualizar um funcionário existente
    @Test
    public void AtualizarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Paula");
        funcionario.setCargo("Supervisora");
        funcionario.setSalario(1750);

        // Configura o mock para retornar o funcionário atualizado ao chamar save
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        // Chama o serviço para atualizar o funcionário
        funcionarioService.update(funcionario);

        // Verifica se o método save foi chamado exatamente uma vez
        verify(funcionarioRepository, times(1)).save(funcionario);

        System.out.println("Funcionario Atualizado: " + funcionario.getNome() +
                " Cargo:" + funcionario.getCargo() +
                " Salario: " + funcionario.getSalario() );
    }

    // Teste para deletar um funcionário por ID
    @Test
    public void DeletarFuncionarioPorId() {
        // Configura o mock para não fazer nada ao chamar deleteById
        doNothing().when(funcionarioRepository).deleteById(1L);

        // Chama o serviço para deletar o funcionário
        Map<String, String> response = funcionarioService.delete(1L);

        // Verifica se o método deleteById foi chamado exatamente uma vez
        verify(funcionarioRepository, times(1)).deleteById(1L);

        // Verifica se a mensagem de sucesso foi retornada corretamente
        assertEquals("Funcionário deletado com sucesso", response.get("message"));
    }
}
