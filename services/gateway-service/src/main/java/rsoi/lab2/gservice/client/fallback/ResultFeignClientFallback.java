package rsoi.lab2.gservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.gservice.client.ResultClient;
import rsoi.lab2.gservice.entity.result.Result;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ResultFeignClientFallback implements ResultClient {

    private static final Logger log = LoggerFactory.getLogger(ResultFeignClientFallback.class);
    private final Throwable cause;

    public ResultFeignClientFallback(Throwable cause) {
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
    public Optional<Result> findByUserIdAndTaskId(UUID idUser, UUID idTask, String token) {
        log.error("findByUserIdAndTaskId() method called.");
        log.error(cause.getMessage());
        Result fallback = new Result();
        fallback.setIdTask(new UUID(0, 0));
        fallback.setIdUser(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public PageCustom<Result> findAll(Integer page, Integer size, String token) {
        log.error("findAll() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public PageCustom<Result> findByTaskId(UUID id, Integer page, Integer size, String token) {
        log.error("findByTaskId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public PageCustom<Result> findByUserId(UUID id, Integer page, Integer size, String token) {
        log.error("findByUserId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<Result> create(Result fallback, String token) {
        log.error("create() method called.");
        log.error(cause.getMessage());
        fallback.setIdTask(new UUID(0, 0));
        fallback.setIdUser(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public void update(UUID idUser, UUID idTask, Result result, String token) {
        log.error("update() method called.");
        log.error(cause.getMessage());
    }

    @Override
    public void delete(UUID idUser, UUID idTask, String token) {
        log.error("delete() method called.");
        log.error(cause.getMessage());
    }
}
