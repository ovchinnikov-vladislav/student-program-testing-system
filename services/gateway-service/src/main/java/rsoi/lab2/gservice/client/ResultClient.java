package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignConfiguration;
import rsoi.lab2.gservice.entity.Result;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.client.fallback.factory.ResultFallbackFactory;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "result-service", configuration = FeignConfiguration.class, fallbackFactory = ResultFallbackFactory.class)
public interface ResultClient {

    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/results")
    Optional<Result> findByUserIdAndTaskId(@PathVariable UUID idUser,
                                           @PathVariable UUID idTask);

    @GetMapping(value = "/results")
    PageCustom<Result> findAll(@RequestParam(value = "page") Integer page,
                     @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/tasks/{id}/results")
    PageCustom<Result> findByTaskId(@PathVariable UUID id,
                                    @RequestParam(value = "page") Integer page,
                                    @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/users/{id}/results")
    PageCustom<Result> findByUserId(@PathVariable UUID id,
                          @RequestParam(value = "page") Integer page,
                          @RequestParam(value = "size") Integer size);

    @PostMapping(value = "/results")
    Optional<Result> create(@RequestBody Result result);

    @PutMapping(value = "/users/{idUser}/tasks/{idTask}/results")
    void update(@PathVariable UUID idUser, @PathVariable UUID idTask, @RequestBody Result result);

    @DeleteMapping(value = "/users/{idUser}/tasks/{idTask}/results")
    void delete(@PathVariable UUID idUser, @PathVariable UUID idTask);

}
