package rsoi.lab2.gservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.entity.result.Result;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.ResultService;
import rsoi.lab2.gservice.service.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class ResultController {

    private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

    @Autowired
    private ResultService resultService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/tasks/{idTask}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result findByUserAndTask(@PathVariable UUID idTask, @RequestHeader HttpHeaders headers, HttpServletRequest req) {
        logger.info("GET http://{}/api/v1/oauth/tasks/{}: findByUserAndTask() method called.", headers.getHost(), idTask);
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(req));
        return resultService.findByUserIdAndTaskId(id, idTask);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/auth/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<Result> findByUser(@NotNull @RequestParam(value = "page") Integer page, @RequestHeader HttpHeaders headers,
                                         @NotNull @RequestParam(value = "size") Integer size, HttpServletRequest req) {
        logger.info("GET http://{}/api/v1/oauth/results: findByUser() method called.", headers.getHost());
        UUID id = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(req));
        return resultService.findByUserId(id, page, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tasks/{id}/results", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageCustom<Result> findByTask(@PathVariable UUID id, @NotNull @RequestParam(value = "page") Integer page,
                               @NotNull @RequestParam(value = "size") Integer size, @RequestHeader HttpHeaders headers) {
        logger.info("GET http://{}/api/v1/tasks/{}/results: findByTask() method called.", headers.getHost(), id);
        return resultService.findByTaskId(id, page, size);
    }
}
