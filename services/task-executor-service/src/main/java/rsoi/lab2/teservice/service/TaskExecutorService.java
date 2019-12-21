package rsoi.lab2.teservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;
import rsoi.lab2.teservice.model.ResultWrapper;
import rsoi.lab2.teservice.model.SomeCompletedTaskModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public interface TaskExecutorService {
    Page<SomeCompletedTaskModel> findAll(Pageable pageable);
    Page<SomeCompletedTaskModel> findByTaskId(UUID id, Pageable pageable);
    Page<SomeCompletedTaskModel> findByUserId(UUID id, Pageable pageable);
    Page<SomeCompletedTaskModel> findByTestId(UUID id, Pageable pageable);
    Page<SomeCompletedTaskModel> findByIdUserAndIdTask(UUID idUser, UUID idTask, Pageable pageable);
    CompletedTask findById(UUID id);
    CompletedTask findByUserIdAndCompletedTaskId(UUID idUser, UUID idCompletedTask);
    CompletedTask findByTaskIdAndCompletedTaskId(UUID idTask, UUID idCompletedTask);
    CompletedTask findByTestIdAndCompletedTaskId(UUID idTest, UUID idCompletedTask);
    CompletedTask create(CompletedTask completedTask);
    void update(UUID id, CompletedTask completedTask);
    void delete(UUID id);
    ResultWrapper execute(ExecuteTaskRequest executeTaskRequest) throws IOException, NoSuchAlgorithmException,
            URISyntaxException, ClassNotFoundException;
}
