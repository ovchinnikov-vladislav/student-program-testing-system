package rsoi.lab5.seservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsoi.lab5.seservice.entity.oauth.AuthorizationCode;

import java.util.UUID;

@Repository
public interface AuthorizationCodeRepository extends JpaRepository<AuthorizationCode, UUID> {
    AuthorizationCode findByUserId(UUID id);
}
