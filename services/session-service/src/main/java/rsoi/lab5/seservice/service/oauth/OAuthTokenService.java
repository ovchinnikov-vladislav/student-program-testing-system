package rsoi.lab5.seservice.service.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab5.seservice.repository.OAuthTokenRepository;
import rsoi.lab5.seservice.entity.oauth.OAuthToken;

import java.util.UUID;

@Service
public class OAuthTokenService {

    private static final Logger logger = LoggerFactory.getLogger(OAuthTokenService.class);

    @Autowired
    private OAuthTokenRepository repository;

    public OAuthToken findByClientId(UUID id) {
        return repository.findByClientId(id);
    }

    public OAuthToken findByUserId(UUID id) {
        return repository.findByUserId(id);
    }

    public OAuthToken create(OAuthToken token) {
        return repository.saveAndFlush(token);
    }

    public OAuthToken update(OAuthToken token) {
        return repository.saveAndFlush(token);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public void deleteByClientId(UUID id) {
        repository.deleteByClientId(id);
    }
}
