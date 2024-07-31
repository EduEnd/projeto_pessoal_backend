package br.com.ifba.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifba.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByName();

    public List<User> findByEmail();

}
