package br.com.ifba.Funcionario.service;

// Importações necessárias para testes unitários
import br.com.ifba.Client.entity.Client;
import br.com.ifba.Client.repository.ClientRepository;
import br.com.ifba.Funcionario.entity.Funcionario;
import br.com.ifba.Funcionario.repository.FuncionarioRepository;
import br.com.ifba.User.entity.User;
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

    @Mock
    private ClientRepository clientRepository;

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
    public void RetornarTodosOsFuncionarios() {
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

        System.out.println("Total de funcionarios paginados " + resultado.getTotalElements() + "\n");
    }

    // Teste para buscar um funcionário pelo nome
    @Test
    public void BuscarFuncionarioPorNome() {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setUser(new User());
        funcionario1.getUser().setName("Rodrigo");

        // Simula uma lista contendo apenas um funcionário chamado Rodrigo
        List<Funcionario> funcionariosMock = List.of(funcionario1);

        // Configura o mock para retornar a lista quando o método findByNome for chamado
        when(funcionarioRepository.findByNome("Rodrigo")).thenReturn(funcionariosMock);

        // Chama o serviço para buscar funcionários pelo nome
        List<Funcionario> resultado = funcionarioService.findByNome("Rodrigo");

        // Verifica se o resultado não é nulo, se contém apenas um funcionário e se o nome está correto
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Rodrigo", resultado.get(0).getUser().getName());

        System.out.println("Funcionario encontrado: " + resultado.get(0).getUser().getName() + "\n");
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
    public void ExcecaoFuncionarioNaoExiste() {
        // Configura o mock para retornar um Optional vazio ao buscar um ID inexistente
        when(funcionarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Verifica se ao tentar buscar um ID inexistente, uma exceção é lançada com a mensagem correta
        Exception exception = assertThrows(RuntimeException.class, () -> funcionarioService.findById(99L));
        assertEquals("Recurso não encontrado!", exception.getMessage());
        System.out.println("Funcionario nao existe" + "\n");
    }

    // Teste para salvar um novo funcionário
    @Test
    public void SalvarNovoFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setUser(new User());
        funcionario.getUser().setName("Jorge");
        funcionario.setCargo("Atendente");
        funcionario.setSalario(1350.0);
        funcionario.getUser().setEmail("JJorge@gmail.com");
        funcionario.getUser().setIdade(32);
        funcionario.getUser().setEndereco("Piraporinha do sul");



        // Configura o mock para retornar o mesmo funcionário ao chamar save
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        // Chama o serviço para salvar o funcionário
        Funcionario resultado = funcionarioService.save(funcionario);

        // Verifica se os dados foram salvos corretamente
        assertNotNull(resultado);
        assertEquals("Jorge", resultado.getUser().getName());
        assertEquals("Atendente", resultado.getCargo());
        assertEquals(1350.0, resultado.getSalario(), 1);
        assertEquals("JJorge@gmail.com", resultado.getUser().getEmail());
        assertEquals(32, resultado.getUser().getIdade());
        assertEquals("Piraporinha do sul", resultado.getUser().getEndereco());

        // Verifica se o método save foi chamado exatamente uma vez
        verify(funcionarioRepository, times(1)).save(funcionario);

        System.out.println("Funcionario Salvo: " + resultado.getUser().getName() +
                "\n Cargo:" + resultado.getCargo() +
                "\n Salario: " + resultado.getSalario() +
                "\n Email: " + resultado.getUser().getEmail() +
                "\n Idade: " + resultado.getUser().getIdade() +
                "\n Localidade: " + resultado.getUser().getEndereco() + "\n");
    }

    // Teste para atualizar um funcionário existente
    @Test
    public void AtualizarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setUser(new User());
        funcionario.setId(1L);
        funcionario.getUser().setName("Paula");
        funcionario.setCargo("Supervisora");
        funcionario.setSalario(1750);
        funcionario.getUser().setEmail("Paulinha@hotmail.com");
        funcionario.getUser().setIdade(29);
        funcionario.getUser().setEndereco("Morro do chapeu");

        // Configura o mock para retornar o funcionário atualizado ao chamar save
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        // Chama o serviço para atualizar o funcionário
        funcionarioService.update(funcionario);

        // Verifica se o método save foi chamado exatamente uma vez
        verify(funcionarioRepository, times(1)).save(funcionario);

        System.out.println("Funcionario Atualizado: " + funcionario.getUser().getName() +
                "\n Cargo: " + funcionario.getCargo() +
                "\n Salario: " + funcionario.getSalario() +
                "\n Email: " + funcionario.getUser().getEmail() +
                "\n Idade: " + funcionario.getUser().getIdade() +
                "\n Localidade: " + funcionario.getUser().getEndereco() + "\n");
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
        System.out.println("Funcionario deletado\n");
    }

    @Test
    public void AssociarFuncionarioPorId(){
        Long funcionarioId = 1L;
        Long clientId = 2L;

        Funcionario funcionario = new Funcionario();
        funcionario.setUser(new User());
        funcionario.setId(funcionarioId);
        funcionario.getUser().setName("Lula");

        Client client = new Client();
        client.setUser(new User());
        client.setId(clientId);
        client.getUser().setName("Bolsonaro");

        when(funcionarioRepository.findById(funcionarioId)).thenReturn(Optional.of(funcionario));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        funcionarioService.associarClient(funcionarioId, clientId);

        assertTrue(funcionario.getClients().contains(client));

        verify(funcionarioRepository).save(funcionario);

        System.out.println("\nCliente associado com sucesso\n");
    }

    @Test
    public void ListarClientesDoFuncionario() {

        Long funcionarioId = 1L;

        Funcionario funcionario = new Funcionario();
        funcionario.setUser(new User());
        funcionario.setId(funcionarioId);
        funcionario.getUser().setName("Lula");

        Client client1 = new Client();
        client1.setUser(new User());
        client1.setId(10L);
        client1.getUser().setName("Bolsonaro");

        Client client2 = new Client();
        client2.setUser(new User());
        client2.setId(20L);
        client2.getUser().setName("Dilma");

        List<Client> clientes = Arrays.asList(client1, client2);
        funcionario.setClients(clientes);

        when(funcionarioRepository.findById(funcionarioId)).thenReturn(Optional.of(funcionario));

        List<Client> resultado = funcionarioService.buscarClientesDoFuncionario(funcionarioId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Bolsonaro", resultado.get(0).getUser().getName());
        assertEquals("Dilma", resultado.get(1).getUser().getName());

        verify(funcionarioRepository).findById(funcionarioId);

        System.out.println("Funcionario: " + funcionario.getUser().getName()
        + "\nClientes associados: " + resultado.get(0).getUser().getName()
                +"," + resultado.get(1).getUser().getName() );
    }

}
