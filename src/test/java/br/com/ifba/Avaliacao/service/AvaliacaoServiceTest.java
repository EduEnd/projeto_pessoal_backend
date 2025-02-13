package br.com.ifba.Avaliacao.service;

// Importação das classes necessárias para os testes.
import br.com.ifba.Avaliacao.entity.Avaliacao;
import br.com.ifba.Avaliacao.repository.AvaliacaoRepository;
import br.com.ifba.Client.entity.Client;
import br.com.ifba.Client.repository.ClientRepository;
import br.com.ifba.Funcionario.entity.Funcionario;
import br.com.ifba.Funcionario.repository.FuncionarioRepository;
import br.com.ifba.User.entity.User;
import org.junit.jupiter.api.BeforeEach;  // Preparação dos testes antes da execução.
import org.junit.jupiter.api.Test;  // Anotação para os métodos de teste.
import org.junit.jupiter.api.extension.ExtendWith;  // Extensão para integração com Mockito.
import org.mockito.InjectMocks;  // Injeta os mocks na classe a ser testada.
import org.mockito.Mock;  // Cria mocks para as dependências.
import org.mockito.junit.jupiter.MockitoExtension;  // Extensão do JUnit 5 para suporte ao Mockito.

import java.util.Optional;  // Usado para simular valores retornados pelos repositórios.

import static org.junit.jupiter.api.Assertions.*;  // Métodos de asserção para testes.
import static org.mockito.Mockito.*;  // Métodos do Mockito para verificar e simular comportamentos.

@ExtendWith(MockitoExtension.class)  // Extensão que permite o uso do Mockito com JUnit 5.
class AvaliacaoServiceTest {

    // Injeta a classe de serviço que será testada.
    @InjectMocks
    private AvaliacaoService avaliacaoService;

    // Criação dos mocks para as dependências da classe AvaliacaoService.
    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    // Objetos auxiliares para os testes.
    private Client mockClient;
    private Funcionario mockFuncionario;
    private Avaliacao mockAvaliacao;

    // Método executado antes de cada teste para inicializar os dados necessários.
    @BeforeEach
    void setUp() {
        // Criação e configuração de um cliente mockado.
        mockClient = new Client();
        mockClient.setUser(new User());
        mockClient.setId(1L);
        mockClient.getUser().setName("Cliente Teste");

        // Criação e configuração de um funcionário mockado.
        mockFuncionario = new Funcionario();
        mockFuncionario.setUser(new User());
        mockFuncionario.setId(2L);
        mockFuncionario.getUser().setName("Funcionário Teste");

        // Criação de uma avaliação mockada associada ao cliente e funcionário.
        mockAvaliacao = new Avaliacao();
        mockAvaliacao.setCliente(mockClient);
        mockAvaliacao.setFuncionario(mockFuncionario);
        mockAvaliacao.setComentario("Ótimo trabalho!");
        mockAvaliacao.setNota(5);
    }

    // Teste para a criação de uma avaliação.
    @Test
    void AvaliarFuncionario() {
        // Simula os comportamentos dos repositórios.
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));
        when(funcionarioRepository.findById(2L)).thenReturn(Optional.of(mockFuncionario));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(mockAvaliacao);

        // Executa o método avaliarFuncionario.
        Avaliacao avaliacaoSalva = avaliacaoService.avaliarFuncionario(1L, 2L, "Ótimo trabalho!", 5);

        // Verificações das condições esperadas para o resultado.
        assertNotNull(avaliacaoSalva);  // Verifica se a avaliação salva não é nula.
        assertEquals("Ótimo trabalho!", avaliacaoSalva.getComentario());  // Verifica o comentário.
        assertEquals(5, avaliacaoSalva.getNota());  // Verifica a nota.
        assertEquals(mockClient, avaliacaoSalva.getCliente());  // Verifica se o cliente está correto.
        assertEquals(mockFuncionario, avaliacaoSalva.getFuncionario());  // Verifica se o funcionário está correto.

        // Verifica se os métodos dos repositórios foram chamados uma vez.
        verify(clientRepository, times(1)).findById(1L);
        verify(funcionarioRepository, times(1)).findById(2L);
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    // Teste para o caso em que o cliente não é encontrado.
    @Test
    void ExcecaoClienteNaoEncontrado() {
        // Simula o comportamento do repositório quando o cliente não é encontrado.
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // Executa a chamada do método e verifica se a exceção é lançada.
        Exception exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.avaliarFuncionario(1L, 2L, "Ótimo trabalho!", 5);
        });

        // Verifica a mensagem da exceção.
        assertEquals("Cliente não encontrado", exception.getMessage());

        // Verifica que os repositórios foram chamados corretamente.
        verify(clientRepository, times(1)).findById(1L);
        verify(funcionarioRepository, never()).findById(anyLong());  // Verifica que o repositório de funcionários não foi chamado.
        verify(avaliacaoRepository, never()).save(any(Avaliacao.class));  // Verifica que a avaliação não foi salva.
    }

    // Teste para o caso em que o funcionário não é encontrado.
    @Test
    void ExcecaoFuncionarioNaoEncontrado() {
        // Simula que o cliente foi encontrado, mas o funcionário não foi encontrado.
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));
        when(funcionarioRepository.findById(2L)).thenReturn(Optional.empty());

        // Executa a chamada do método e verifica se a exceção é lançada.
        Exception exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.avaliarFuncionario(1L, 2L, "Ótimo trabalho!", 5);
        });

        // Verifica a mensagem da exceção.
        assertEquals("Funcionário não encontrado", exception.getMessage());

        // Verifica que os repositórios foram chamados corretamente.
        verify(clientRepository, times(1)).findById(1L);
        verify(funcionarioRepository, times(1)).findById(2L);
        verify(avaliacaoRepository, never()).save(any(Avaliacao.class));  // Verifica que a avaliação não foi salva.
    }
}
