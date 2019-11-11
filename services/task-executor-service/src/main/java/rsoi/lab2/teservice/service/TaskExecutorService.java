package rsoi.lab2.teservice.service;

import org.springframework.data.domain.Pageable;
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;
import rsoi.lab2.teservice.model.SomeCompletedTaskModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface TaskExecutorService {
    List<SomeCompletedTaskModel> getAll();
    List<SomeCompletedTaskModel> getAll(Pageable pageable);
    List<SomeCompletedTaskModel> getByTaskId(Long id);
    List<SomeCompletedTaskModel> getByTaskId(Long id, Pageable pageable);
    List<SomeCompletedTaskModel> getByUserId(Long id);
    List<SomeCompletedTaskModel> getByUserId(Long id, Pageable pageable);
    List<SomeCompletedTaskModel> getByTestId(Long id);
    List<SomeCompletedTaskModel> getByTestId(Long id, Pageable pageable);
    CompletedTask getById(Long id);
    CompletedTask getByUserIdAndCompletedTaskId(Long idUser, Long idCompletedTask);
    CompletedTask getByTaskIdAndCompletedTaskId(Long idTask, Long idCompletedTask);
    CompletedTask getByTestIdAndCompletedTaskId(Long idTest, Long idCompletedTask);
    CompletedTask create(CompletedTask completedTask);
    void update(CompletedTask completedTask);
    void delete(Long id);
    ResultTest execute(ExecuteTaskRequest executeTaskRequest) throws IOException, NoSuchAlgorithmException,
            URISyntaxException, ClassNotFoundException;
}
