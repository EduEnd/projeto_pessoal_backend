package br.com.ifba.User.service;

// Importações necessárias para o funcionamento dos testes unitários e manipulação de dados
import br.com.ifba.User.entity.User;
import br.com.ifba.User.repository.UserRepository;
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

public class UserServiceTest {

    // Criação de um mock para o UserRepository (simula a funcionalidade do repositório sem precisar de um banco de dados real)
    @Mock
    private UserRepository userRepository;

    // Injeta o mock userRepository na classe UserService
    @InjectMocks
    private UserService userService;

    // Método executado antes dos testes para inicializar os mocks do Mockito
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Testa a funcionalidade de retornar todos os usuários paginados
    @Test
    public void RetornarTodosOsUserPaginados() {
        // Cria uma lista simulada de usuários
        List<User> users = Arrays.asList(new User(), new User());

        // Cria uma página simulada com os usuários
        Page<User> userPage = new PageImpl<>(users);
        Pageable pageable = PageRequest.of(0, 2); // Define a paginação (página 0, 2 elementos por página)

        // Simula o comportamento do método findAll() do repositório
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        // Chama o método do service
        Page<User> resultado = userService.findAll(pageable);

        // Verifica se o resultado não é nulo e se contém os dois usuários simulados
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
    }

    // Testa a funcionalidade de buscar usuários pelo nome
    @Test
    public void BuscarUserPorNome() {
        // Cria um usuário simulado
        User user1 = new User();
        user1.setName("Marcos");

        // Cria uma lista simulada contendo o usuário
        List<User> usersMock = List.of(user1);

        // Simula o comportamento do repositório ao buscar um usuário pelo nome
        when(userRepository.findByName("Marcos")).thenReturn(usersMock);

        // Chama o método do service
        List<User> resultado = userService.findByName("Marcos");

        // Verifica se o resultado contém o usuário esperado
        assertNotNull(resultado);
        assertEquals(1, resultado.size()); // Verifica se há exatamente 1 usuário na lista
        assertEquals("Marcos", resultado.get(0).getName());

        System.out.println("Usuario Encontrado: " + resultado.get(0).getName());
    }

    // Testa a funcionalidade de buscar um usuário pelo ID
    @Test
    public void BuscarUserPorId() {
        // Cria um usuário simulado
        User user = new User();
        user.setId(1L);

        // Simula o comportamento do repositório ao buscar um usuário por ID
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Chama o método do service
        User resultado = userService.findById(1L);

        // Verifica se o usuário foi encontrado corretamente
        assertNotNull(resultado);
        assertEquals(Long.valueOf(1), resultado.getId());
    }

    // Testa a funcionalidade de lançar exceção quando o usuário não existe
    @Test
    public void LancarExcecaoQuandoUserNaoExiste() {
        // Simula o comportamento do repositório ao não encontrar um usuário
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Verifica se uma exceção RuntimeException é lançada ao buscar um ID inexistente
        Exception exception = assertThrows(RuntimeException.class, () -> userService.findById(99L));

        // Confirma se a mensagem de erro está correta
        assertEquals("Recurso não encontrado!", exception.getMessage());
    }

    // Testa a funcionalidade de salvar um novo usuário
    @Test
    public void SalvarNovoFuncionario() {
        // Cria um novo usuário simulado
        User user = new User();
        user.setName("Leonardo");
        user.setEmail("Leo781@gmail.com");
        user.setUser_name("Leo_Junior");
        user.setPassword("Deusefiel");

        // Simula o comportamento do repositório ao salvar um usuário
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Chama o método do service
        User resultado = userService.save(user);

        // Verifica se o usuário foi salvo corretamente
        assertNotNull(resultado);
        assertEquals("Leonardo", resultado.getName());
        assertEquals("Leo781@gmail.com", resultado.getEmail());
        assertEquals("Leo_Junior", resultado.getUser_name());
        assertEquals("Deusefiel", resultado.getPassword());

        // Garante que o método save() foi chamado exatamente uma vez
        verify(userRepository, times(1)).save(user);

        System.out.println("Usuario Atualizado: " +resultado.getName() +
                ", Email: " + resultado.getEmail() +
                ", Nome de Usuario: " + resultado.getUser_name());
    }

    // Testa a funcionalidade de atualizar um usuário existente
    @Test
    public void AtualizarUser() {
        // Cria um usuário simulado
        User user = new User();
        user.setId(1L);
        user.setName("Sara");
        user.setEmail("SarinhaHD321@gmail.com");
        user.setUser_name("Sarinha");
        user.setPassword("senha");

        // Simula o comportamento do repositório ao salvar um usuário atualizado
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Chama o método do service para atualizar o usuário
        userService.updateUser(user);

        // Garante que o método save() foi chamado exatamente uma vez
        verify(userRepository, times(1)).save(user);

        System.out.println("Usuario Atualizado: " + user.getName() +
                ", Email: " + user.getEmail() +
                ", Nome de Usuario: " + user.getUser_name());
    }

    // Testa a funcionalidade de deletar um usuário pelo ID
    @Test
    public void DeletarUserPorId() {
        // Simula o comportamento do repositório ao deletar um usuário
        doNothing().when(userRepository).deleteById(1L);

        // Chama o método do service para deletar o usuário
        Map<String, String> response = userService.deleteUser(1L);

        // Garante que o método deleteById() foi chamado exatamente uma vez
        verify(userRepository, times(1)).deleteById(1L);

        // Verifica se a mensagem de sucesso foi retornada corretamente
        assertEquals("Usuario deletado com sucesso", response.get("message"));
    }
}


