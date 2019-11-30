package rsoi.lab2.gservice.service;

import org.springframework.data.domain.Page;
import rsoi.lab2.gservice.entity.User;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.UUID;

public interface UserService {
    User findById(UUID id);
    PageCustom<User> findAll(Integer page, Integer size);
    User create(User user);
    void update(User user);
    void delete(UUID id);
    User check(User userWithNameEmailPass);
}
