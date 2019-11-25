package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.entity.Test;
import rsoi.lab2.gservice.exception.HttpNotValueOfParameterException;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.TaskService;
import rsoi.lab2.gservice.service.TestService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequestMapping(value = "/gate")
@Validated
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task findById(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/tasks/{}: findById() method called.", headers.getHost(), id);
        return taskService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task findByUserIdAndTaskId(@PathVariable Long idUser, @PathVariable Long idTask,
                                     @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/users/{}/tasks/{}: findByUserIdAndTaskId() method called.", headers.getHost(), idUser, idTask);
        return taskService.findByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<Task> findAll(@NotNull @RequestParam(value = "page") Integer page, @NotNull @RequestParam(value = "size") Integer size,
                                    @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/tasks: findAll() method called.", headers.getHost());
        return taskService.findAll(page, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<Task> findByUserId(@PathVariable Long id, @NotNull @RequestParam(value = "page") Integer page,
                               @NotNull @RequestParam(value = "size") Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/users/{}/tasks: findByUserId() method called.", headers.getHost(), id);
        return taskService.findByUserId(id, page, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users/{id}/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task create(@PathVariable Long id, @Valid @RequestBody Task task, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/gate/users/{}/tasks: create() method called.", headers.getHost(), id);
        task.setIdUser(id);
        task.setCreateDate(new Date());
        return taskService.create(task);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/users/{idUser}/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable Long idUser, @PathVariable Long idTask,
                       @Valid @RequestBody Task task, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/gate/users/{}/tasks/{}: update() method called.", headers.getHost(), idUser, idTask);
        task.setIdUser(idUser);
        task.setIdTask(idTask);
        task.setCreateDate(new Date());
        taskService.update(task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/users/{idUser}/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable Long idUser, @PathVariable Long idTask, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/gate/users/{}/tasks/{}: delete() method called.", headers.getHost(), idUser, idTask);
        taskService.delete(idTask);
    }
}
