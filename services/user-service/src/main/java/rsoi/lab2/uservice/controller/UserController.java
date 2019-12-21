package rsoi.lab2.uservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.uservice.entity.User;
import rsoi.lab2.uservice.exception.HttpNotFoundException;
import rsoi.lab2.uservice.exception.HttpNotValueOfParameterException;
import rsoi.lab2.uservice.model.CheckUserRequest;
import rsoi.lab2.uservice.model.PageCustom;
import rsoi.lab2.uservice.model.SomeUsersModel;
import rsoi.lab2.uservice.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/")
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeUsersModel> findAll(@NotNull @RequestParam(value = "page") Integer page,
                                              @NotNull @RequestParam(value = "size") Integer size,
                                              @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users: findAll() method called.", headers.getHost());
        return userService.findAll(PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SomeUsersModel findById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        InetSocketAddress host = headers.getHost();
        logger.info("GET http://{}/users/{}: findById() method called.", headers.getHost(), id);
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/users/check", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User findByRequest(@RequestBody CheckUserRequest request, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/users/check: findByRequest() method called: ", headers.getHost());
        logger.info("\trequest: " + request);
        if (request.getUserName() != null && request.getPassword() != null) {
            logger.info("\trequest.getUserName() != null && request.getPassword() != null");
            try {
                User userByLogin = userService.findByUserName(request.getUserName());
                if (userByLogin != null && userByLogin.getPassword().equals(request.getPassword())) {
                    logger.info("\t\tuserByLogin != null && userByLogin.getPassword().equals(request.getPassword())");
                    return userByLogin;
                }
            } catch (HttpNotFoundException exc) {
                logger.info("\trequest.getEmail() != null && request.getPassword() != null");
                User userByEmail = userService.findByEmail(request.getEmail());
                if (userByEmail != null && userByEmail.getPassword().equals(request.getPassword())) {
                    logger.info("\t\tuserByEmail != null && userByEmail.getPassword().equals(request.getPassword())");
                    return userByEmail;
                }
            }
        }
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User create(@Valid @RequestBody User user, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/users: create() method called.", headers.getHost());
        return userService.create(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User update(@PathVariable UUID id, @Valid @RequestBody User user, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/users/{}: update() method called.", headers.getHost(), id);
        return userService.update(id, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE {}/users/{}: delete() method called.", headers.getHost(), id);
        userService.delete(id);
    }
}
