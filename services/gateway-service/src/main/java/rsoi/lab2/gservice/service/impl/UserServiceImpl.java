package rsoi.lab2.gservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.UserClient;
import rsoi.lab2.gservice.entity.User;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.service.UserService;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserClient userClient;

    @Override
    @HystrixCommand(fallbackMethod = "getAllFallback")
    public User[] findAll(Integer page, Integer size) {
        logger.info("getAll() method called:");
        User[] users = userClient.findAll(page, size);
        logger.info("\t" + Arrays.toString(users));
        return users;
    }

    private User[] getAllFallback(Integer page, Integer size) {
        logger.info("getAllFallback() method called:");
        User[] users = new User[0];
        logger.info("\t" + Arrays.toString(users));
        return users;
    }

    @Override
    public User findById(Long id) {
        logger.info("getUserById() method called:");
        User user = userClient.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Not found User by id = " + id));
        logger.info("\t" + user);
        return user;
    }

    @Override
    public User create(User user) {
        logger.info("create() method called:");
        User result = userClient.create(user)
                .orElseThrow(() -> new HttpCanNotCreateException("User cannot create"));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void update(Long id, User user) {
        logger.info("update() method called.");
        userClient.update(id, user);
    }

    @Override
    public void delete(Long id) {
        logger.info("delete() method called.");
        userClient.delete(id);
    }

    @Override
    public User check(User userWithNameEmailPass) {
        logger.info("check() method called:");
        User user = userClient.check(userWithNameEmailPass)
                .orElseThrow(() -> new HttpCanNotCreateException("User cannot check"));
        logger.info("\t" + user);
        return user;
    }

}
