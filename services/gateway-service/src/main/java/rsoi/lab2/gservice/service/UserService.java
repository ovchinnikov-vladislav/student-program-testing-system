package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.User;

public interface UserService {
    User findById(Long id);
    User[] findAll(Integer page, Integer size);
    User create(User user);
    void update(Long id, User user);
    void delete(Long id);
    User check(User userWithNameEmailPass);
}
