package rsoi.lab2.gservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.SessionClient;
import rsoi.lab2.gservice.entity.user.User;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.exception.jwt.JwtAuthenticationException;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.oauth.AuthorizationCode;
import rsoi.lab2.gservice.model.oauth.OAuthClient;
import rsoi.lab2.gservice.model.oauth.RegistrationOAuthClientDto;
import rsoi.lab2.gservice.service.SessionService;
import rsoi.lab2.gservice.service.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    private SessionClient sessionClient;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Map<String, String> getTokenByForm(HashMap<String, String> requestDto, String t) {
        logger.info("getTokenByForm() method called:");
        Map<String, String> token = sessionClient.getTokenByForm(requestDto, "Bearer " + t);
        if (token != null && token.get("access_token") == null)
            throw new ServiceAccessException("Session service unavailable.");
        logger.info("\t" + token);
        return token;
    }

    @Override
    public Map<String, String> getTokenByJson(HashMap<String, String> requestDto, String t) {
        logger.info("getTokenByJson() method called:");
        Map<String, String> token = sessionClient.getTokenByJson(requestDto, "Bearer " + t);
        if (token != null && token.get("access_token") == null)
            throw new ServiceAccessException("Session service unavailable.");
        logger.info("\t" + token);
        return token;
    }

    @Override
    public Boolean validityToken(HashMap<String, String> requestDto) {
        logger.info("validityToken() method called:");
        Boolean isValidate = sessionClient.validityToken(requestDto);
        if (isValidate == null)
            throw new ServiceAccessException("Session service unavailable.");
        logger.info("\t" + isValidate);
        return isValidate;
    }

    @Override
    public PageCustom<User> findUsersAll(Integer page, Integer size, String token) {
        logger.info("findUsersAll() method called:");
        checkToken(token);
        PageCustom<User> users = sessionClient.findUsersAll(page, size, "Bearer " + token);
        if (users == null)
            throw new ServiceAccessException("Session service unavailable.");
        logger.info("\t" + users.getContent());
        return users;
    }

    @Override
    public User findUserById(UUID id) {
        logger.info("findUserById() method called:");
        User user = sessionClient.findUserById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be found"));

        UUID zeroUUID = new UUID(0, 0);
        if (user.getId().equals(zeroUUID))
            throw new ServiceAccessException("Session service unavailable.");

        logger.info("\t" + user);
        return user;
    }

    @Override
    public PageCustom<OAuthClient> findClientsAll(Integer page, Integer size, String token) {
        logger.info("findClientsAll() method called:");
        checkToken(token);
        PageCustom<OAuthClient> clients = sessionClient.findClientsAll(page, size, "Bearer " + token);
        if (clients == null)
            throw new ServiceAccessException("Session service unavailable.");
        logger.info("\t" + clients.getContent());
        return clients;
    }

    @Override
    public OAuthClient findClientById(UUID id) {
        logger.info("findClientById() method called:");
        OAuthClient client = sessionClient.findClientById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("OAuthClient could not be found"));

        UUID zeroUUID = new UUID(0, 0);
        if (client.getClientId().equals(zeroUUID))
            throw new ServiceAccessException("Session service unavailable.");

        logger.info("\t" + client);
        return client;
    }

    @Override
    public User createUser(User user) {
        logger.info("createUser() method called:");
        User result = sessionClient.createUser(user)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be created"));

        UUID zeroUUID = new UUID(0, 0);
        if (result.getId().equals(zeroUUID))
            throw new ServiceAccessException("Session service unavailable.");
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void updateUser(UUID id, User user, String token) {
        logger.info("updateUser() method called.");
        checkToken(token);
        User checkUser = sessionClient.findUserById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be checked"));

        UUID zeroUUID = new UUID(0, 0);
        if (checkUser.getId().equals(zeroUUID))
            throw new ServiceAccessException("Session service unavailable.");

        user.setId(id);

        sessionClient.updateUser(id, user, "Bearer " + token);
    }

    @Override
    public void deleteUser(UUID id, String token) {
        logger.info("deleteUser() method called.");
        checkToken(token);
        User user = sessionClient.findUserById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be checked"));

        UUID zeroUUID = new UUID(0, 0);
        if (user.getId().equals(zeroUUID))
            throw new ServiceAccessException("Session service unavailable.");

        sessionClient.deleteUser(id, "Bearer " + token);
    }

    @Override
    public OAuthClient createClient(RegistrationOAuthClientDto clientDto) {
        logger.info("createClient() method called:");
        OAuthClient result = sessionClient.createClient(clientDto)
                .orElseThrow(() -> new HttpCanNotCreateException("OAuthClient could not be created"));

        UUID zeroUUID = new UUID(0, 0);
        if (result.getClientId().equals(zeroUUID))
            throw new ServiceAccessException("Session service unavailable.");
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void updateClient(UUID id, OAuthClient client, String token) {
        logger.info("updateClient() method called.");
        checkToken(token);
        OAuthClient checkClient = sessionClient.findClientById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("OAuthClient could not be checked"));

        UUID zeroUUID = new UUID(0, 0);
        if (checkClient.getClientId().equals(zeroUUID))
            throw new ServiceAccessException("Session service unavailable.");

        client.setClientId(id);

        sessionClient.updateClient(id, client, "Bearer " + token);
    }

    @Override
    public void deleteClient(UUID id, String token) {
        logger.info("deleteClient() method called.");
        checkToken(token);
        OAuthClient client = sessionClient.findClientById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("OAuthClient could not be checked"));

        UUID zeroUUID = new UUID(0, 0);
        if (client.getClientId().equals(zeroUUID))
            throw new ServiceAccessException("Session service unavailable.");

        sessionClient.deleteClient(id, "Bearer " + token);
    }

    private void checkToken(String token) {
        boolean t = jwtTokenProvider.validateAccessToken(token);

        if (!t)
            throw new JwtAuthenticationException("Token is invalid.");
    }
}
