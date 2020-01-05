package rsoi.lab5.seservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsoi.lab5.seservice.entity.oauth.OAuthClient;

import java.util.UUID;

@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClient, UUID> {
    OAuthClient findByClientId(UUID id);
}
