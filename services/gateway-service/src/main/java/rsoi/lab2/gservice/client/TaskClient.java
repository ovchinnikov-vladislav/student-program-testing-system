package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.client.fallback.factory.TaskFallbackFactory;
import rsoi.lab2.gservice.conf.FeignConfiguration;
import rsoi.lab2.gservice.entity.task.Task;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "task-service", configuration = FeignConfiguration.class, fallbackFactory = TaskFallbackFactory.class)
public interface TaskClient {

    @GetMapping(value = "/api/v1/token")
    Map<String, String> getToken(@RequestHeader("Authorization") String authorizationData);

    @GetMapping(value = "/api/v1/tasks")
    PageCustom<Task> findAll(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size,
                             @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/tasks/{id}")
    Optional<Task> findById(@PathVariable UUID id, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{id}/tasks")
    PageCustom<Task> findByUserId(@PathVariable UUID id, @RequestParam(value = "page") Integer page,
                        @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{idUser}/tasks/{idTask}")
    Optional<Task> findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask,
                                         @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/tasks")
    Optional<Task> create(@RequestBody Task task, @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/tasks/{id}")
    void update(@PathVariable UUID id, @RequestBody Task task, @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/tasks/{id}")
    void delete(@PathVariable UUID id, @RequestHeader("Authorization") String token);
}
