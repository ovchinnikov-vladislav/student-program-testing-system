package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.*;
import rsoi.lab2.gservice.exception.HttpNotValueOfParameterException;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.TaskService;
import rsoi.lab2.gservice.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "/gate/users")
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<User> findAll(@NotNull @RequestParam(value = "page") Integer page, @NotNull @RequestParam(value = "size") Integer size,
                                    @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/users: getAll() method called.", headers.getHost());
        return userService.findAll(page, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/users/{}: getById() method called.", headers.getHost(), id);
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User create(@Valid @RequestBody User user, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/gate/users: create() method called.", headers.getHost());
        return userService.create(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/check")
    public User check(@RequestBody User request, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/gate/users/check: check() method called.", headers.getHost());
        return userService.check(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable UUID id, @Valid @RequestBody User user, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/gate/users/{}: update() method called.", headers.getHost(), id);
        user.setIdUser(id);
        userService.update(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/gate/users/{}: delete() method called.", headers.getHost(), id);
        userService.delete(id);
    }
}
