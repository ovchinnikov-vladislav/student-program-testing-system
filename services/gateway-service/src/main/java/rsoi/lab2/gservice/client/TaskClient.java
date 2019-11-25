package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignErrorDecoder;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.Optional;

@FeignClient(name = "task-service", configuration = FeignErrorDecoder.class)
public interface TaskClient {

    @GetMapping(value = "/tasks")
    PageCustom<Task> findAll(@RequestParam(value = "page") Integer page,
                             @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/tasks/{id}")
    Optional<Task> findById(@PathVariable Long id);

    @GetMapping(value = "/users/{id}/tasks")
    PageCustom<Task> findByUserId(@PathVariable Long id,
                        @RequestParam(value = "page") Integer page,
                        @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/users/{idUser}/tasks/{idTask}")
    Optional<Task> findByUserIdAndTaskId(@PathVariable Long idUser, @PathVariable Long idTask);

    @PostMapping(value = "/tasks")
    Optional<Task> create(@RequestBody Task task);

    @PutMapping(value = "/tasks/{id}")
    void update(@PathVariable Long id, @RequestBody Task task);

    @DeleteMapping(value = "/tasks/{id}")
    void delete(@PathVariable Long id);

}
