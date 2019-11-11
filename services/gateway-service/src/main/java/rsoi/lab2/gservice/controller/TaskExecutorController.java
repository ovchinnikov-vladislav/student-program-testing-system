package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.model.ExecuteTask;
import rsoi.lab2.gservice.model.ExecuteTaskRequest;
import rsoi.lab2.gservice.model.ResultTest;
import rsoi.lab2.gservice.service.TaskExecutorService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/gate")
public class TaskExecutorController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskExecutorService taskExecutorService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/tasks/execute", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultTest execute(@Valid @RequestBody ExecuteTaskRequest request, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/gate/tasks/execute: execute() method called.", headers.getHost());
        return taskExecutorService.execute(request);
    }

}
