package br.com.ifba.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifba.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
