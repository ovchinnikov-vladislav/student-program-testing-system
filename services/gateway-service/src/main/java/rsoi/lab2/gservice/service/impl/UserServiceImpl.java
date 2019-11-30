package rsoi.lab2.gservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.UserClient;
import rsoi.lab2.gservice.entity.User;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserClient userClient;

    @Override
    @HystrixCommand(fallbackMethod = "getAllFallback")
    public PageCustom<User> findAll(Integer page, Integer size) {
        logger.info("getAll() method called:");
        PageCustom<User> users = userClient.findAll(page, size);
        logger.info("\t" + users.getContent());
        return users;
    }

    private PageCustom<User> getAllFallback(Integer page, Integer size) {
        logger.info("getAllFallback() method called:");
        PageCustom<User> pages = new PageCustom<>(new ArrayList<>(), PageRequest.of(page, size), 0);
        logger.info("\t" + null);
        return pages;
    }

    @Override
    public User findById(UUID id) {
        logger.info("getUserById() method called:");
        User user = userClient.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("User could not be found with id = " + id));
        logger.info("\t" + user);
        return user;
    }

    @Override
    public User create(User user) {
        logger.info("create() method called:");
        User result = userClient.create(user)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be created"));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void update(User user) {
        logger.info("update() method called.");
        userClient.update(user.getIdUser(), user);
    }

    @Override
    public void delete(UUID id) {
        logger.info("delete() method called.");
        userClient.delete(id);
    }

    @Override
    public User check(User userWithNameEmailPass) {
        logger.info("check() method called:");
        User user = userClient.check(userWithNameEmailPass)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be checked"));
        logger.info("\t" + user);
        return user;
    }

}
