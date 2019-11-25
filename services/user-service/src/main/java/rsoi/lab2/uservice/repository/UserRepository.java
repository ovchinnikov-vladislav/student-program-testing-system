package rsoi.lab2.uservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rsoi.lab2.uservice.entity.User;
import rsoi.lab2.uservice.model.SomeUsersModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    @Query("select u from User u")
    Page<SomeUsersModel> findAllUsers(Pageable pageable);
}
