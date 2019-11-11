package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignErrorDecoder;
import rsoi.lab2.gservice.entity.CompletedTask;
import rsoi.lab2.gservice.model.ExecuteTask;
import rsoi.lab2.gservice.model.ResultTest;

import java.util.Optional;

@FeignClient(name = "task-executor-service", configuration = FeignErrorDecoder.class)
public interface TaskExecutorClient {

    @GetMapping(value = "/completed_tasks/{id}")
    Optional<CompletedTask> findById(@PathVariable Long id);

    @GetMapping(value = "/users/{idUser}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByUserIdAndCompletedTaskId(@PathVariable Long idUser, @PathVariable Long idCompletedTask);

    @GetMapping(value = "/tasks/{idTask}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByTaskIdAndCompletedTaskId(@PathVariable Long idTask, @PathVariable Long idCompletedTask);

    @GetMapping(value = "/tests/{idTest}/completed_tasks/{idCompletedTask}")
    Optional<CompletedTask> findByTestIdAndCompletedTaskId(@PathVariable Long idTest, @PathVariable Long idCompletedTask);

    @GetMapping(value = "/completed_tasks")
    CompletedTask[] findAll(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "size", required = false) Integer size);

    @GetMapping(value = "/users/{id}/completed_tasks")
    CompletedTask[] findByUserId(@PathVariable Long id,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size);

    @GetMapping(value = "/tasks/{id}/completed_tasks")
    CompletedTask[] findByTaskId(@PathVariable Long id,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "size", required = false) Integer size);

    @GetMapping(value = "/tests/{id}/completed_tasks")
    CompletedTask[] findByTestId(@PathVariable Long id,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "size", required = false) Integer size);

    @PostMapping(value = "/completed_tasks")
    Optional<CompletedTask> create(@RequestBody CompletedTask completedTask);

    @PutMapping(value = "/completed_tasks/{id}")
    void update(@PathVariable Long id, @RequestBody CompletedTask completedTask);

    @DeleteMapping(value = "/completed_tasks/{id}")
    void delete(@PathVariable Long id);

    @PostMapping(value = "/tasks/execute")
    Optional<ResultTest> execute(@RequestBody ExecuteTask executeTask);
}
