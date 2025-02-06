package br.com.ifba.User.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifba.User.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

     List<User> findByName(String rodrigo);

     List<User> findByEmail();

}
