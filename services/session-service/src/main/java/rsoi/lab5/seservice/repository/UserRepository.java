package rsoi.lab5.seservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rsoi.lab5.seservice.entity.user.User;
import rsoi.lab5.seservice.model.user.UserModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String name);
    Optional<User> findByUsernameOrEmail(String username, String email);
    @Query("select u from User u")
    Page<UserModel> findAllUsers(Pageable pageable);
    @Query("select u from User u where id = :id")
    Optional<UserModel> findByIdUser(@Param("id") UUID id);
}
