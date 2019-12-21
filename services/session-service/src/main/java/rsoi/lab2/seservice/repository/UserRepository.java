package rsoi.lab2.seservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rsoi.lab2.seservice.entity.User;

/**
 * User repository for CRUD operations.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
