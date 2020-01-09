package rsoi.lab2.rservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import rsoi.lab2.rservice.ResultServiceApp;
import rsoi.lab2.rservice.entity.Result;
import rsoi.lab2.rservice.model.ErrorResponse;
import rsoi.lab2.rservice.model.PageCustom;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ResultServiceApp.class)
@TestPropertySource(locations = "classpath:application-h2.properties")
public class ResultControllerTest extends AbstractTest {

    private static final String URL_RESULTS_GET_CREATE = "http://localhost:8085/api/v1/results";
    private static final String URL_RESULT_BY_USER_AND_TASK = "http://localhost:8085/api/v1/users/{idUser}/tasks/{idTask}/results";
    private static final String URL_RESULTS_BY_TASK = "http://localhost:8085/api/v1/tasks/{id}/results";
    private static final String URL_RESULTS_BY_USER = "http://localhost:8085/api/v1/users/{id}/results";

    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test()
    public void testFindAll() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_RESULTS_GET_CREATE + "?page={page}&size={size}", 0, 20);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();

        Assert.assertEquals(results.size(), 5);
    }

    @Test
    public void testFindByUserIdAndTaskId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_RESULTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();
        UUID idUser = results.get(0).getIdUser();
        UUID idTask = results.get(0).getIdTask();

        mvcResult = super.requestGet(URL_RESULT_BY_USER_AND_TASK, idUser, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Result result = super.mapFromJson(content, Result.class);
        Assert.assertEquals(result.getIdTask(), idTask);
        Assert.assertEquals(result.getIdUser(), idUser);
    }

    @Test
    public void testNotFoundByUserIdAndTaskId() throws Exception {
        UUID idUser = UUID.randomUUID();
        UUID idTask = UUID.randomUUID();
        MvcResult mvcResult = super.requestGet(URL_RESULT_BY_USER_AND_TASK, idUser, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "Result could not be found with idUser: " + idUser + " and idTask: " + idTask);
    }

    @Test
    public void testFindByTaskId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_RESULTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();
        UUID idTask = results.get(0).getIdTask();

        mvcResult = super.requestGet(URL_RESULTS_BY_TASK + "?page={page}&size={size}", idTask, 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        results = page.getContent();
        Assert.assertEquals(results.size(), 1);
        Assert.assertEquals(results.get(0).getIdTask(), idTask);
    }

    @Test
    public void testFindByUserId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_RESULTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();
        UUID idUser = results.get(0).getIdUser();

        mvcResult = super.requestGet(URL_RESULTS_BY_USER + "?page={page}&size={size}", idUser, 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        results = page.getContent();
        Assert.assertEquals(results.size(), 1);
        Assert.assertEquals(results.get(0).getIdUser(), idUser);
    }

    @Test
    public void testCreate() throws Exception {
        Result result = new Result();
        result.setIdTask(UUID.randomUUID());
        result.setMark(100.);
        result.setIdUser(UUID.randomUUID());
        result.setCountAttempt(0);

        String inputJson = super.mapToJson(result);
        MvcResult mvcResult = super.requestPost(URL_RESULTS_GET_CREATE, inputJson);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 201);
        String content = mvcResult.getResponse().getContentAsString();
        Result r = super.mapFromJson(content, Result.class);
        Assert.assertEquals(r.getIdTask(), result.getIdTask());
        Assert.assertEquals(r.getIdUser(), result.getIdUser());
    }

    @Test
    public void testBadRequestCreate() throws Exception {
        Result result = new Result();
        result.setIdTask(UUID.randomUUID());

        String inputJson = super.mapToJson(result);
        MvcResult mvcResult = super.requestPost(URL_RESULTS_GET_CREATE, inputJson);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 400);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 400);
        Assert.assertEquals(errorResponse.getError(), "Bad Request");
    }

    @Test
    public void testUpdate() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_RESULTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();
        UUID idUser = results.get(1).getIdUser();
        UUID idTask = results.get(1).getIdTask();

        mvcResult = super.requestGet(URL_RESULT_BY_USER_AND_TASK, idUser, idTask);
        int statusGet = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusGet, 200);
        String contentGet = mvcResult.getResponse().getContentAsString();
        Result result = super.mapFromJson(contentGet, Result.class);
        Assert.assertNotNull(result);

        result.setCountAttempt(result.getCountAttempt()+1);
        String inputJson = super.mapToJson(result);
        mvcResult = super.requestPut(URL_RESULT_BY_USER_AND_TASK, inputJson, idUser, idTask);
        int statusPut = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusPut, 200);
    }

    @Test
    public void testDelete() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_RESULTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        PageCustom<Result> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Result>>() {});
        List<Result> results = page.getContent();
        UUID idUser = results.get(2).getIdUser();
        UUID idTask = results.get(2).getIdTask();

        mvcResult = super.requestDelete(URL_RESULT_BY_USER_AND_TASK, idUser, idTask);
        int statusDelete = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusDelete, 204);
    }
}
