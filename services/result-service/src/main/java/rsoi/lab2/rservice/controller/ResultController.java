package rsoi.lab2.rservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.rservice.entity.Result;
import rsoi.lab2.rservice.service.ResultService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class ResultController {

    private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

    @Autowired
    private ResultService resultService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/results")
    public Result getByUserIdAndTaskId(@PathVariable Long idUser, @PathVariable Long idTask, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/tasks/{}/results: getByUserIdAndTaskId() method called.", headers.getHost(), idUser, idTask);
        return resultService.getByUserIdAndTaskId(idUser, idTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Result> getAll(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/results: getAll() method called.", headers.getHost());
        return (page != null && size != null) ? resultService.getAll(PageRequest.of(page, size)) : resultService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Result> getByTaskId(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/tasks/{}/results: getByTaskId() method called.", headers.getHost(), id);
        return (page != null && size != null) ? resultService.getByTaskId(id, PageRequest.of(page, size)) : resultService.getByTaskId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Result> getByUserId(@PathVariable Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/users/{}/results: getByUserId() method called.", headers.getHost(), id);
        return (page != null && size != null) ? resultService.getByUserId(id, PageRequest.of(page, size)) : resultService.getByUserId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result create(@Valid @RequestBody Result result, @RequestHeader HttpHeaders headers) {
        logger.info("POST http://{}/results: create() method called:", headers.getHost());
        logger.info("\t" + result);
        return resultService.add(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/users/{idUser}/tasks/{idTask}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable Long idUser, @PathVariable Long idTask, @Valid @RequestBody Result result, @RequestHeader HttpHeaders headers) {
        logger.info("PUT http://{}/users/{}/tasks/{}/results: update() method called:", headers.getHost(), idUser, idTask);
        logger.info("\t" + result);
        resultService.update(result);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/users/{idUser}/tasks/{idTask}/results")
    public void delete(@PathVariable Long idUser, @PathVariable Long idTask, @RequestHeader HttpHeaders headers) {
        logger.info("DELETE http://{}/users/{}/tasks/{}/results: delete() method called.", headers.getHost(), idUser, idTask);
        resultService.delete(idUser, idTask);
    }

}
