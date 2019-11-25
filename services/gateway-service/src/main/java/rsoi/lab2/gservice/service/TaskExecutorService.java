package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.CompletedTask;
import rsoi.lab2.gservice.model.ExecuteTask;
import rsoi.lab2.gservice.model.ExecuteTaskRequest;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.ResultTest;

public interface TaskExecutorService {
    CompletedTask findById(Long id);
    CompletedTask findByUserIdAndCompletedTaskId(Long idUser, Long idCompletedTask);
    CompletedTask findByTestIdAndCompletedTaskId(Long idTest, Long idCompletedTask);
    PageCustom<CompletedTask> findAll(Integer page, Integer size);
    PageCustom<CompletedTask> findByUserId(Long id, Integer page, Integer size);
    PageCustom<CompletedTask> findByTaskId(Long id, Integer page, Integer size);
    PageCustom<CompletedTask> findByTestId(Long id, Integer page, Integer size);
    PageCustom<CompletedTask> findByUserIdAndTaskId(Long idUser, Long idTask, Integer page, Integer size);
    ResultTest execute(ExecuteTaskRequest request);
}
