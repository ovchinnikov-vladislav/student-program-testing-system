package rsoi.lab2.testservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.service.TestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tests/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test getById(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tests/{}: getById() method called.", headers.getHost(), id);
        return testService.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tests/{idTest}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test getByUserIdAndTestId(@PathVariable Long idUser, @PathVariable Long idTest, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tests/{}: getByUserIdAndTestId() method called.", headers.getHost(), idUser, idTest);
        return testService.getByUserIdAndTestId(idUser, idTest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Test> getAll(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tests: getAll() method called.", headers.getHost());
        return (page != null && size != null) ? testService.getAll(PageRequest.of(page, size)) : testService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Test> getByUserId(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tests: getByUserId() method called.", headers.getHost(), id);
        return (page != null && size != null) ? testService.getByUserId(id, PageRequest.of(page, size)) : testService.getByUserId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Test> getByTaskId(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks/{}/tests: getByTaskId() method called.", headers.getHost(), id);
        return (page != null && size != null) ? testService.getByTaskId(id, PageRequest.of(page, size)) : testService.getByTaskId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Test> getByUserIdAndTaskId(@PathVariable Long idUser, @PathVariable Long idTask, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tasks/{}/tests: getByUserIdAndTaskId() method called.", headers.getHost(), idUser, idTask);
        return (page != null && size != null) ? testService.getByUserIdAndTaskId(idUser, idTask, PageRequest.of(page, size)) : testService.getByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Test create(@Valid @RequestBody Test test, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/tests: create() method called.", headers.getHost());
        return testService.create(test);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/tests/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable Long id, @Valid @RequestBody Test test, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/tests/{}: update() method called.", headers.getHost(), id);
        testService.update(test);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/tests/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/tests/{}: delete() method called.", headers.getHost(), id);
        testService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/tasks/{id}/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteByTaskId(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/tasks/{}/tests: delete() method called.", headers.getHost(), id);
        testService.deleteByTaskId(id);
    }
}
