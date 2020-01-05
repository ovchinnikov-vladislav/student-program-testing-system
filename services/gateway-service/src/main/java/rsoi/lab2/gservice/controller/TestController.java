package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.test.Test;
import rsoi.lab2.gservice.service.TestService;
import rsoi.lab2.gservice.service.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/auth/tasks/{idTask}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test create(@PathVariable UUID idTask, @Valid @RequestBody Test test,
                       @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        logger.info("POST http://{}/api/v1/oauth/tasks/{}/tests: create() method called.", headers.getHost(), idTask);
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
        return testService.create(id, idTask, test);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/auth/tasks/{idTask}/tests/{idTest}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable UUID idTask, @PathVariable UUID idTest,
                       @Valid @RequestBody Test test, @RequestHeader HttpHeaders headers,
                       HttpServletRequest request) {
        logger.info("PUT http://{}/api/v1/oauth/tasks/{}/tests/{}: update() method called.", headers.getHost(), idTask, idTest);
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
        testService.update(id, idTask, idTest, test);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/auth/tasks/{idTask}/tests/{idTest}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable UUID idTask, @PathVariable UUID idTest, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/api/v1/oauth/tasks/{}/tests/{}: delete() method called.", headers.getHost(), idTask, idTest);
        testService.delete(idTest);
    }
}
