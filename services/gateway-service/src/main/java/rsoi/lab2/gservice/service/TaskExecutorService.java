package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.CompletedTask;
import rsoi.lab2.gservice.model.*;

import java.util.UUID;

public interface TaskExecutorService {
    CompletedTask findById(UUID id);
    CompletedTask findByUserIdAndCompletedTaskId(UUID idUser, UUID idCompletedTask);
    CompletedTask findByTestIdAndCompletedTaskId(UUID idTest, UUID idCompletedTask);
    PageCustom<CompletedTask> findAll(Integer page, Integer size);
    PageCustom<CompletedTask> findByUserId(UUID id, Integer page, Integer size);
    PageCustom<CompletedTask> findByTaskId(UUID id, Integer page, Integer size);
    PageCustom<CompletedTask> findByTestId(UUID id, Integer page, Integer size);
    PageCustom<CompletedTask> findByUserIdAndTaskId(UUID idUser, UUID idTask, Integer page, Integer size);
    ResultTest execute(ExecuteTaskRequest request);
}
