package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignErrorDecoder;
import rsoi.lab2.gservice.entity.Test;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "test-service", configuration = FeignErrorDecoder.class)
public interface TestClient {

    @GetMapping(value = "/tests/{id}")
    Optional<Test> findById(@PathVariable Long id);

    @GetMapping(value = "/users/{idUser}/tests/{idTest}")
    Optional<Test> findByUserIdAndTestId(@PathVariable Long idUser, @PathVariable Long idTest);

    @GetMapping(value = "/tests")
    PageCustom<Test> findAll(@RequestParam(value = "page") Integer page,
                             @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/users/{id}/tests")
    PageCustom<Test> findByUserId(@PathVariable Long id,
                        @RequestParam(value = "page") Integer page,
                        @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/tasks/{id}/tests")
    Optional<Test> findByTaskId(@PathVariable Long id);

    @GetMapping(value = "/users/{idUser}/tasks/{idTask}/tests")
    Optional<Test> findByUserIdAndTaskId(@PathVariable Long idUser, @PathVariable Long idTask);

    @PostMapping(value = "/tests")
    Optional<Test> create(@RequestBody Test test);

    @PutMapping(value = "/tests/{id}")
    void update(@PathVariable Long id, @RequestBody Test test);

    @DeleteMapping(value = "/tests/{id}")
    void delete(@PathVariable Long id);

    @DeleteMapping(value = "/tasks/{id}/tests")
    void deleteByTaskId(@PathVariable Long id);
}
