package rsoi.lab2.gservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.gservice.client.TaskClient;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.Optional;
import java.util.UUID;

public class TaskFeignClientFallback implements TaskClient {

    private static final Logger log = LoggerFactory.getLogger(TaskFeignClientFallback.class);
    private final Throwable cause;

    public TaskFeignClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public PageCustom<Task> findAll(Integer page, Integer size) {
        log.error("findAll() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        log.error("findById() method called.");
        log.error(cause.getMessage());
        Task fallback = new Task();
        fallback.setIdTask(new UUID(0, 0));
        return Optional.of(fallback);
    }

    public PageCustom<Task> findByUserId(UUID id, Integer page, Integer size) {
        log.error("findByUserId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<Task> findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        log.error("findByUserIdAndTaskId() method called.");
        log.error(cause.getMessage());
        Task fallback = new Task();
        fallback.setIdTask(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<Task> create(Task fallback) {
        log.error("create() method called.");
        log.error(cause.getMessage());
        fallback.setIdTask(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public void update(UUID id, Task task) {
        log.error("update() method called.");
        log.error(cause.getMessage());
    }

    @Override
    public void delete(UUID id) {
        log.error("delete() method called.");
        log.error(cause.getMessage());
    }
}
