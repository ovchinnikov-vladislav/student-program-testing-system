package rsoi.lab2.testservice.controller;

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
import rsoi.lab2.testservice.conf.jwt.JwtTokenProvider;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.exception.HttpNotValueOfParameterException;
import rsoi.lab2.testservice.model.SomeTestsModel;
import rsoi.lab2.testservice.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private TestService testService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public TestController(TestService testService, JwtTokenProvider jwtTokenProvider) {
        this.testService = testService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> getToken(@RequestHeader HttpHeaders headers, HttpServletRequest req) {
        logger.info("GET http://{}/api/v1/token", headers.getHost());
        return jwtTokenProvider.getToken(req).toMap();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tests/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test findById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/tests/{}: findById() method called.", headers.getHost(), id);
        return testService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tests/{idTest}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test findByUserIdAndTestId(@PathVariable UUID idUser, @PathVariable UUID idTest, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/users/{}/tests/{}: findByUserIdAndTestId() method called.", headers.getHost(), idUser, idTest);
        return testService.findByUserIdAndTestId(idUser, idTest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeTestsModel> findAll(@NotNull @RequestParam(value = "page") Integer page,
                                        @NotNull @RequestParam(value = "size") Integer size,
                                        @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/tests: findAll() method called.", headers.getHost());
        return testService.findAll(PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeTestsModel> findByUserId(@PathVariable UUID id,
                                             @NotNull @RequestParam(value = "page") Integer page,
                                             @NotNull @RequestParam(value = "size") Integer size,
                                             @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/users/{}/tests: findByUserId() method called.", headers.getHost(), id);
        return testService.findByUserId(id, PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test findByTaskId(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/tasks/{}/tests: findByTaskId() method called.", headers.getHost(), id);
        return testService.findByTaskId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/users/{}/tasks/{}/tests: findByUserIdAndTaskId() method called.", headers.getHost(), idUser, idTask);
        return testService.findByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test create(@Valid @RequestBody Test test, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/api/v1/tests: create() method called.", headers.getHost());
        return testService.create(test);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/tests/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable UUID id, @Valid @RequestBody Test test, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/api/v1/tests/{}: update() method called.", headers.getHost(), id);
        testService.update(id, test);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/tests/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/api/v1/tests/{}: delete() method called.", headers.getHost(), id);
        testService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/tasks/{id}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteByTaskId(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/api/v1/tasks/{}/tests: delete() method called.", headers.getHost(), id);
        testService.deleteByTaskId(id);
    }
}
