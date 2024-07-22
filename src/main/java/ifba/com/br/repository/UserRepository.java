package ifba.com.br.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ifba.com.br.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
