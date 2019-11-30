package rsoi.lab2.rservice.controller;

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
import rsoi.lab2.rservice.entity.Result;
import rsoi.lab2.rservice.exception.HttpNotValueOfParameterException;
import rsoi.lab2.rservice.service.ResultService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/")
@Validated
public class ResultController {

    private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

    @Autowired
    private ResultService resultService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/results")
    public Result findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tasks/{}/results: findByUserIdAndTaskId() method called.", headers.getHost(), idUser, idTask);
        return resultService.findByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Result> findAll(@NotNull @RequestParam(value = "page") Integer page,
                                @NotNull @RequestParam(value = "size") Integer size,
                                @NotNull @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/results: findAll() method called.", headers.getHost());
        return resultService.findAll(PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Result> findByTaskId(@PathVariable UUID id,
                                     @NotNull @RequestParam(value = "page") Integer page,
                                     @NotNull @RequestParam(value = "size") Integer size,
                                     @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks/{}/results: findByTaskId() method called.", headers.getHost(), id);
        return resultService.findByTaskId(id, PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Result> findByUserId(@PathVariable UUID id,
                                     @NotNull @RequestParam(value = "page") Integer page,
                                     @NotNull @RequestParam(value = "size") Integer size,
                                     @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/results: findByUserId() method called.", headers.getHost(), id);
        return resultService.findByUserId(id, PageRequest.of(page, size));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result create(@Valid @RequestBody Result result, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/results: create() method called:", headers.getHost());
        logger.info("\t" + result);
        return resultService.create(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/users/{idUser}/tasks/{idTask}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable UUID idUser, @PathVariable UUID idTask, @Valid @RequestBody Result result, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/users/{}/tasks/{}/results: update() method called:", headers.getHost(), idUser, idTask);
        result.setIdUser(idUser);
        result.setIdTask(idTask);
        logger.info("\t" + result);
        resultService.update(result);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/users/{idUser}/tasks/{idTask}/results")
    public void delete(@PathVariable UUID idUser, @PathVariable UUID idTask, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/users/{}/tasks/{}/results: delete() method called.", headers.getHost(), idUser, idTask);
        resultService.delete(idUser, idTask);
    }

}
