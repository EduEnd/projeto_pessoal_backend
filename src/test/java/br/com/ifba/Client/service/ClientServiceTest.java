package br.com.ifba.Client.service;

import br.com.ifba.Client.entity.Client;
import br.com.ifba.Client.repository.ClientRepository;
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

public class ClientServiceTest {

    // Mock da dependência do ClientRepository
    @Mock
    private ClientRepository clientRepository;

    // A injeção do mock no ClientService
    @InjectMocks
    private ClientService clientService;

    // Método para inicializar o Mockito antes de cada teste
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    // Teste para verificar o retorno de todos os clientes de forma paginada
    @Test
    public void RetornarTodosOsClientesPaginados() {
        // Criando uma lista de clientes de exemplo
        List<Client> clients = Arrays.asList(new Client(), new Client());
        // Criando uma página a partir dessa lista
        Page<Client> clientPage = new PageImpl<>(clients);
        // Definindo uma página para a requisição
        Pageable pageable = PageRequest.of(0, 2);

        // Configurando o comportamento do mock para retornar a página de clientes
        when(clientRepository.findAll(any(Pageable.class))).thenReturn(clientPage);

        // Chamando o método no service
        Page<Client> resultado = clientService.findAll(pageable);

        // Verificando se o resultado não é nulo e se a quantidade de elementos está correta
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
    }

    // Teste para buscar clientes por nome
    @Test
    public void BuscarClientesPorNome() {
        // Criando clientes com o nome "João"
        Client client1 = new Client();
        client1.setName("João");
        Client client2 = new Client();
        client2.setName("João");

        // Criando uma lista com esses clientes
        List<Client> clientesMock = List.of(client1, client2);

        // Configurando o mock para retornar a lista de clientes com o nome "João"
        when(clientRepository.findByName("João")).thenReturn(clientesMock);

        // Chamando o método no service
        List<Client> resultado = clientService.findByName("João");

        // Verificando se o resultado não é nulo, se a lista contém 2 elementos e o nome do primeiro cliente
        assertNotNull(resultado);
        assertEquals(2, resultado.size()); // Usando size() para verificar o número de elementos
        assertEquals("João", resultado.get(0).getName());

        System.out.println("Cliente encontrado: " + resultado.get(0).getName());
    }

    // Teste para buscar um cliente pelo ID
    @Test
    public void BuscarClientePorId() {
        // Criando um cliente com ID 1
        Client client = new Client();
        client.setId(1L);

        // Configurando o mock para retornar o cliente quando buscar pelo ID
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Chamando o método no service
        Client resultado = clientService.findById(1L);

        // Verificando se o cliente não é nulo e se o ID do cliente é 1
        assertNotNull(resultado);
        assertEquals(Long.valueOf(1), resultado.getId());

        System.out.println("Cliente buscado por id: " + resultado.getId());
    }

    // Teste para lançar exceção quando um cliente não é encontrado
    @Test
    public void LancarExcecaoQuandoClienteNaoExiste() {
        // Configurando o mock para retornar Optional.empty quando o cliente não é encontrado
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        // Verificando se a exceção RuntimeException é lançada com a mensagem correta
        Exception exception = assertThrows(RuntimeException.class, () -> clientService.findById(99L));

        // Verificando se a mensagem da exceção é a esperada
        assertEquals("Recurso não encontrado!", exception.getMessage());
    }

    // Teste para salvar um novo cliente
    @Test
    public void SalvarNovoCliente() {
        // Criando um cliente com nome "Maria"
        Client client = new Client();
        client.setName("Maria");
        client.setIdade(20);
        client.setLocalidade("Irece/Bahia");

        // Configurando o mock para retornar o cliente quando o método save for chamado
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Chamando o método no service
        Client resultado = clientService.save(client);

        // Verificando se o resultado não é nulo e se o nome do cliente é "Maria"
        assertNotNull(resultado);
        assertEquals("Maria", resultado.getName());
        assertEquals(20, resultado.getIdade());
        assertEquals("Irece/Bahia", resultado.getLocalidade());

        verify(clientRepository, times(1)).save(client);
        System.out.println("Cliente Salvo: " + resultado.getName() +
                " Idade:" + resultado.getIdade() +
                " Localidade: " + resultado.getLocalidade() );
    }

    // Teste para atualizar um cliente
    @Test
    public void AtualizarCliente() {

        // Criando um cliente com ID 1 e nome "Carlos"
        Client client = new Client();
        client.setId(1L);
        client.setName("Carlos");
        client.setIdade(34);
        client.setLocalidade("America dourada/Bahia");

        System.out.println("Cliente atualizado: "+ client.getName() +
                " Idade:" + client.getIdade() +
                " Localidade: " + client.getLocalidade());

        // Configurando o mock para retornar o cliente atualizado
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Chamando o método de atualização no service
        clientService.update(client);

        // Verificando se o método save foi chamado uma vez no mock
        verify(clientRepository, times(1)).save(client);
    }

    // Teste para deletar um cliente pelo ID
    @Test
    public void DeletarClientePorId() {
        // Configurando o mock para não fazer nada quando deletar um cliente
        doNothing().when(clientRepository).deleteById(1L);

        // Chamando o método de delete no service
        Map<String, String> response = clientService.delete(1L);

        // Verificando se o método deleteById foi chamado uma vez
        verify(clientRepository, times(1)).deleteById(1L);
        // Verificando se a mensagem de sucesso está correta
        assertEquals("Cliente deletado com sucesso", response.get("message"));
        System.out.println("Cliente deletado");
    }
}

