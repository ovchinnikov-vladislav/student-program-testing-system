package rsoi.lab2.gservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.gservice.conf.FeignErrorDecoder;
import rsoi.lab2.gservice.entity.User;

import java.util.Optional;

@FeignClient(name = "user-service", configuration = FeignErrorDecoder.class)
public interface UserClient {

    @GetMapping(value = "/users")
    User[] findAll(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size);

    @GetMapping(value = "/users/{id}")
    Optional<User> findById(@PathVariable Long id);

    @PostMapping(value = "/users")
    Optional<User> create(@RequestBody User user);

    @PostMapping(value = "/users/check")
    Optional<User> check(@RequestBody User userWithNameEmailPass);

    @PutMapping(value = "/users/{id}")
    void update(@PathVariable Long id, @RequestBody User user);

    @DeleteMapping(value = "/users/{id}")
    void delete(@PathVariable Long id);

}
