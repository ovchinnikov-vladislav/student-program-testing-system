package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignConfiguration;
import rsoi.lab2.gservice.entity.result.Result;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.client.fallback.factory.ResultFallbackFactory;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "result-service", configuration = FeignConfiguration.class, fallbackFactory = ResultFallbackFactory.class)
public interface ResultClient {

    @GetMapping(value = "/api/v1/token")
    Map<String, String> getToken(@RequestHeader("Authorization") String authorizationData);

    @GetMapping(value = "/api/v1/users/{idUser}/tasks/{idTask}/results")
    Optional<Result> findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask,
                                           @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/results")
    PageCustom<Result> findAll(@RequestParam(value = "page") Integer page,
                     @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/tasks/{id}/results")
    PageCustom<Result> findByTaskId(@PathVariable UUID id, @RequestParam(value = "page") Integer page,
                                    @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{id}/results")
    PageCustom<Result> findByUserId(@PathVariable UUID id, @RequestParam(value = "page") Integer page,
                          @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/results")
    Optional<Result> create(@RequestBody Result result, @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/users/{idUser}/tasks/{idTask}/results")
    void update(@PathVariable UUID idUser, @PathVariable UUID idTask, @RequestBody Result result,
                @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/users/{idUser}/tasks/{idTask}/results")
    void delete(@PathVariable UUID idUser, @PathVariable UUID idTask, @RequestHeader("Authorization") String token);

}
