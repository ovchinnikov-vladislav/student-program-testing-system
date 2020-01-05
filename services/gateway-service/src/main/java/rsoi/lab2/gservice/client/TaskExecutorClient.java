package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.client.fallback.factory.TaskExecutorFallbackFactory;
import rsoi.lab2.gservice.conf.FeignConfiguration;
import rsoi.lab2.gservice.entity.completedtask.CompletedTask;
import rsoi.lab2.gservice.model.executetask.ExecuteTask;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.result.ResultWrapper;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "task-executor-service", configuration = FeignConfiguration.class, fallbackFactory = TaskExecutorFallbackFactory.class)
public interface TaskExecutorClient {

    @GetMapping(value = "/api/v1/token")
    Map<String, String> getToken(@RequestHeader("Authorization") String authorizationData);

    @GetMapping(value = "/api/v1/completed_tasks/{id}")
    Optional<CompletedTask> findById(@PathVariable UUID id, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{idUser}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByUserIdAndCompletedTaskId(@PathVariable UUID idUser, @PathVariable UUID idCompletedTask,
                                                           @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/tasks/{idTask}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByTaskIdAndCompletedTaskId(@PathVariable UUID idTask, @PathVariable UUID idCompletedTask,
                                                           @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/tests/{idTest}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByTestIdAndCompletedTaskId(@PathVariable UUID idTest, @PathVariable UUID idCompletedTask,
                                                           @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/completed_tasks")
    PageCustom<CompletedTask> findAll(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size,
                                      @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{id}/completed_tasks")
    PageCustom<CompletedTask> findByUserId(@PathVariable UUID id, @RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/tasks/{id}/completed_tasks")
    PageCustom<CompletedTask> findByTaskId(@PathVariable UUID id, @RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/tests/{id}/completed_tasks")
    PageCustom<CompletedTask> findByTestId(@PathVariable UUID id, @RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{idUser}/tasks/{idTask}/completed_tasks")
    PageCustom<CompletedTask> findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask, @RequestParam(value = "page") Integer page,
                                                    @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/completed_tasks")
    Optional<CompletedTask> create(@RequestBody CompletedTask completedTask, @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/completed_tasks/{id}")
    void update(@PathVariable UUID id, @RequestBody CompletedTask completedTask, @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/completed_tasks/{id}")
    void delete(@PathVariable UUID id, @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/tasks/execute")
    Optional<ResultWrapper> execute(@RequestBody ExecuteTask executeTask, @RequestHeader("Authorization") String token);
}
