package rsoi.lab2.taskservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.taskservice.entity.Task;
import rsoi.lab2.taskservice.model.SomeTasksModel;
import rsoi.lab2.taskservice.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SomeTasksModel> getAll(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks: getAll() method called.", headers.getHost());
        return (page != null && size != null) ? taskService.getAll(PageRequest.of(page, size)) : taskService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task getById(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks/{}: getById() method called.", headers.getHost(), id);
        return taskService.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SomeTasksModel> getByUserId(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tasks: getByUserId() method called.", headers.getHost(), id);
        return (page != null && size != null) ? taskService.getByUserId(id, PageRequest.of(page, size)) : taskService.getByUserId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task getByUserIdAndTaskId(@PathVariable Long idUser, @PathVariable Long idTask, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tasks/{}: getByUserIdAndTaskId() method called.", headers.getHost(), idUser, idTask);
        return taskService.getByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task create(@Valid @RequestBody Task task, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/tasks: create() method called:", headers.getHost());
        logger.info("\t" + task);
        return taskService.create(task);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable Long id, @Valid @RequestBody Task task, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/tasks/{}: update() method called:", headers.getHost(), id);
        logger.info("\t" + task);
        taskService.update(task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/tasks/{id}")
    public void delete(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/tasks/{}: delete() method called.", headers.getHost(), id);
        taskService.delete(id);
    }
}
