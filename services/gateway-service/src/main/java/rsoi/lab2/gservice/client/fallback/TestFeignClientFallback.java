package rsoi.lab2.gservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.gservice.client.TestClient;
import rsoi.lab2.gservice.entity.test.Test;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TestFeignClientFallback implements TestClient {
    private static final Logger log = LoggerFactory.getLogger(TestFeignClientFallback.class);
    private final Throwable cause;

    public TestFeignClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public Map<String, String> getToken(String authorizationData) {
        log.error("getToken() method called.");
        log.error(cause.getMessage());
        HashMap<String, String> token = new HashMap<>();
        token.put("access_token", null);
        return token;
    }

    @Override
    public Optional<Test> findById(UUID id, String token) {
        log.error("findById() method called.");
        log.error(cause.getMessage());
        Test fallback = new Test();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<Test> findByUserIdAndTestId(UUID idUser, UUID idTest, String token) {
        log.error("findByUserIdAndTestId() method called.");
        log.error(cause.getMessage());
        Test fallback = new Test();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public PageCustom<Test> findAll(Integer page, Integer size, String token) {
        log.error("findAll() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public PageCustom<Test> findByUserId(UUID id, Integer page, Integer size, String token) {
        log.error("findByUserId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<Test> findByTaskId(UUID id, String token) {
        log.error("findByTaskId() method called.");
        log.error(cause.getMessage());
        Test fallback = new Test();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<Test> findByUserIdAndTaskId(UUID idUser, UUID idTask, String token) {
        log.error("findByUserIdAndTaskId() method called.");
        log.error(cause.getMessage());
        Test fallback = new Test();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<Test> create(Test fallback, String token) {
        log.error("create() method called.");
        log.error(cause.getMessage());
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public void update(UUID id, Test test, String token) {
        log.error("update() method called.");
        log.error(cause.getMessage());
    }

    @Override
    public void delete(UUID id, String token) {
        log.error("delete() method called.");
        log.error(cause.getMessage());
    }

    @Override
    public void deleteByTaskId(UUID id, String token) {
        log.error("deleteByTaskId() method called.");
        log.error(cause.getMessage());
    }
}
