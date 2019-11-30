package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.entity.Result;
import rsoi.lab2.gservice.entity.Test;
import rsoi.lab2.gservice.service.TaskService;
import rsoi.lab2.gservice.service.ResultService;
import rsoi.lab2.gservice.service.TestService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/gate")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users/{idUser}/tasks/{idTask}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test create(@PathVariable UUID idUser, @PathVariable UUID idTask, @Valid @RequestBody Test test,
                       @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/gate/users/{}/tasks/{}/tests: create() method called.", headers.getHost(), idUser, idTask);
        test.setIdTask(idTask);
        test.setIdUser(idUser);
        return testService.create(test);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/users/{idUser}/tasks/{idTask}/tests/{idTest}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable UUID idUser, @PathVariable UUID idTask, @PathVariable UUID idTest,
                       @Valid @RequestBody Test test, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/gate/users/{}/tasks/{}/tests/{}: update() method called.", headers.getHost(), idUser, idTask, idTest);
        test.setIdTest(idTest);
        test.setIdUser(idUser);
        test.setIdTask(idTask);
        testService.update(test);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/users/{idUser}/tasks/{idTask}/tests/{idTest}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable UUID idUser, @PathVariable UUID idTask,
                       @PathVariable UUID idTest, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/gate/users/{}/tasks/{}/tests/{}: delete() method called.", headers.getHost(), idUser, idTask, idTest);
        testService.delete(idTest);
    }
}
