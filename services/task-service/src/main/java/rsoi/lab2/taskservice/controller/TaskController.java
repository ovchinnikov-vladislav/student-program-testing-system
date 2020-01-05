package rsoi.lab2.taskservice.controller;

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
import rsoi.lab2.taskservice.conf.jwt.JwtTokenProvider;
import rsoi.lab2.taskservice.entity.Task;
import rsoi.lab2.taskservice.exception.HttpNotValueOfParameterException;
import rsoi.lab2.taskservice.model.SomeTasksModel;
import rsoi.lab2.taskservice.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public TaskController(TaskService taskService, JwtTokenProvider jwtTokenProvider) {
        this.taskService = taskService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> getToken(@RequestHeader HttpHeaders headers, HttpServletRequest req) {
        logger.info("GET http://{}/api/v1/token", headers.getHost());
        return jwtTokenProvider.getToken(req).toMap();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeTasksModel> findAll(@NotNull @RequestParam(value = "page") Integer page,
                                        @NotNull @RequestParam(value = "size") Integer size,
                                        @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/tasks: findAll() method called.", headers.getHost());
        return taskService.findAll(PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task findById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/tasks/{}: findById() method called.", headers.getHost(), id);
        return taskService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SomeTasksModel> findByUserId(@PathVariable UUID id,
                                             @NotNull @RequestParam(value = "page") Integer page,
                                             @NotNull @RequestParam(value = "size") Integer size,
                                             @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/users/{}/tasks: findByUserId() method called.", headers.getHost(), id);
        return taskService.findByUserId(id, PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/users/{}/tasks/{}: findByUserIdAndTaskId() method called.", headers.getHost(), idUser, idTask);
        return taskService.findByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task create(@Valid @RequestBody Task task, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/api/v1/tasks: create() method called:", headers.getHost());
        logger.info("\t" + task);
        return taskService.create(task);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable UUID id, @Valid @RequestBody Task task, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/api/v1/tasks/{}: update() method called:", headers.getHost(), id);
        logger.info("\t" + task);
        taskService.update(id, task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/tasks/{id}")
    public void delete(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/api/v1/tasks/{}: delete() method called.", headers.getHost(), id);
        taskService.delete(id);
    }
}
