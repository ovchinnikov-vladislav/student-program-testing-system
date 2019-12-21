package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.client.fallback.factory.TaskExecutorFallbackFactory;
import rsoi.lab2.gservice.conf.FeignConfiguration;
import rsoi.lab2.gservice.entity.CompletedTask;
import rsoi.lab2.gservice.model.ExecuteTask;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.ResultWrapper;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "task-executor-service", configuration = FeignConfiguration.class, fallbackFactory = TaskExecutorFallbackFactory.class)
public interface TaskExecutorClient {

    @GetMapping(value = "/completed_tasks/{id}")
    Optional<CompletedTask> findById(@PathVariable UUID id);

    @GetMapping(value = "/users/{idUser}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByUserIdAndCompletedTaskId(@PathVariable UUID idUser, @PathVariable UUID idCompletedTask);

    @GetMapping(value = "/tasks/{idTask}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByTaskIdAndCompletedTaskId(@PathVariable UUID idTask, @PathVariable UUID idCompletedTask);

    @GetMapping(value = "/tests/{idTest}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByTestIdAndCompletedTaskId(@PathVariable UUID idTest, @PathVariable UUID idCompletedTask);

    @GetMapping(value = "/completed_tasks")
    PageCustom<CompletedTask> findAll(@RequestParam(value = "page") Integer page,
                                      @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/users/{id}/completed_tasks")
    PageCustom<CompletedTask> findByUserId(@PathVariable UUID id,
                                 @RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/tasks/{id}/completed_tasks")
    PageCustom<CompletedTask> findByTaskId(@PathVariable UUID id,
                                 @RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/tests/{id}/completed_tasks")
    PageCustom<CompletedTask> findByTestId(@PathVariable UUID id,
                                 @RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/completed_tasks")
    PageCustom<CompletedTask> findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask,
                                          @RequestParam(value = "page") Integer page,
                                          @RequestParam(value = "size") Integer size);

    @PostMapping(value = "/completed_tasks")
    Optional<CompletedTask> create(@RequestBody CompletedTask completedTask);

    @PutMapping(value = "/completed_tasks/{id}")
    void update(@PathVariable UUID id, @RequestBody CompletedTask completedTask);

    @DeleteMapping(value = "/completed_tasks/{id}")
    void delete(@PathVariable UUID id);

    @PostMapping(value = "/tasks/execute")
    Optional<ResultWrapper> execute(@RequestBody ExecuteTask executeTask);
}
