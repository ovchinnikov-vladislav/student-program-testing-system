package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.Result;
import rsoi.lab2.gservice.service.ResultService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/gate")
public class ResultController {

    private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

    @Autowired
    private ResultService resultService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result getById(@PathVariable Long idUser, @PathVariable Long idTask, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/users/{}/tasks/{}: getById() method called.", headers.getHost(), idUser, idTask);
        return resultService.findByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result[] getByUser(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/users/{}/results: getByUserId() method called.", headers.getHost(), id);
        return resultService.findByUserId(id, page, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result[] getByTaskId(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/gate/tasks/{}/results: getByTaskId() method called.", headers.getHost(), id);
        return resultService.findByTaskId(id, page, size);
    }
}
