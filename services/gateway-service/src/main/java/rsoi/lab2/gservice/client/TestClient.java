package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignErrorDecoder;
import rsoi.lab2.gservice.entity.Test;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "test-service", configuration = FeignErrorDecoder.class)
public interface TestClient {

    @GetMapping(value = "/tests/{id}")
    Optional<Test> findById(@PathVariable UUID id);

    @GetMapping(value = "/users/{idUser}/tests/{idTest}")
    Optional<Test> findByUserIdAndTestId(@PathVariable UUID idUser, @PathVariable UUID idTest);

    @GetMapping(value = "/tests")
    PageCustom<Test> findAll(@RequestParam(value = "page") Integer page,
                             @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/users/{id}/tests")
    PageCustom<Test> findByUserId(@PathVariable UUID id,
                        @RequestParam(value = "page") Integer page,
                        @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/tasks/{id}/tests")
    Optional<Test> findByTaskId(@PathVariable UUID id);

    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/tests")
    Optional<Test> findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask);

    @PostMapping(value = "/tests")
    Optional<Test> create(@RequestBody Test test);

    @PutMapping(value = "/tests/{id}")
    void update(@PathVariable UUID id, @RequestBody Test test);

    @DeleteMapping(value = "/tests/{id}")
    void delete(@PathVariable UUID id);

    @DeleteMapping(value = "/tasks/{id}/tests")
    void deleteByTaskId(@PathVariable UUID id);
}
