package br.com.ifba.service;



import br.com.ifba.exception.BusinessException;
import br.com.ifba.repository.UserRepository;
import br.com.ifba.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> findByName() {
        return userRepository.findByName();
    }

    public List<User> findByEmail() {
        return userRepository.findByEmail();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new BusinessException("Recurso não encontrado!"));
    }
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    @Transactional
    public void updateUser(User user) {
             userRepository.save(user);
    }

    public Map<String, String> deleteUser(Long id) {
        userRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuario deletado com sucesso");
        return response;
    }
}
