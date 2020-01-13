package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.completedtask.CompletedTask;
import rsoi.lab2.gservice.model.executetask.ExecuteTaskRequest;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.result.ResultTest;
import rsoi.lab2.gservice.service.TaskExecutorService;
import rsoi.lab2.gservice.service.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class TaskExecutorController {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorController.class);

    @Autowired
    private TaskExecutorService taskExecutorService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/tasks/{idTask}/completed_tasks")
    public PageCustom<CompletedTask> findCompletedTaskByTask(@PathVariable UUID idTask,
                                                             @NotNull @RequestParam(value = "page") Integer page,
                                                             @NotNull @RequestParam(value = "size") Integer size,
                                                             @RequestHeader HttpHeaders headers,
                                                             HttpServletRequest request) {
        logger.info("GET http://{}/api/v1/oauth/tasks/{}/completed_tasks: findCompletedTask() method called.", headers.getHost(), idTask);
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
        return taskExecutorService.findByUserIdAndTaskId(id, idTask, page, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/auth/tasks/execute", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultTest execute(@Valid @RequestBody ExecuteTaskRequest request, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/api/v1/oauth/tasks/execute: execute() method called.", headers.getHost());
        return taskExecutorService.execute(request);
    }

}
