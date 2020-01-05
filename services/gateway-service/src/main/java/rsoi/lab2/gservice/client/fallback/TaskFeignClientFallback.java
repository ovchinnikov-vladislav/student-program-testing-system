package rsoi.lab2.gservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.gservice.client.TaskClient;
import rsoi.lab2.gservice.entity.task.Task;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TaskFeignClientFallback implements TaskClient {

    private static final Logger log = LoggerFactory.getLogger(TaskFeignClientFallback.class);
    private final Throwable cause;

    public TaskFeignClientFallback(Throwable cause) {
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
    public PageCustom<Task> findAll(Integer page, Integer size, String token) {
        log.error("findAll() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<Task> findById(UUID id, String token) {
        log.error("findById() method called.");
        log.error(cause.getMessage());
        Task fallback = new Task();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    public PageCustom<Task> findByUserId(UUID id, Integer page, Integer size, String token) {
        log.error("findByUserId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<Task> findByUserIdAndTaskId(UUID idUser, UUID idTask, String token) {
        log.error("findByUserIdAndTaskId() method called.");
        log.error(cause.getMessage());
        Task fallback = new Task();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<Task> create(Task fallback, String token) {
        log.error("create() method called.");
        log.error(cause.getMessage());
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public void update(UUID id, Task task, String token) {
        log.error("update() method called.");
        log.error(cause.getMessage());
    }

    @Override
    public void delete(UUID id, String token) {
        log.error("delete() method called.");
        log.error(cause.getMessage());
    }
}
