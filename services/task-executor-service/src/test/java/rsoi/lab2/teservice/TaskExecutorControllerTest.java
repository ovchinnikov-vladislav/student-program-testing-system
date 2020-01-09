package rsoi.lab2.teservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.model.*;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskExecutorServiceApp.class)
@TestPropertySource("classpath:application-h2.properties")
public class TaskExecutorControllerTest extends AbstractTest {

    private static final String URL_COMPLETED_TASKS_GET_CREATE = "http://localhost:8084/api/v1/completed_tasks";
    private static final String URL_COMPLETED_TASKS_BY_USER = "http://localhost:8084/api/v1/users/{id}/completed_tasks";
    private static final String URL_COMPLETED_TASKS_BY_TASK = "http://localhost:8084/api/v1/tasks/{id}/completed_tasks";
    private static final String URL_COMPLETED_TASKS_BY_TEST = "http://localhost:8084/api/v1/tests/{id}/completed_tasks";
    private static final String URL_COMPLETED_TASKS_BY_USER_AND_TASK = "http://localhost:8084/api/v1/users/{idUser}/tasks/{idTask}/completed_tasks";
    private static final String URL_COMPLETED_TASK_GET_UPDATE_DELETE = "http://localhost:8084/api/v1/completed_tasks/{id}";
    private static final String URL_COMPLETED_TASK_BY_USER_AND_COMPLETED_TASK = "http://localhost:8084/api/v1/users/{idUser}/completed_tasks/{idCompletedTask}";
    private static final String URL_COMPLETED_TASK_BY_TASK_AND_COMPLETED_TASK = "http://localhost:8084/api/v1/tasks/{idTask}/completed_tasks/{idCompletedTask}";
    private static final String URL_COMPLETED_TASK_BY_TEST_AND_COMPLETED_TASK = "http://localhost:8084/api/v1/tests/{idTest}/completed_tasks/{idCompletedTask}";
    private static final String URL_TASK_EXECUTE = "http://localhost:8084/api/v1/tasks/execute";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testFindAll() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        Assert.assertEquals(completedTasks.size(), 4);
    }

    @Test
    public void testFindByTask() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID idTask = completedTasks.get(0).getIdTask();

        mvcResult = super.requestGet(URL_COMPLETED_TASKS_BY_TASK + "?page={page}&size={size}", idTask, 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        completedTasks = page.getContent();
        Assert.assertEquals(completedTasks.size(), 1);
        Assert.assertEquals(completedTasks.get(0).getIdTask(), idTask);
    }

    @Test
    public void testFindByUser() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID idUser = completedTasks.get(0).getIdUser();

        mvcResult = super.requestGet(URL_COMPLETED_TASKS_BY_USER + "?page={page}&size={size}", idUser, 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        completedTasks = page.getContent();
        Assert.assertEquals(completedTasks.size(), 1);
        Assert.assertEquals(completedTasks.get(0).getIdUser(), idUser);
    }

    @Test
    public void testFindByTest() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID idTest = completedTasks.get(0).getIdTest();

        mvcResult = super.requestGet(URL_COMPLETED_TASKS_BY_TEST + "?page={page}&size={size}", idTest, 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        completedTasks = page.getContent();
        Assert.assertEquals(completedTasks.size(), 1);
        Assert.assertEquals(completedTasks.get(0).getIdTest(), idTest);
    }

    @Test
    public void testFindByUserAndTask() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID idUser = completedTasks.get(0).getIdUser();
        UUID idTask = completedTasks.get(0).getIdTask();

        mvcResult = super.requestGet(URL_COMPLETED_TASKS_BY_USER_AND_TASK + "?page={page}&size={size}", idUser, idTask, 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        completedTasks = page.getContent();
        Assert.assertEquals(completedTasks.size(), 1);
        Assert.assertEquals(completedTasks.get(0).getIdUser(), idUser);
        Assert.assertEquals(completedTasks.get(0).getIdTask(), idTask);
    }

    @Test
    public void testFindById() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID id = completedTasks.get(0).getId();

        mvcResult = super.requestGet(URL_COMPLETED_TASK_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getId(), id);
    }

    @Test
    public void testNotFoundById() throws Exception {
        UUID id = UUID.randomUUID();

        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "CompletedTask could not be found with id: " + id);
    }

    @Test
    public void testFindByTaskAndCompletedTask() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID idTask = completedTasks.get(0).getIdTask();
        UUID idCompletedTask = completedTasks.get(0).getId();

        mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_TASK_AND_COMPLETED_TASK, idTask, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getIdTask(), idTask);
        Assert.assertEquals(completedTask.getId(), idCompletedTask);
    }

    @Test
    public void testNotFoundByTaskAndCompletedTask() throws Exception {
        UUID idTask = UUID.randomUUID();
        UUID idCompletedTask = UUID.randomUUID();
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_TASK_AND_COMPLETED_TASK, idTask, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "CompletedTask could not be found with idTask: " + idTask + " and idCompletedTask: " + idCompletedTask);
    }

    @Test
    public void testFindByUserAndCompletedTask() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID idUser = completedTasks.get(0).getIdUser();
        UUID idCompletedTask = completedTasks.get(0).getId();

        mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_USER_AND_COMPLETED_TASK, idUser, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getIdUser(), idUser);
        Assert.assertEquals(completedTask.getId(), idCompletedTask);
    }

    @Test
    public void testNotFoundByUserAndCompletedTask() throws Exception {
        UUID idUser = UUID.randomUUID();
        UUID idCompletedTask = UUID.randomUUID();
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_USER_AND_COMPLETED_TASK, idUser, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "CompletedTask could not be found with idUser: " + idUser + " and idCompletedTask: " + idCompletedTask);
    }

    @Test
    public void testFindByTestAndCompletedTask() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID idTest = completedTasks.get(0).getIdTest();
        UUID idCompletedTask = completedTasks.get(0).getId();

        mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_TEST_AND_COMPLETED_TASK, idTest, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getIdTest(), idTest);
        Assert.assertEquals(completedTask.getId(), idCompletedTask);
    }

    @Test
    public void testNotFoundByTestAndCompletedTask() throws Exception {
        UUID idUser = UUID.randomUUID();
        UUID idCompletedTask = UUID.randomUUID();
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_USER_AND_COMPLETED_TASK, idUser, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "CompletedTask could not be found with idUser: " + idUser + " and idCompletedTask: " + idCompletedTask);
    }

    @Test
    public void testCreate() throws Exception {
        CompletedTask completedTask = new CompletedTask();
        completedTask.setSourceCode("public class App { public static void main(String... args) {} }");
        completedTask.setCountAllTests(0);
        completedTask.setWasSuccessful((byte) 0);
        completedTask.setCountFailedTests(0);
        completedTask.setCountSuccessfulTests(0);
        completedTask.setIdTask(UUID.randomUUID());
        completedTask.setIdUser(UUID.randomUUID());
        completedTask.setIdTest(UUID.randomUUID());
        String inputJson = super.mapToJson(completedTask);
        MvcResult mvcResult = super.requestPost(URL_COMPLETED_TASKS_GET_CREATE, inputJson);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 201);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask result = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getSourceCode(), result.getSourceCode());
    }

    @Test
    public void testUpdate() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID id = completedTasks.get(1).getId();

        mvcResult = super.requestGet(URL_COMPLETED_TASK_GET_UPDATE_DELETE, id);
        int statusGet = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusGet, 200);
        content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertNotNull(completedTask);

        String inputJson = super.mapToJson(completedTask);
        mvcResult = super.requestPut(URL_COMPLETED_TASK_GET_UPDATE_DELETE, inputJson, id);
        int statusPut = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusPut, 200);
    }

    @Test
    public void testDelete() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<CompletedTask> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<CompletedTask>>() {});
        List<CompletedTask> completedTasks = page.getContent();
        UUID id = completedTasks.get(2).getId();

        mvcResult = super.requestDelete(URL_COMPLETED_TASK_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 204);
    }

    @Test
    public void testExecute() throws Exception {
        ExecuteTaskRequest executeTaskRequest = new ExecuteTaskRequest();
        executeTaskRequest.setIdTask(UUID.randomUUID());
        executeTaskRequest.setIdTest(UUID.randomUUID());
        executeTaskRequest.setIdUser(UUID.randomUUID());
        executeTaskRequest.setSourceTask("public class App { public static void main(String... args) {}}");
        executeTaskRequest.setSourceTest("import org.junit.Test; import org.junit.Assert; public class SourceTest { " +
                "@Test public void test1() {" +
                "Assert.assertTrue(true);" +
                "}" +
                "@Test public void test2() {" +
                "Assert.assertTrue(false);" +
                "}" +
                "}");
        MvcResult mvcResult = super.requestPost(URL_TASK_EXECUTE, super.mapToJson(executeTaskRequest));
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        ResultWrapper resultWrapper = super.mapFromJson(content, ResultWrapper.class);
        ResultTest resultTest = resultWrapper.getResultTest();
        Assert.assertEquals(resultTest.getCountAllTests(), 2);
        Assert.assertEquals(resultTest.getCountFailedTests(), 1);
        Assert.assertEquals(resultTest.getCountSuccessfulTests(), 1);
        Assert.assertEquals(resultTest.getWasSuccessful(), 0);
    }
}
