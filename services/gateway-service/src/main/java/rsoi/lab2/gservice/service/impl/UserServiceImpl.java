package rsoi.lab2.gservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.UserClient;
import rsoi.lab2.gservice.entity.User;
import rsoi.lab2.gservice.exception.feign.ClientBadResponseExceptionWrapper;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.UserService;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserClient userClient;

    @Override
    public PageCustom<User> findAll(Integer page, Integer size) {
        logger.info("findAll() method called:");
        PageCustom<User> users = userClient.findAll(page, size);
        if (users == null)
            throw new ServiceAccessException("User service unavailable.");
        logger.info("\t" + users.getContent());
        return users;
    }

    @Override
    public User findById(UUID id) {
        logger.info("findUserById() method called:");
        User user = userClient.findById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be checked"));

        UUID zeroUUID = new UUID(0, 0);
        if (user.getIdUser().equals(zeroUUID))
            throw new ServiceAccessException("User service unavailable.");

        logger.info("\t" + user);
        return user;
    }

    @Override
    public User create(User user) {
        logger.info("create() method called:");
        User result = userClient.create(user)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be created"));

        UUID zeroUUID = new UUID(0, 0);
        if (result.getIdUser().equals(zeroUUID))
            throw new ServiceAccessException("User service unavailable.");
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void update(UUID id, User user) {
        logger.info("update() method called.");
        User checkUser = userClient.findById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be checked"));

        UUID zeroUUID = new UUID(0, 0);
        if (checkUser.getIdUser().equals(zeroUUID))
            throw new ServiceAccessException("User service unavailable.");

        user.setIdUser(id);

        userClient.update(id, user);
    }

    @Override
    public void delete(UUID id) {
        logger.info("delete() method called.");
        User user = userClient.findById(id)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be checked"));

        UUID zeroUUID = new UUID(0, 0);
        if (user.getIdUser().equals(zeroUUID))
            throw new ServiceAccessException("User service unavailable.");

        userClient.delete(id);
    }

    @Override
    public User check(User userWithNameEmailPass) {
        logger.info("check() method called:");
        User user = userClient.check(userWithNameEmailPass)
                .orElseThrow(() -> new HttpCanNotCreateException("User could not be checked"));

        UUID zeroUUID = new UUID(0, 0);
        if (user.getIdUser().equals(zeroUUID))
            throw new ServiceAccessException("User service unavailable.");

        logger.info("\t" + user);
        return user;
    }

}
