package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.*;
import rsoi.lab2.gservice.entity.user.User;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.oauth.AuthorizationCode;
import rsoi.lab2.gservice.model.oauth.OAuthClient;
import rsoi.lab2.gservice.model.oauth.RegistrationOAuthClientDto;
import rsoi.lab2.gservice.service.SessionService;
import rsoi.lab2.gservice.service.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<User> findUsersAll(@NotNull @RequestParam(value = "page") Integer page, @NotNull @RequestParam(value = "size") Integer size,
                                         @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        logger.info("GET http://{}/api/v1/oauth/users: findUsersAll() method called.", headers.getHost());
        return sessionService.findUsersAll(page, size, jwtTokenProvider.resolveToken(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/clients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<OAuthClient> findClientsAll(@NotNull @RequestParam(value = "page") Integer page, @NotNull @RequestParam(value = "size") Integer size,
                                                  @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        logger.info("GET http://{}/api/v1/oauth/clients: findClientsAll() method called.", headers.getHost());
        return sessionService.findClientsAll(page, size, jwtTokenProvider.resolveToken(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User findUserById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/oauth/users/{}: findUserById() method called.", headers.getHost(), id);
        return sessionService.findUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/clients/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OAuthClient getClientById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/oauth/clients/{}: findClientById() method called.", headers.getHost(), id);
        return sessionService.findClientById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User createUser(@Valid @RequestBody User user, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/api/v1/users: createUser() method called.", headers.getHost());
        return sessionService.createUser(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/auth/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateUser(@PathVariable UUID id, @Valid @RequestBody User user, @RequestHeader HttpHeaders headers,
                           HttpServletRequest request) {
        logger.info("PUT http://{}/api/v1/oauth/users/{}: updateUser() method called.", headers.getHost(), id);
        sessionService.updateUser(id, user, jwtTokenProvider.resolveToken(request));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/auth/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteUser(@PathVariable UUID id, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        logger.info("DELETE http://{}/api/v1/oauth/users/{}: deleteUser() method called.", headers.getHost(), id);
        sessionService.deleteUser(id, jwtTokenProvider.resolveToken(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/auth/clients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OAuthClient createClient(@Valid @RequestBody RegistrationOAuthClientDto clientDto, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/api/v1/clients: createClient() method called.", headers.getHost());
        return sessionService.createClient(clientDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/auth/clients/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateClient(@PathVariable UUID id, @Valid @RequestBody OAuthClient client, @RequestHeader HttpHeaders headers,
                             HttpServletRequest request) {
        logger.info("PUT http://{}/api/v1/oauth/clients/{}: updateClient() method called.", headers.getHost(), id);
        sessionService.updateClient(id, client, jwtTokenProvider.resolveToken(request));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/auth/clients/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteClient(@PathVariable UUID id, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        logger.info("DELETE http://{}/api/v1/oauth/clients/{}: deleteClient() method called.", headers.getHost(), id);
        sessionService.deleteClient(id, jwtTokenProvider.resolveToken(request));
    }
}
