package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.task.Task;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.TaskService;
import rsoi.lab2.gservice.service.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task findById(@PathVariable UUID id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/tasks/{}: findById() method called.", headers.getHost(), id);
        return taskService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task findByUserIdAndTaskId(@PathVariable UUID idTask, @RequestHeader HttpHeaders headers,
                                      HttpServletRequest request) {
        logger.info("GET http://{}/api/v1/oauth/tasks/{}: findByUserIdAndTaskId() method called.", headers.getHost(), idTask);
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
        return taskService.findByUserIdAndTaskId(id, idTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<Task> findAll(@NotNull @RequestParam(value = "page") Integer page, @NotNull @RequestParam(value = "size") Integer size,
                                    @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/tasks: findAll() method called.", headers.getHost());
        return taskService.findAll(page, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<Task> findByUserId(@NotNull @RequestParam(value = "page") Integer page, @NotNull @RequestParam(value = "size") Integer size,
                                         @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        logger.info("GET http://{}/api/v1/oauth/tasks: findByUserId() method called.", headers.getHost());
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
        return taskService.findByUserId(id, page, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/auth/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task create(@Valid @RequestBody Task task, @RequestHeader HttpHeaders headers, HttpServletRequest request) throws IOException {
        logger.info("POST http://{}/api/v1/oauth/tasks: create() method called.", headers.getHost());
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
        return taskService.create(id, task);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/auth/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable UUID idTask, @Valid @RequestBody Task task, @RequestHeader HttpHeaders headers,
                       HttpServletRequest request) throws IOException {
        logger.info("PUT http://{}/api/v1/oauth/tasks/{}: update() method called.", headers.getHost(), idTask);
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
        taskService.update(id, idTask, task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/auth/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable UUID idTask, @RequestHeader HttpHeaders headers) throws IOException {
        logger.info("DELETE http://{}/api/v1/oauth/tasks/{}: delete() method called.", headers.getHost(), idTask);
        taskService.delete(idTask);
    }
}
