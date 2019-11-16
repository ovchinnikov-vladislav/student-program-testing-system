package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.entity.Test;
import rsoi.lab2.gservice.service.TaskService;
import rsoi.lab2.gservice.service.TestService;

@RestController
@RequestMapping(value = "/gate")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task getById(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/tasks/{}: getById() method called.", headers.getHost(), id);
        return taskService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task getByUserIdAndTaskId(@PathVariable Long idUser, @PathVariable Long idTask, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/users/{}/tasks/{}: getByUserIdAndTaskId() method called.", headers.getHost(), idUser, idTask);
        return taskService.findByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task[] getAll(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/tasks: getAll() method called.", headers.getHost());
        return taskService.findAll(page, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task[] getByUserId(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/users/{}/tasks: getByUserId() method called.", headers.getHost(), id);
        return taskService.findByUserId(id, page, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users/{id}/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task create(@PathVariable Long id, @RequestBody Task task, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/gate/users/{}/tasks: create() method called.", headers.getHost(), id);
        task.setIdUser(id);
        return taskService.create(task);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/users/{idUser}/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable Long idUser, @PathVariable Long idTask, @RequestBody Task task, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/gate/users/{}/tasks/{}: update() method called.", headers.getHost(), idUser, idTask);
        task.setIdUser(idUser);
        task.setIdTask(idTask);
        taskService.update(idTask, task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/users/{idUser}/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(@PathVariable Long idUser, @PathVariable Long idTask, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/gate/users/{}/tasks/{}: delete() method called.", headers.getHost(), idUser, idTask);
        taskService.delete(idTask);
    }
}
