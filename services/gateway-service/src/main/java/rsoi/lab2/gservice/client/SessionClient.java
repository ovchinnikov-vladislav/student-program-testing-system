package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignConfiguration;
import rsoi.lab2.gservice.client.fallback.factory.SessionFallbackFactory;
import rsoi.lab2.gservice.entity.user.User;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.oauth.AuthorizationCode;
import rsoi.lab2.gservice.model.oauth.OAuthClient;
import rsoi.lab2.gservice.model.oauth.RegistrationOAuthClientDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "session-service", configuration = FeignConfiguration.class, fallbackFactory = SessionFallbackFactory.class)
public interface SessionClient {

    @PostMapping(value = "/api/v1/oauth/token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, String> getTokenByForm(@RequestParam HashMap<String, String> requestDto,
                                       @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/oauth/token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Map<String, String> getTokenByJson(@RequestBody HashMap<String, String> requestDto,
                                       @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/oauth/token/validity", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Boolean validityToken(@RequestBody HashMap<String, String> requestDto);

    @GetMapping(value = "/api/v1/users")
    PageCustom<User> findUsersAll(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size,
                                  @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{id}")
    Optional<User> findUserById(@PathVariable UUID id);

    @GetMapping(value = "/api/v1/clients")
    PageCustom<OAuthClient> findClientsAll(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size,
                                           @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/clients/{id}")
    Optional<OAuthClient> findClientById(@PathVariable UUID id);

    @PostMapping(value = "/api/v1/users/reg")
    Optional<User> createUser(@RequestBody User user);

    @PostMapping(value = "/api/v1/clients/reg")
    Optional<OAuthClient> createClient(@RequestBody RegistrationOAuthClientDto client);

    @PutMapping(value = "/api/v1/users/{id}")
    void updateUser(@PathVariable UUID id, @RequestBody User user, @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/clients/{id}")
    void updateClient(@PathVariable UUID id, @RequestBody OAuthClient client, @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/users/{id}")
    void deleteUser(@PathVariable UUID id, @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/clients/{id}")
    void deleteClient(@PathVariable UUID id, @RequestHeader("Authorization") String token);

}
