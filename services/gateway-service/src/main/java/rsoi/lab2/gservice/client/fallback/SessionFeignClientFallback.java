package rsoi.lab2.gservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.gservice.client.SessionClient;
import rsoi.lab2.gservice.entity.user.User;
import rsoi.lab2.gservice.exception.jwt.JwtAuthenticationException;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.oauth.AuthorizationCode;
import rsoi.lab2.gservice.model.oauth.OAuthClient;
import rsoi.lab2.gservice.model.oauth.RegistrationOAuthClientDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SessionFeignClientFallback implements SessionClient {
    private static final Logger logger = LoggerFactory.getLogger(SessionFeignClientFallback.class);
    private final Throwable cause;

    public SessionFeignClientFallback(Throwable cause) {
        this.cause = cause;
    }


    @Override
    public Map<String, String> getTokenByForm(HashMap<String, String> requestDto, String token) {
        logger.error("getTokenByForm() method called.");
        logger.error(cause.getMessage());
        Map<String, String> fallback = new HashMap<>();
        fallback.put("access_token", null);
        return fallback;
    }

    @Override
    public Map<String, String> getTokenByJson(HashMap<String, String> requestDto, String token) {
        logger.error("getTokenByJson() method called.");
        logger.error(cause.getMessage());
        Map<String, String> fallback = new HashMap<>();
        fallback.put("access_token", null);
        return fallback;
    }

    @Override
    public Boolean validityToken(HashMap<String, String> requestDto) {
        logger.error("getTokenByJson() method called.");
        logger.error(cause.getMessage());
        return null;
    }

    @Override
    public PageCustom<User> findUsersAll(Integer page, Integer size, String token) {
        logger.error("findUsersAll() method called.");
        logger.error(cause.getMessage());
        if (cause instanceof JwtAuthenticationException)
            throw new JwtAuthenticationException(cause.getMessage());
        return null;
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        logger.error("findUserById() method called.");
        logger.error(cause.getMessage());
        User fallback = new User();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public PageCustom<OAuthClient> findClientsAll(Integer page, Integer size, String token) {
        logger.error("findClientsAll() method called.");
        logger.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<OAuthClient> findClientById(UUID id) {
        logger.error("findClientById() method called.");
        logger.error(cause.getMessage());
        OAuthClient fallback = new OAuthClient();
        fallback.setClientId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<User> createUser(User fallback) {
        logger.error("createUser() method called.");
        logger.error(cause.getMessage());
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public void updateUser(UUID id, User user, String token) {
        logger.error("updateUser() method called.");
        logger.error(cause.getMessage());
    }

    @Override
    public void deleteUser(UUID id, String token) {
        logger.error("deleteUser() method called.");
        logger.error(cause.getMessage());
    }

    @Override
    public Optional<OAuthClient> createClient(RegistrationOAuthClientDto client) {
        logger.error("createClient() method called.");
        logger.error(cause.getMessage());
        OAuthClient fallback = new OAuthClient();
        fallback.setClientId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public void updateClient(UUID id, OAuthClient client, String token) {
        logger.error("updateClient() method called.");
        logger.error(cause.getMessage());
    }

    @Override
    public void deleteClient(UUID id, String token) {
        logger.error("deleteClient() method called.");
        logger.error(cause.getMessage());
    }
}
