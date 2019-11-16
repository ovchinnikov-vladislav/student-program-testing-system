package rsoi.lab2.teservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;
import rsoi.lab2.teservice.model.SomeCompletedTaskModel;
import rsoi.lab2.teservice.service.TaskExecutorService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class TaskExecutorController {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorController.class);

    @Autowired
    private TaskExecutorService taskExecutorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/completed_tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask getById(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/completed_tasks/{}: getById() method called.", headers.getHost(), id);
        return taskExecutorService.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/completed_tasks/{idCompletedTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask getByUserIdAndCompletedTaskId(@PathVariable Long idUser, @PathVariable Long idCompletedTask, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/completed_tasks/{}: getByUserIdAndCompletedTaskId() method called.", headers.getHost(), idUser, idCompletedTask);
        return taskExecutorService.getByUserIdAndCompletedTaskId(idUser, idCompletedTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{idTask}/completed_tasks/{idCompletedTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask getByTaskIdAndCompletedTaskId(@PathVariable Long idTask, @PathVariable Long idCompletedTask, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks/{}/completed_tasks/{}: getByTaskIdAndCompletedTaskId() method called.", headers.getHost(), idTask, idCompletedTask);
        return taskExecutorService.getByTaskIdAndCompletedTaskId(idTask, idCompletedTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tests/{idTest}/completed_tasks/{idCompletedTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask getByTestIdAndCompletedTaskId(@PathVariable Long idTest, @PathVariable Long idCompletedTask, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tests/{}/completed_tasks/{}: getByTestIdAndCompletedTaskId() method called.", headers.getHost(), idTest, idCompletedTask);
        return taskExecutorService.getByTestIdAndCompletedTaskId(idTest, idCompletedTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SomeCompletedTaskModel> getAll(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size,
                                               @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/completed_tasks: getAll() method called.", headers.getHost());
        return (page != null && size != null) ?
                taskExecutorService.getAll(PageRequest.of(page, size)) :
                taskExecutorService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SomeCompletedTaskModel> getByUserId(@PathVariable Long id,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size,
                                           @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/completed_tasks: getByUserId() method called.", headers.getHost(), id);
        return (page != null && size != null) ?
                taskExecutorService.getByUserId(id, PageRequest.of(page, size)) :
                taskExecutorService.getByUserId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SomeCompletedTaskModel> getByTaskId(@PathVariable Long id,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size,
                                           @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks/{}/completed_tasks: getByTaskId() method called.", headers.getHost(), id);
        return (page != null && size != null) ?
                taskExecutorService.getByTaskId(id, PageRequest.of(page, size)) :
                taskExecutorService.getByTaskId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tests/{id}/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SomeCompletedTaskModel> getByTestId(@PathVariable Long id,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size,
                                           @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tests/{}/completed_tasks: getByTestId() method called.", headers.getHost(), id);
        return (page != null && size != null) ?
                taskExecutorService.getByTestId(id, PageRequest.of(page, size)) :
                taskExecutorService.getByTestId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SomeCompletedTaskModel> getByUserIdAndTaskId(@PathVariable Long idUser, @PathVariable Long idTask,
                                                    @RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "size", required = false) Integer size,
                                                    @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tasks/{}/completed_tasks: getByTaskId() method called.", headers.getHost(), idUser, idTask);
        return (page != null && size != null) ?
                taskExecutorService.getByIdUserAndIdTask(idUser, idTask, PageRequest.of(page, size)) :
                taskExecutorService.getByIdUserAndIdTask(idUser, idTask);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask create(@Valid @RequestBody CompletedTask completedTask, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/completed_tasks: create() method called.", headers.getHost());
        return taskExecutorService.create(completedTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/completed_tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable Long id, @Valid @RequestBody CompletedTask completedTask, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/completed_tasks/{}: update() method called.", headers.getHost(), id);
        completedTask.setIdCompletedTask(id);
        taskExecutorService.update(completedTask);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/completed_tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/completed_tasks/{}: delete() method called.", headers.getHost(), id);
        taskExecutorService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/tasks/execute", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultTest execute(@Valid @RequestBody ExecuteTaskRequest executeTaskRequest, @RequestHeader HttpHeaders headers)
            throws IOException, NoSuchAlgorithmException, URISyntaxException, ClassNotFoundException {
        logger.info("POST http://{}/tasks/execute: execute() method called.", headers.getHost());
        return taskExecutorService.execute(executeTaskRequest);
    }
}
