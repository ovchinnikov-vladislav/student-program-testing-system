package rsoi.lab2.gservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.gservice.client.TaskExecutorClient;
import rsoi.lab2.gservice.entity.completedtask.CompletedTask;
import rsoi.lab2.gservice.model.executetask.ExecuteTask;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.result.ResultWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TaskExecutorFeignClientFallback implements TaskExecutorClient {

    private static final Logger log = LoggerFactory.getLogger(TaskExecutorFeignClientFallback.class);
    private final Throwable cause;

    public TaskExecutorFeignClientFallback(Throwable cause) {
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
    public Optional<CompletedTask> findById(UUID id, String token) {
        log.error("findById() method called.");
        log.error(cause.getMessage());
        CompletedTask fallback = new CompletedTask();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<CompletedTask> findByUserIdAndCompletedTaskId(UUID idUser, UUID idCompletedTask, String token) {
        log.error("findByUserIdAndCompletedTaskId() method called.");
        log.error(cause.getMessage());
        CompletedTask fallback = new CompletedTask();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<CompletedTask> findByTaskIdAndCompletedTaskId(UUID idTask, UUID idCompletedTask, String token) {
        log.error("findByTaskIdAndCompletedTaskId() method called.");
        log.error(cause.getMessage());
        CompletedTask fallback = new CompletedTask();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public Optional<CompletedTask> findByTestIdAndCompletedTaskId(UUID idTest, UUID idCompletedTask, String token) {
        log.error("findByTestIdAndCompletedTaskId() method called.");
        log.error(cause.getMessage());
        CompletedTask fallback = new CompletedTask();
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public PageCustom<CompletedTask> findAll(Integer page, Integer size, String token) {
        log.error("findAll() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public PageCustom<CompletedTask> findByUserId(UUID id, Integer page, Integer size, String token) {
        log.error("findByUserId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public PageCustom<CompletedTask> findByTaskId(UUID id, Integer page, Integer size, String token) {
        log.error("findByTaskId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public PageCustom<CompletedTask> findByTestId(UUID id, Integer page, Integer size, String token) {
        log.error("findByTestId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public PageCustom<CompletedTask> findByUserIdAndTaskId(UUID idUser, UUID idTask, Integer page, Integer size, String token) {
        log.error("findByUserIdAndTaskId() method called.");
        log.error(cause.getMessage());
        return null;
    }

    @Override
    public Optional<CompletedTask> create(CompletedTask fallback, String token) {
        log.error("create() method called.");
        log.error(cause.getMessage());
        fallback.setId(new UUID(0, 0));
        return Optional.of(fallback);
    }

    @Override
    public void update(UUID id, CompletedTask completedTask, String token) {
        log.error("update() method called.");
        log.error(cause.getMessage());
    }

    @Override
    public void delete(UUID id, String token) {
        log.error("delete() method called.");
        log.error(cause.getMessage());
    }

    @Override
    public Optional<ResultWrapper> execute(ExecuteTask executeTask, String token) {
        log.error("execute() method called.");
        log.error(cause.getMessage());
        ResultWrapper fallback = new ResultWrapper();
        fallback.setIdCompletedTask(new UUID(0, 0));
        return Optional.of(fallback);
    }
}
