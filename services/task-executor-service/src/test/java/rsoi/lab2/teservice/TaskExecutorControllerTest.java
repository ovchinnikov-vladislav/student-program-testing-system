package rsoi.lab2.teservice;

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
import rsoi.lab2.teservice.model.ErrorResponse;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskExecutorServiceApp.class)
@TestPropertySource("classpath:application-h2.properties")
public class TaskExecutorControllerTest extends AbstractTest {

    private static final String URL_COMPLETED_TASKS_GET_CREATE = "http://localhost:8084/completed_tasks";
    private static final String URL_COMPLETED_TASKS_BY_USER = "http://localhost:8084/users/{id}/completed_tasks";
    private static final String URL_COMPLETED_TASKS_BY_TASK = "http://localhost:8084/tasks/{id}/completed_tasks";
    private static final String URL_COMPLETED_TASKS_BY_TEST = "http://localhost:8084/tests/{id}/completed_tasks";
    private static final String URL_COMPLETED_TASK_GET_UPDATE_DELETE = "http://localhost:8084/completed_tasks/{id}";
    private static final String URL_COMPLETED_TASK_BY_USER_AND_COMPLETED_TASK = "http://localhost:8084/users/{idUser}/completed_tasks/{idCompletedTask}";
    private static final String URL_COMPLETED_TASK_BY_TASK_AND_COMPLETED_TASK = "http://localhost:8084/tasks/{idTask}/completed_tasks/{idCompletedTask}";
    private static final String URL_COMPLETED_TASK_BY_TEST_AND_COMPLETED_TASK = "http://localhost:8084/tests/{idTest}/completed_tasks/{idCompletedTask}";
    private static final String URL_TASK_EXECUTE = "http://localhost:8084/tasks/execute";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testFindAll() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_GET_CREATE);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask[] completedTasks = super.mapFromJson(content, CompletedTask[].class);
        Assert.assertEquals(completedTasks.length, 3);
    }

    @Test
    public void testFindByTask() throws Exception {
        int idTask = 1;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_BY_TASK, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask[] completedTasks = super.mapFromJson(content, CompletedTask[].class);
        Assert.assertEquals(completedTasks.length, 1);
    }

    @Test
    public void testFindByUser() throws Exception {
        int idUser = 1;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_BY_USER, idUser);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask[] completedTasks = super.mapFromJson(content, CompletedTask[].class);
        Assert.assertEquals(completedTasks.length, 3);
    }

    @Test
    public void testFindByTest() throws Exception {
        int idTest = 1;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASKS_BY_TEST, idTest);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask[] completedTasks = super.mapFromJson(content, CompletedTask[].class);
        Assert.assertEquals(completedTasks.length, 1);
    }

    @Test
    public void testFindById() throws Exception {
        int id = 2;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getIdCompletedTask().longValue(), id);
    }

    @Test
    public void testNotFoundById() throws Exception {
        int id = 100;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError(), "404 NOT_FOUND");
        Assert.assertEquals(errorResponse.getMessage(), "Not found CompletedTask by id = " + id);
    }

    @Test
    public void testFindByTaskAndCompletedTask() throws Exception {
        int idTask = 2;
        int idCompletedTask = 2;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_TASK_AND_COMPLETED_TASK, idTask, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getIdTask().longValue(), idTask);
        Assert.assertEquals(completedTask.getIdCompletedTask().longValue(), idCompletedTask);
    }

    @Test
    public void testNotFoundByTaskAndCompletedTask() throws Exception {
        int idTask = 2;
        int idCompletedTask = 5;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_TASK_AND_COMPLETED_TASK, idTask, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError(), "404 NOT_FOUND");
    }

    @Test
    public void testFindByUserAndCompletedTask() throws Exception {
        int idUser = 1;
        int idCompletedTask = 2;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_USER_AND_COMPLETED_TASK, idUser, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getIdUser().longValue(), idUser);
        Assert.assertEquals(completedTask.getIdCompletedTask().longValue(), idCompletedTask);
    }

    @Test
    public void testNotFoundByUserAndCompletedTask() throws Exception {
        int idUser = 5;
        int idCompletedTask = 2;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_USER_AND_COMPLETED_TASK, idUser, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError(), "404 NOT_FOUND");
    }

    @Test
    public void testFindByTestAndCompletedTask() throws Exception {
        int idTest = 2;
        int idCompletedTask = 2;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_TEST_AND_COMPLETED_TASK, idTest, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertEquals(completedTask.getIdTest().longValue(), idTest);
        Assert.assertEquals(completedTask.getIdCompletedTask().longValue(), idCompletedTask);
    }

    @Test
    public void testNotFoundByTestAndCompletedTask() throws Exception {
        int idUser = 5;
        int idCompletedTask = 2;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_BY_USER_AND_COMPLETED_TASK, idUser, idCompletedTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError(), "404 NOT_FOUND");
    }

    @Test
    public void testCreate() throws Exception {
        CompletedTask completedTask = new CompletedTask();
        completedTask.setSourceCode("public class App { public static void main(String... args) {} }");
        completedTask.setCountAllTests(0);
        completedTask.setCountFailedTests(0);
        completedTask.setCountSuccessfulTests(0);
        completedTask.setIdTask(4L);
        completedTask.setIdUser(1L);
        completedTask.setIdTest(4L);
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
        int id = 2;
        MvcResult mvcResult = super.requestGet(URL_COMPLETED_TASK_GET_UPDATE_DELETE, id);
        int statusGet = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusGet, 200);
        String content = mvcResult.getResponse().getContentAsString();
        CompletedTask completedTask = super.mapFromJson(content, CompletedTask.class);
        Assert.assertNotNull(completedTask);

        completedTask.setInfoCompletedTask("Изменненая информация о завершенном тесте.");
        String inputJson = super.mapToJson(completedTask);
        mvcResult = super.requestPut(URL_COMPLETED_TASK_GET_UPDATE_DELETE, inputJson, id);
        int statusPut = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusPut, 200);
    }

    @Test
    public void testDelete() throws Exception {
        int id = 3;
        MvcResult mvcResult = super.requestDelete(URL_COMPLETED_TASK_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 204);
    }

    @Test
    public void testExecute() throws Exception {
        ExecuteTaskRequest executeTaskRequest = new ExecuteTaskRequest();
        executeTaskRequest.setIdTask(1L);
        executeTaskRequest.setIdTest(1L);
        executeTaskRequest.setIdUser(1L);
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
        ResultTest resultTest = super.mapFromJson(content, ResultTest.class);
        Assert.assertEquals(resultTest.getCountAllTests(), 2);
        Assert.assertEquals(resultTest.getCountFailedTests(), 1);
        Assert.assertEquals(resultTest.getCountSuccessfulTests(), 1);
        Assert.assertEquals(resultTest.getWasSuccessful(), 0);
    }
}
