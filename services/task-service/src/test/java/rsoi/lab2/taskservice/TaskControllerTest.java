package rsoi.lab2.taskservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import rsoi.lab2.taskservice.entity.Task;
import rsoi.lab2.taskservice.model.ErrorResponse;
import rsoi.lab2.taskservice.model.PageCustom;
import rsoi.lab2.taskservice.model.SomeTasksModel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskServiceApp.class)
@TestPropertySource(locations = "classpath:application-h2.properties")
public class TaskControllerTest extends AbstractTest {

    private static final String URL_TASKS_GET_CREATE = "http://localhost:8082/tasks";
    private static final String URL_TASK_GET_UPDATE_DELETE = "http://localhost:8082/tasks/{id}";
    private static final String URL_GET_TASKS_BY_USER_ID = "http://localhost:8082/users/{id}/tasks";
    private static final String URL_GET_TASK_BY_USER_ID_AND_TASK_ID = "http://localhost:8082/users/{idUser}/tasks/{idTask}";

    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test()
    public void testFindAll() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();

        Assert.assertEquals(tasks.size(), 2);
    }

    @Test
    public void testFindById() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        UUID taskId = tasks.get(0).getIdTask();

        mvcResult = super.requestGet(URL_TASK_GET_UPDATE_DELETE, taskId);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Task task = super.mapFromJson(content, Task.class);
        Assert.assertEquals(task.getIdTask(), taskId);
    }

    @Test
    public void testNotFoundById() throws Exception {
        UUID id = UUID.randomUUID();
        MvcResult mvcResult = super.requestGet(URL_TASK_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "Task could not be found with id: " + id);
    }

    @Test
    public void testFindByUserIdAndTaskId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        UUID idUser = tasks.get(0).getIdUser();
        UUID idTask = tasks.get(0).getIdTask();

        mvcResult = super.requestGet(URL_GET_TASK_BY_USER_ID_AND_TASK_ID, idUser, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Task task = super.mapFromJson(content, Task.class);
        Assert.assertEquals(task.getIdTask(), idTask);
        Assert.assertEquals(task.getIdUser(), idUser);
    }

    @Test
    public void testNotFoundByUserIdAndTaskId() throws Exception {
        UUID idUser = UUID.randomUUID();
        UUID idTask = UUID.randomUUID();
        MvcResult mvcResult = super.requestGet(URL_GET_TASK_BY_USER_ID_AND_TASK_ID, idUser, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "Task could not be found with idUser: " + idUser + " and idTask: " + idTask);
    }

    @Test
    public void testFindByUserId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        UUID idUser = tasks.get(0).getIdUser();

        mvcResult = super.requestGet(URL_GET_TASKS_BY_USER_ID + "?page={page}&size={size}", idUser, 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        tasks = page.getContent();
        Assert.assertEquals(tasks.size(), 1);
        Assert.assertEquals(tasks.get(0).getIdUser(), idUser);
    }

    @Test
    public void testCreate() throws Exception {
        Task task = new Task();
        task.setNameTask("Нахождение минимального числа.");
        task.setComplexity((byte) 1);
        task.setDescription("Написать программу, нахождение минимального числа в массиве.");
        task.setTemplateCode("public class App {\n" +
                "public static void main(String... args) {\n" +
                "}\n}\n");
        task.setTextTask("На вход в стандартный поток подаются 10 чисел:\n" +
                "напишите программу нахождения минимального числа и выводящего его в" +
                "стандартный поток вывода.");
        task.setCreateDate(new Date());
        task.setIdUser(UUID.randomUUID());

        String inputJson = super.mapToJson(task);
        MvcResult mvcResult = super.requestPost(URL_TASKS_GET_CREATE, inputJson);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 201);
        String content = mvcResult.getResponse().getContentAsString();
        Task result = super.mapFromJson(content, Task.class);
        Assert.assertEquals(task.getTextTask(), result.getTextTask());
    }

    @Test
    public void testBadRequestCreate() throws Exception {
        Task task = new Task();
        task.setComplexity((byte) 1);
        task.setDescription("Написать программу, нахождение минимального числа в массиве.");
        task.setTemplateCode("public class App {\n" +
                "public static void main(String... args) {\n" +
                "}\n}\n");
        task.setTextTask("На вход в стандартный поток подаются 10 чисел:\n" +
                "напишите программу нахождения минимального числа и выводящего его в" +
                "стандартный поток вывода.");

        String inputJson = super.mapToJson(task);
        MvcResult mvcResult = super.requestPost(URL_TASKS_GET_CREATE, inputJson);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 400);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 400);
        Assert.assertEquals(errorResponse.getError(), "Bad Request");
    }

    @Test
    public void testUpdate() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        UUID id = tasks.get(0).getIdTask();

        mvcResult = super.requestGet(URL_TASK_GET_UPDATE_DELETE, id);
        int statusGet = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusGet, 200);
        String contentGet = mvcResult.getResponse().getContentAsString();
        Task task = super.mapFromJson(contentGet, Task.class);
        Assert.assertNotNull(task);

        task.setTextTask("Измененное задание");
        String inputJson = super.mapToJson(task);
        mvcResult = super.requestPut(URL_TASK_GET_UPDATE_DELETE, inputJson, id);
        int statusPut = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusPut, 200);
    }

    @Test
    public void testDelete() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TASKS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Task> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Task>>() {});
        List<Task> tasks = page.getContent();
        UUID id = tasks.get(0).getIdTask();

        mvcResult = super.requestDelete(URL_TASK_GET_UPDATE_DELETE, id);
        int statusDelete = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusDelete, 204);
    }

}
