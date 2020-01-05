package rsoi.lab5.seservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsoi.lab5.seservice.entity.oauth.OAuthToken;

import java.util.UUID;

@Repository
public interface OAuthTokenRepository extends JpaRepository<OAuthToken, UUID> {
    OAuthToken findByClientId(UUID clientId);
    OAuthToken findByRefreshToken(String refreshToken);
    OAuthToken findByUserId(UUID userId);
    void deleteByClientId(UUID id);
}
