package rsoi.lab2.gservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.gservice.client.UserClient;
import rsoi.lab2.gservice.entity.User;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.Optional;
import java.util.UUID;

public class UserFeignClientFallback implements UserClient {
    private static final Logger logger = LoggerFactory.getLogger(UserFeignClientFallback.class);
    private final Throwable cause;

    public UserFeignClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public PageCustom<User> findAll(Integer page, Integer size) {
        logger.error("findAll() method called.");
        logger.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<User> findById(UUID id) {
        logger.error("findById() method called.");
        logger.error(cause.getMessage());
        User fallback = new User();
        fallback.setIdUser(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<User> create(User fallback) {
        logger.error("create() method called.");
        logger.error(cause.getMessage());
        fallback.setIdUser(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<User> check(User userWithNameEmailPass) {
        logger.error("check() method called.");
        logger.error(cause.getMessage());
        User fallback = new User();
        fallback.setIdUser(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public void update(UUID id, User user) {
        logger.error("update() method called.");
        logger.error(cause.getMessage());
    }

    @Override
    public void delete(UUID id) {
        logger.error("delete() method called.");
        logger.error(cause.getMessage());
    }
}
