package rsoi.lab2.gservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import rsoi.lab2.gservice.client.*;
import rsoi.lab2.gservice.entity.*;
import rsoi.lab2.gservice.model.ExecuteTask;
import rsoi.lab2.gservice.model.ExecuteTaskRequest;
import rsoi.lab2.gservice.model.ResultTest;
import java.util.Optional;

import java.io.IOException;
import java.util.Date;

@WebAppConfiguration
public class AbstractTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserClient userClient;
    @MockBean
    private TaskClient taskClient;
    @MockBean
    private TestClient testClient;
    @MockBean
    private TaskExecutorClient taskExecutorClient;
    @MockBean
    private ResultClient resultClient;

    MockMvc mvc;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        createMockTasks();
        createMockCompletedTasks();
        createMockUsers();
        createMockResults();
        createMockExecuteTask();
    }

    MvcResult requestGet(String url, Object... param) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url, param).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
    }

    MvcResult requestPost(String url, String json, Object... param) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(url, param).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json)).andReturn();
    }

    MvcResult requestPut(String url, String json, Object... param) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.put(url, param).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json)).andReturn();
    }

    MvcResult requestDelete(String url, Object... param) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.delete(url, param).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
    }

    String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        return new ObjectMapper().readValue(json, clazz);
    }

    private void createMockUsers() {
        User[] users = new User[4];
        for (int i = 0; i < users.length; i++) {
            User user = new User();
            user.setIdUser(i + 1L);
            user.setFirstName("FirstName " + (i + 1));
            user.setLastName("LastName " + (i + 1));
            user.setPassword("Password " + (i + 1));
            user.setEmail("Email " + (i + 1));
            user.setGroup((byte) 1);
            user.setStatus((byte) 1);
            user.setUserName("UserName " + (i + 1));
            users[i] = user;
            Mockito.doReturn(Optional.of(user)).when(userClient).findById(i + 1L);
            User input1 = new User();
            input1.setUserName(user.getUserName());
            input1.setPassword(user.getPassword());
            Mockito.doReturn(Optional.of(user)).when(userClient).check(input1);
            User input2 = new User();
            input2.setEmail(user.getEmail());
            input2.setPassword(user.getPassword());
            Mockito.doReturn(Optional.of(user)).when(userClient).check(input2);
            User input3 = new User();
            input3.setUserName(user.getUserName());
            input3.setEmail(user.getEmail());
            input3.setPassword(user.getPassword());
            Mockito.doReturn(Optional.of(user)).when(userClient).check(input3);
        }
        Mockito.doReturn(users).when(userClient).findAll(null, null);
    }

    private void createMockTasks() {
        Task[] tasks = new Task[10];
        for (int i = 0; i < tasks.length; i++) {
            Task task = new Task();
            task.setNameTask("Testing task " + (i + 1));
            task.setDescription("Testing task " + (i + 1));
            task.setTextTask("Testing task " + (i + 1));
            task.setImage("");
            task.setIdUser(1L);
            task.setIdTask(i+1L);
            task.setTemplateCode("public class App { public static void main(String... args) {}}");
            task.setCreateDate(new Date());
            task.setComplexity((byte) 1);
            tasks[i] = task;
            Test[] testsByTask = new Test[1];
            testsByTask[0] = new Test();
            testsByTask[0].setCreateDate(new Date());
            testsByTask[0].setIdTask(i + 1L);
            testsByTask[0].setIdUser(1L);
            testsByTask[0].setSourceCode("import org.junit.Test; public class SourceTest { @Test public void test() {Assert.assertTrue(true);}}");
            testsByTask[0].setDescription("");
            task.setTests(testsByTask);
            Mockito.doReturn(Optional.of(task)).when(taskClient).findById(i + 1L);
            Mockito.doReturn(Optional.of(task)).when(taskClient).findByUserIdAndTaskId(1L, i + 1L);
            Mockito.doReturn(testsByTask).when(testClient).findByTaskId(i + 1L, null, null);
            Mockito.doReturn(testsByTask).when(testClient).findByUserIdAndTaskId(1L, i + 1L, null, null);
        }
        Mockito.doReturn(tasks).when(taskClient).findAll(null, null);
        Mockito.doReturn(tasks).when(taskClient).findByUserId(1L, null, null);
    }

    private void createMockCompletedTasks() {
        CompletedTask[] completedTasks = new CompletedTask[10];
        for (int i = 0; i < completedTasks.length; i++) {
            CompletedTask completedTask = new CompletedTask();
            completedTask.setCountAllTests(1);
            completedTask.setCountFailedTests(0);
            completedTask.setCountSuccessfulTests(1);
            completedTask.setIdTask(i + 1L);
            completedTask.setIdTest(i + 1L);
            completedTask.setIdUser(1L);
            completedTask.setIdCompletedTask(i + 1L);
            completedTask.setSourceCode("public class App { public static void main(String... args) {}}");
            completedTask.setInfoCompletedTask("");
            Mockito.doReturn(Optional.of(completedTask)).when(taskExecutorClient).findById(i + 1L);
            Mockito.doReturn(Optional.of(completedTask)).when(taskExecutorClient).findByUserIdAndCompletedTaskId(1L, i + 1L);
        }
        Mockito.doReturn(completedTasks).when(taskExecutorClient).findAll(null, null);
        Mockito.doReturn(completedTasks).when(taskExecutorClient).findByUserId(1L, null, null);
    }

    private void createMockResults() {
        Result[] results = new Result[10];
        for (int i = 0; i < results.length; i++) {
            Result result = new Result();
            result.setCountAttempt(1);
            result.setIdTask(i + 1L);
            result.setIdUser(1L);
            result.setMark(100.);
            Mockito.doReturn(Optional.of(result)).when(resultClient).findByUserIdAndTaskId(1L, i + 1L);
            Mockito.doReturn(new Result[] { result }).when(resultClient).findByTaskId(i + 1L, null, null);
        }
        Mockito.doReturn(results).when(resultClient).findAll(null, null);
        Mockito.doReturn(results).when(resultClient).findByUserId(1L, null, null);
    }

    private void createMockExecuteTask() {
        ExecuteTaskRequest executeTaskRequest = new ExecuteTaskRequest();
        executeTaskRequest.setIdTask(1L);
        executeTaskRequest.setIdUser(1L);
        executeTaskRequest.setSourceTask("public class App { public static void main(String... args) {} }");

        ExecuteTask executeTask = new ExecuteTask();
        executeTask.setIdTask(executeTaskRequest.getIdTask());
        executeTask.setIdUser(executeTask.getIdUser());
        executeTask.setIdTest(executeTaskRequest.getIdTask());
        executeTask.setSourceTask(executeTaskRequest.getSourceTask());
        executeTask.setSourceTest("import org.junit.*; public class SourceTest { @Test public void test() {Assert.assertTrue(true);}}");

        ResultTest resultTest = new ResultTest();
        resultTest.setCountAllTests(1);
        resultTest.setCountFailedTests(0);
        resultTest.setCountSuccessfulTests(1);

        Mockito.doReturn(Optional.of(resultTest)).when(taskExecutorClient).execute(executeTask);
    }
}
