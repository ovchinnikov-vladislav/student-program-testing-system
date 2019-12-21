package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignConfiguration;
import rsoi.lab2.gservice.entity.User;
import rsoi.lab2.gservice.client.fallback.factory.UserFallbackFactory;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "user-service", configuration = FeignConfiguration.class, fallbackFactory = UserFallbackFactory.class)
public interface UserClient {

    @GetMapping(value = "/users")
    PageCustom<User> findAll(@RequestParam(value = "page") Integer page,
                             @RequestParam(value = "size") Integer size);

    @GetMapping(value = "/users/{id}")
    Optional<User> findById(@PathVariable UUID id);

    @PostMapping(value = "/users")
    Optional<User> create(@RequestBody User user);

    @PostMapping(value = "/users/check")
    Optional<User> check(@RequestBody User userWithNameEmailPass);

    @PutMapping(value = "/users/{id}")
    void update(@PathVariable UUID id, @RequestBody User user);

    @DeleteMapping(value = "/users/{id}")
    void delete(@PathVariable UUID id);

}
