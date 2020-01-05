package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.user.User;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.TokenObject;
import rsoi.lab2.gservice.model.oauth.AuthorizationCode;
import rsoi.lab2.gservice.model.oauth.OAuthClient;
import rsoi.lab2.gservice.model.oauth.RegistrationOAuthClientDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface SessionService {
    Map<String, String> getTokenByForm(HashMap<String, String> requestDto, String token);
    Map<String, String> getTokenByJson(HashMap<String, String> requestDto, String token);
    Boolean validityToken(HashMap<String, String> requestDto);
    PageCustom<User> findUsersAll(Integer page, Integer size, String token);
    User findUserById(UUID id);
    PageCustom<OAuthClient> findClientsAll(Integer page, Integer size, String token);
    OAuthClient findClientById(UUID id);
    User createUser(User user);
    OAuthClient createClient(RegistrationOAuthClientDto clientDto);
    void updateUser(UUID id, User user, String token);
    void updateClient(UUID id, OAuthClient client, String token);
    void deleteUser(UUID id, String token);
    void deleteClient(UUID id, String token);
}
