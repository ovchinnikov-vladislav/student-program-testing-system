package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.client.fallback.factory.TestFallbackFactory;
import rsoi.lab2.gservice.conf.FeignConfiguration;
import rsoi.lab2.gservice.entity.test.Test;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "test-service", configuration = FeignConfiguration.class, fallbackFactory = TestFallbackFactory.class)
public interface TestClient {

    @GetMapping(value = "/api/v1/token")
    Map<String, String> getToken(@RequestHeader("Authorization") String authorizationData);

    @GetMapping(value = "/api/v1/tests/{id}")
    Optional<Test> findById(@PathVariable UUID id, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{idUser}/tests/{idTest}")
    Optional<Test> findByUserIdAndTestId(@PathVariable UUID idUser, @PathVariable UUID idTest,
                                         @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/tests")
    PageCustom<Test> findAll(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size,
                             @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{id}/tests")
    PageCustom<Test> findByUserId(@PathVariable UUID id, @RequestParam(value = "page") Integer page,
                        @RequestParam(value = "size") Integer size, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/tasks/{id}/tests")
    Optional<Test> findByTaskId(@PathVariable UUID id, @RequestHeader("Authorization") String token);

    @GetMapping(value = "/api/v1/users/{idUser}/tasks/{idTask}/tests")
    Optional<Test> findByUserIdAndTaskId(@PathVariable UUID idUser, @PathVariable UUID idTask,
                                         @RequestHeader("Authorization") String token);

    @PostMapping(value = "/api/v1/tests")
    Optional<Test> create(@RequestBody Test test, @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/v1/tests/{id}")
    void update(@PathVariable UUID id, @RequestBody Test test, @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/tests/{id}")
    void delete(@PathVariable UUID id, @RequestHeader("Authorization") String token);

    @DeleteMapping(value = "/api/v1/tasks/{id}/tests")
    void deleteByTaskId(@PathVariable UUID id, @RequestHeader("Authorization") String token);
}
