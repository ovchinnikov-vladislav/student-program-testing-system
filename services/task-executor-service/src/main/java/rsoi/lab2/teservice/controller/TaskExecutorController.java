package rsoi.lab2.teservice.controller;

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
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.exception.HttpNotValueOfParameterException;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;
import rsoi.lab2.teservice.model.SomeCompletedTaskModel;
import rsoi.lab2.teservice.service.TaskExecutorService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/")
@Validated
public class TaskExecutorController {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorController.class);

    @Autowired
    private TaskExecutorService taskExecutorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/completed_tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask findById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/completed_tasks/{}: findById() method called.", headers.getHost(), id);
        return taskExecutorService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/completed_tasks/{idCompletedTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask findByUserIdAndCompletedTaskId(@PathVariable UUID idUser,
                                                        @PathVariable UUID idCompletedTask,
                                                        @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/completed_tasks/{}: findByUserIdAndCompletedTaskId() method called.", headers.getHost(), idUser, idCompletedTask);
        return taskExecutorService.findByUserIdAndCompletedTaskId(idUser, idCompletedTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{idTask}/completed_tasks/{idCompletedTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask findByTaskIdAndCompletedTaskId(@PathVariable UUID idTask,
                                                       @PathVariable UUID idCompletedTask,
                                                       @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks/{}/completed_tasks/{}: findByTaskIdAndCompletedTaskId() method called.", headers.getHost(), idTask, idCompletedTask);
        return taskExecutorService.findByTaskIdAndCompletedTaskId(idTask, idCompletedTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tests/{idTest}/completed_tasks/{idCompletedTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask findByTestIdAndCompletedTaskId(@PathVariable UUID idTest,
                                                        @PathVariable UUID idCompletedTask,
                                                        @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tests/{}/completed_tasks/{}: findByTestIdAndCompletedTaskId() method called.", headers.getHost(), idTest, idCompletedTask);
        return taskExecutorService.findByTestIdAndCompletedTaskId(idTest, idCompletedTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeCompletedTaskModel> findAll(@NotNull @RequestParam(value = "page") Integer page,
                                                @NotNull @RequestParam(value = "size") Integer size,
                                                @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/completed_tasks: findAll() method called.", headers.getHost());
        return taskExecutorService.findAll(PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeCompletedTaskModel> findByUserId(@PathVariable UUID id,
                                           @NotNull @RequestParam(value = "page") Integer page,
                                           @NotNull @RequestParam(value = "size") Integer size,
                                           @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/completed_tasks: findByUserId() method called.", headers.getHost(), id);
        return taskExecutorService.findByUserId(id, PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeCompletedTaskModel> findByTaskId(@PathVariable UUID id,
                                           @NotNull @RequestParam(value = "page") Integer page,
                                           @NotNull @RequestParam(value = "size") Integer size,
                                           @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks/{}/completed_tasks: findByTaskId() method called.", headers.getHost(), id);
        return taskExecutorService.findByTaskId(id, PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tests/{id}/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeCompletedTaskModel> findByTestId(@PathVariable UUID id,
                                           @NotNull @RequestParam(value = "page") Integer page,
                                           @NotNull @RequestParam(value = "size") Integer size,
                                           @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tests/{}/completed_tasks: findByTestId() method called.", headers.getHost(), id);
        return taskExecutorService.findByTestId(id, PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeCompletedTaskModel> findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask,
                                                    @NotNull @RequestParam(value = "page") Integer page,
                                                    @NotNull @RequestParam(value = "size") Integer size,
                                                    @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tasks/{}/completed_tasks: findByTaskId() method called.", headers.getHost(), idUser, idTask);
        return taskExecutorService.findByIdUserAndIdTask(idUser, idTask, PageRequest.of(page, size));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/completed_tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletedTask create(@Valid @RequestBody CompletedTask completedTask, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/completed_tasks: create() method called.", headers.getHost());
        return taskExecutorService.create(completedTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/completed_tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable UUID id, @Valid @RequestBody CompletedTask completedTask, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/completed_tasks/{}: update() method called.", headers.getHost(), id);
        completedTask.setIdCompletedTask(id);
        taskExecutorService.update(completedTask);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/completed_tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
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
