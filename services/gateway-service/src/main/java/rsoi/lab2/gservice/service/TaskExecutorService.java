package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.CompletedTask;
import rsoi.lab2.gservice.model.ExecuteTask;
import rsoi.lab2.gservice.model.ExecuteTaskRequest;
import rsoi.lab2.gservice.model.ResultTest;

public interface TaskExecutorService {
    CompletedTask findById(Long id);
    CompletedTask findByUserIdAndCompletedTaskId(Long idUser, Long idCompletedTask);
    CompletedTask findByTestIdAndCompletedTaskId(Long idTest, Long idCompletedTask);
    CompletedTask[] findAll(Integer page, Integer size);
    CompletedTask[] findByUserId(Long id, Integer page, Integer size);
    CompletedTask[] findByTaskId(Long id, Integer page, Integer size);
    CompletedTask[] findByTestId(Long id, Integer page, Integer size);
    ResultTest execute(ExecuteTaskRequest request);
}
