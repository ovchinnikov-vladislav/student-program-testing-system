package rsoi.lab2.taskservice;

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

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ResultServiceApp.class)
@TestPropertySource(locations = "classpath:application-h2.properties")
public class ResultControllerTest extends AbstractTest {

    private static final String URL_RESULTS_GET_CREATE = "http://localhost:8085/results";
    private static final String URL_RESULT_BY_USER_AND_TASK = "http://localhost:8085/users/{idUser}/tasks/{idTask}/results";
    private static final String URL_RESULTS_BY_TASK = "http://localhost:8085/tasks/{id}/results";
    private static final String URL_RESULTS_BY_USER = "http://localhost:8085/users/{id}/results";

    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test()
    public void testFindAll() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_RESULTS_GET_CREATE );

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Result[] results = super.mapFromJson(content, Result[].class);

        Assert.assertEquals(results.length, 5);
    }

    @Test
    public void testFindByUserIdAndTaskId() throws Exception {
        int idUser = 1;
        int idTask = 1;
        MvcResult mvcResult = super.requestGet(URL_RESULT_BY_USER_AND_TASK, idUser, idTask);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Result result = super.mapFromJson(content, Result.class);
        Assert.assertEquals(result.getIdTask().longValue(), idTask);
        Assert.assertEquals(result.getIdUser().longValue(), idUser);
    }

    @Test
    public void testNotFoundByUserIdAndTaskId() throws Exception {
        int idUser = 1;
        int idTask = 100;
        MvcResult mvcResult = super.requestGet(URL_RESULT_BY_USER_AND_TASK, idUser, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError(), "404 NOT_FOUND");
        Assert.assertEquals(errorResponse.getMessage(), "Not found Result by idUser = " + idUser + " and idTask = " + idTask);
    }

    @Test
    public void testFindByTaskId() throws Exception {
        int idTask = 1;
        MvcResult mvcResult = super.requestGet(URL_RESULTS_BY_TASK, idTask);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Result[] results = super.mapFromJson(content, Result[].class);
        Assert.assertEquals(results.length, 3);
    }

    @Test
    public void testFindByUserId() throws Exception {
        int idUser = 1;
        MvcResult mvcResult = super.requestGet(URL_RESULTS_BY_USER, idUser);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Result[] results = super.mapFromJson(content, Result[].class);
        Assert.assertEquals(results.length, 3);
    }

    @Test
    public void testCreate() throws Exception {
        Result result = new Result();
        result.setIdTask(5L);
        result.setMark(100.);
        result.setCreateDate(new Date());
        result.setIdUser(1L);
        result.setCountAttempt(0);

        String inputJson = super.mapToJson(result);
        MvcResult mvcResult = super.requestPost(URL_RESULTS_GET_CREATE, inputJson);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 201);
        String content = mvcResult.getResponse().getContentAsString();
        Result r = super.mapFromJson(content, Result.class);
        Assert.assertEquals(r.getIdTask().longValue(), result.getIdTask().longValue());
        Assert.assertEquals(r.getIdUser().longValue(), result.getIdUser().longValue());
    }

    @Test
    public void testBadRequestCreate() throws Exception {
        Result result = new Result();
        result.setIdTask(23L);
        result.setCreateDate(new Date());

        String inputJson = super.mapToJson(result);
        MvcResult mvcResult = super.requestPost(URL_RESULTS_GET_CREATE, inputJson);

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 400);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError(), "400 BAD_REQUEST");
    }

    @Test
    public void testUpdate() throws Exception {
        int idUser = 2;
        int idTask = 1;
        MvcResult mvcResult = super.requestGet(URL_RESULT_BY_USER_AND_TASK, idUser, idTask);
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
        int idUser = 1;
        int idTask = 1;
        MvcResult mvcResult = super.requestDelete(URL_RESULT_BY_USER_AND_TASK, idUser, idTask);
        int statusDelete = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusDelete, 204);
    }
}
