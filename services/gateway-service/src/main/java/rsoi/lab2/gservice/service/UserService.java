package rsoi.lab2.gservice.service;

import org.springframework.data.domain.Page;
import rsoi.lab2.gservice.entity.User;
import rsoi.lab2.gservice.model.PageCustom;

public interface UserService {
    User findById(Long id);
    PageCustom<User> findAll(Integer page, Integer size);
    User create(User user);
    void update(User user);
    void delete(Long id);
    User check(User userWithNameEmailPass);
}
