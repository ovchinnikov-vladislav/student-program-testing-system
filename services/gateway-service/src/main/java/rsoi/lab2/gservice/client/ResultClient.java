package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignErrorDecoder;
import rsoi.lab2.gservice.entity.Result;

import java.util.Optional;

@FeignClient(name = "result-service", configuration = FeignErrorDecoder.class)
public interface ResultClient {

    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/results")
    Optional<Result> findByUserIdAndTaskId(@PathVariable Long idUser,
                                           @PathVariable Long idTask);

    @GetMapping(value = "/results")
    Result[] findAll(@RequestParam(value = "page", required = false) Integer page,
                     @RequestParam(value = "size", required = false) Integer size);

    @GetMapping(value = "/tasks/{id}/results")
    Result[] findByTaskId(@PathVariable Long id,
                          @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "size", required = false) Integer size);

    @GetMapping(value = "/users/{id}/results")
    Result[] findByUserId(@PathVariable Long id,
                          @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "size", required = false) Integer size);

    @PostMapping(value = "/results")
    Optional<Result> create(@RequestBody Result result);

    @PutMapping(value = "/users/{idUser}/tasks/{idTask}/results")
    void update(@PathVariable Long idUser, @PathVariable Long idTask, @RequestBody Result result);

    @DeleteMapping(value = "/users/{idUser}/tasks/{idTask}/results")
    void delete(@PathVariable Long idUser, @PathVariable Long idTask);

}
