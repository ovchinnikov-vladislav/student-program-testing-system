package rsoi.lab2.gservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.entity.Result;
import rsoi.lab2.gservice.entity.User;
import rsoi.lab2.gservice.model.ExecuteTask;
import rsoi.lab2.gservice.model.ExecuteTaskRequest;
import rsoi.lab2.gservice.model.ResultTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GatewayServiceApp.class)
public class GatewayControllerTest extends AbstractTest {

    private static final String URL_USERS = "http://localhost:8080/gate/users";
    private static final String URL_USERS_ID = "http://localhost:8080/gate/users/{id}";
    private static final String URL_USERS_CHECK = "http://localhost:8080/gate/users/check";

    private static final String URL_TASKS = "http://localhost:8080/gate/tasks";
    private static final String URL_TASKS_ID = "http://localhost:8080/gate/tasks/{id}";
    private static final String URL_TASKS_BY_USER = "http://localhost:8080/gate/users/{id}/tasks";
    private static final String URL_TASK_BY_USER_AND_TASK = "http://localhost:8080/gate/users/{idUser}/tasks/{idTask}";
    private static final String URL_TASK_EXECUTE = "http://localhost:8080/gate/tasks/execute";

    private static final String URL_RESULTS_BY_USER = "http://localhost:8080/gate/users/{id}/results";
    private static final String URL_RESULTS_BY_TASK = "http://localhost:8080/gate/tasks/{id}/results";
    private static final String URL_RESULTS_BY_USER_AND_TASK = "http://localhost:8080/gate/users/{idUser}/tasks/{idTask}/results";

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testGetUsers() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_USERS).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        User[] users = super.mapFromJson(content, User[].class);
        Assert.assertEquals(users.length, 4);
    }

    @Test
    public void testGetUserById() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_USERS_ID, 2).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        User user = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getIdUser().longValue(), 2);
    }

    @Test
    public void testCheckUser() throws Exception {
        User user = new User();
        user.setUserName("UserName " + 1);
        user.setPassword("Password " + 1);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8).content(super.mapToJson(user))).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        User result = super.mapFromJson(content, User.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(user.getUserName(), result.getUserName());
        Assert.assertEquals(user.getPassword(), result.getPassword());

        user.setEmail("Email " + 1);
        user.setPassword("Password " + 1);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8).content(super.mapToJson(user))).andReturn();
        status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        result = super.mapFromJson(content, User.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(user.getEmail(), result.getEmail());
        Assert.assertEquals(user.getPassword(), result.getPassword());

        user.setUserName("UserName " + 1);
        user.setEmail("Email " + 1);
        user.setPassword("Password " + 1);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8).content(super.mapToJson(user))).andReturn();
        status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        result = super.mapFromJson(content, User.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(user.getUserName(), result.getUserName());
        Assert.assertEquals(user.getEmail(), result.getEmail());
        Assert.assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    public void testGetTasks() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Task[] tasks = super.mapFromJson(content, Task[].class);
        Assert.assertEquals(tasks.length, 10);
    }

    @Test
    public void testGetTaskById() throws Exception {
        int id = 1;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS_ID, id).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Task task = super.mapFromJson(content, Task.class);
        Assert.assertNotNull(task);
        Assert.assertEquals(task.getIdTask().longValue(), id);
        Assert.assertNotNull(task.getTest());
        Assert.assertEquals(task.getTest().getIdTask().longValue(), id);
    }

    @Test
    public void testGetTasksByIdUser() throws Exception {
        int id = 1;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS_BY_USER, id).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Task[] tasks = mapFromJson(content, Task[].class);
        Assert.assertNotNull(tasks);
        Assert.assertEquals(tasks.length, 10);
        for (Task task : tasks) {
            Assert.assertNotNull(task);
            Assert.assertEquals(task.getIdUser().longValue(), id);
        }
    }

    @Test
    public void testGetTaskByIdUserAndIdTask() throws Exception {
        int idUser = 1;
        int idTask = 1;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASK_BY_USER_AND_TASK, idUser, idTask).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();

        Task task = super.mapFromJson(content, Task.class);
        Assert.assertNotNull(task);
        Assert.assertEquals(task.getIdTask().longValue(), idTask);
        Assert.assertEquals(task.getIdUser().longValue(), idUser);
        Assert.assertNotNull(task.getTest());
        Assert.assertEquals(task.getTest().getIdTask().longValue(), idTask);
        Assert.assertEquals(task.getTest().getIdUser().longValue(), idUser);
    }

    @Test
    public void testExecute() throws Exception {
        ExecuteTaskRequest executeTaskRequest = new ExecuteTaskRequest();
        executeTaskRequest.setIdTask(1L);
        executeTaskRequest.setIdUser(1L);
        executeTaskRequest.setSourceTask("public class App { public static void main(String... args) {} }");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_TASK_EXECUTE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(super.mapToJson(executeTaskRequest))).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();

        ResultTest resultTest = super.mapFromJson(content, ResultTest.class);
        Assert.assertNotNull(resultTest);
        Assert.assertEquals(resultTest.getCountAllTests(), 1);
        Assert.assertEquals(resultTest.getCountFailedTests(), 0);
        Assert.assertEquals(resultTest.getCountSuccessfulTests(), 1);
    }

    @Test
    public void testResultsByUser() throws Exception {
        int id = 1;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_RESULTS_BY_USER, id).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Result[] results = mapFromJson(content, Result[].class);
        Assert.assertNotNull(results);
        Assert.assertEquals(results.length, 10);
        for (Result result : results) {
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getIdUser().longValue(), id);
        }
    }

    @Test
    public void testResultsByTask() throws Exception {
        int id = 1;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_RESULTS_BY_TASK, id).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Result[] results = mapFromJson(content, Result[].class);
        Assert.assertNotNull(results);
        Assert.assertEquals(results.length, 1);
        for (Result result : results) {
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getIdTask().longValue(), id);
        }
    }

    @Test
    public void testResultByUserAndTask() throws Exception {
        int idTask = 1;
        int idUser = 1;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_RESULTS_BY_USER_AND_TASK, idUser, idTask).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Result result = mapFromJson(content, Result.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getIdTask().longValue(), idTask);
        Assert.assertEquals(result.getIdUser().longValue(), idUser);
    }
}
