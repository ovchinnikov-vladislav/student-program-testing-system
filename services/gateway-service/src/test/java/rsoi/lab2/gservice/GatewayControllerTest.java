package rsoi.lab2.gservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rsoi.lab2.gservice.entity.task.Task;
import rsoi.lab2.gservice.entity.result.Result;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.List;
import java.util.UUID;

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
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_USERS + "?page=0&size=20").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<User> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<User>>() {});
        List<User> users = page.getContent();
        Assert.assertEquals(users.size(), 4);
    }

    @Test
    public void testGetUserById() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_USERS + "?page=0&size=20").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<User> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<User>>() {});
        List<User> users = page.getContent();
        UUID idUser = users.get(0).getIdUser();

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_USERS_ID, idUser).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        User user = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getIdUser(), idUser);
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
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS + "?page=0&size=20").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        Assert.assertEquals(tasks.size(), 10);
    }

    @Test
    public void testGetTaskById() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS + "?page=0&size=20").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        UUID id = tasks.get(0).getIdTask();

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS_ID, id).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Task task = super.mapFromJson(content, Task.class);
        Assert.assertNotNull(task);
        Assert.assertEquals(task.getIdTask(), id);
        Assert.assertNotNull(task.getTest());
        Assert.assertEquals(task.getTest().getIdTask(), id);
    }

    @Test
    public void testGetTasksByIdUser() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS + "?page=0&size=20").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        UUID id = tasks.get(0).getIdUser();

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS_BY_USER + "?page=0&size=20", id).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        tasks = page.getContent();
        Assert.assertNotNull(tasks);
        Assert.assertEquals(tasks.size(), 10);
        for (Task task : tasks) {
            Assert.assertNotNull(task);
            Assert.assertEquals(task.getIdUser(), id);
        }
    }

    @Test
    public void testGetTaskByIdUserAndIdTask() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASKS + "?page=0&size=20").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        UUID idUser = tasks.get(0).getIdUser();
        UUID idTask = tasks.get(0).getIdTask();

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_TASK_BY_USER_AND_TASK, idUser, idTask).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();

        Task task = super.mapFromJson(content, Task.class);
        Assert.assertNotNull(task);
        Assert.assertEquals(task.getIdTask(), idTask);
        Assert.assertEquals(task.getIdUser(), idUser);
        Assert.assertNotNull(task.getTest());
        Assert.assertEquals(task.getTest().getIdTask(), idTask);
        Assert.assertEquals(task.getTest().getIdUser(), idUser);
    }

    /*@Test
    public void testExecute() throws Exception {
        UUID idTask = UUID.fromString("7c9802de-bbb4-43f8-b0a2-d7d7cac47260");
        UUID idUser = UUID.fromString("6c2153f8-f2db-4e41-9379-9df2983c91f3");

        ExecuteTaskRequest executeTaskRequest = new ExecuteTaskRequest();
        executeTaskRequest.setIdTask(idTask);
        executeTaskRequest.setIdUser(idUser);
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
    }*/

    @Test
    public void testResultsByUser() throws Exception {
        UUID id = UUID.fromString("6c2153f8-f2db-4e41-9379-9df2983c91f3");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_RESULTS_BY_USER + "?page=0&size=20", id).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();
        Assert.assertNotNull(results);
        Assert.assertEquals(results.size(), 10);
        for (Result result : results) {
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getIdUser(), id);
        }
    }

    @Test
    public void testResultsByTask() throws Exception {
        UUID idUser = UUID.fromString("6c2153f8-f2db-4e41-9379-9df2983c91f3");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_RESULTS_BY_USER + "?page=0&size=20", idUser).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();
        UUID id = results.get(0).getIdTask();

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_RESULTS_BY_TASK + "?page=0&size=20", id).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        results = page.getContent();
        Assert.assertNotNull(results);
        Assert.assertEquals(results.size(), 1);
        for (Result result : results) {
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getIdTask(), id);
        }
    }

    @Test
    public void testResultByUserAndTask() throws Exception {
        UUID idUser = UUID.fromString("6c2153f8-f2db-4e41-9379-9df2983c91f3");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_RESULTS_BY_USER + "?page=0&size=20", idUser).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();
        UUID idTask = results.get(0).getIdTask();

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL_RESULTS_BY_USER_AND_TASK, idUser, idTask).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Result result = mapFromJson(content, Result.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getIdTask(), idTask);
        Assert.assertEquals(result.getIdUser(), idUser);
    }
}
