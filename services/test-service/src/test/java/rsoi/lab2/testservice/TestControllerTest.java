package rsoi.lab2.testservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import rsoi.lab2.testservice.entity.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import rsoi.lab2.testservice.model.ErrorResponse;
import rsoi.lab2.testservice.model.PageCustom;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestServiceApp.class)
@TestPropertySource("classpath:application-h2.properties")
public class TestControllerTest extends AbstractTest {

    private static final String URL_TESTS_GET_CREATE = "http://localhost:8083/tests";
    private static final String URL_TEST_GET_UPDATE_DELETE = "http://localhost:8083/tests/{id}";
    private static final String URL_TESTS_BY_USER_ID = "http://localhost:8083/users/{id}/tests";
    private static final String URL_TEST_BY_TASK_ID = "http://localhost:8083/tasks/{id}/tests";
    private static final String URL_TEST_BY_USER_ID_AND_TEST_ID = "http://localhost:8083/users/{idUser}/tests/{idTest}";
    private static final String URL_TEST_BY_USER_ID_AND_TASK_ID = "http://localhost:8083/users/{idUser}/tasks/{idTask}/tests";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        Assert.assertEquals(tests.size(), 2);
    }

    @org.junit.Test
    public void testFindByUserId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        UUID idUser = tests.get(0).getIdUser();

        mvcResult = super.requestGet(URL_TESTS_BY_USER_ID + "?page={page}&size={size}", idUser, 0, 20);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        tests = page.getContent();
        Assert.assertEquals(tests.size(), 1);
        Assert.assertEquals(tests.get(0).getIdUser(), idUser);
    }

    @org.junit.Test
    public void testFindById() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        UUID id = tests.get(0).getIdTest();

        mvcResult = super.requestGet(URL_TEST_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Test test = super.mapFromJson(content, Test.class);
        Assert.assertEquals(test.getIdTest(), id);
    }

    @org.junit.Test
    public void testNotFoundById() throws Exception {
        UUID id = UUID.randomUUID();
        MvcResult mvcResult = super.requestGet(URL_TEST_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "Test could not be found with id: " + id);
    }

    @org.junit.Test
    public void testFindByUserIdAndTestId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        UUID idUser = tests.get(0).getIdUser();
        UUID idTest = tests.get(0).getIdTest();

        mvcResult = super.requestGet(URL_TEST_BY_USER_ID_AND_TEST_ID, idUser, idTest);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Test test = super.mapFromJson(content, Test.class);
        Assert.assertNotNull(test);
        Assert.assertEquals(test.getIdTest(), idTest);
        Assert.assertEquals(test.getIdUser(), idUser);
    }

    @org.junit.Test
    public void testNotFoundByUserIdAndTestId() throws Exception {
        UUID idUser = UUID.randomUUID();
        UUID idTest = UUID.randomUUID();

        MvcResult mvcResult = super.requestGet(URL_TEST_BY_USER_ID_AND_TEST_ID, idUser, idTest);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "Test could not be found with idUser: " + idUser + " and idTest: " + idTest);
    }

    @org.junit.Test
    public void testFindByTaskId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        UUID idTask = tests.get(0).getIdTask();

        mvcResult = super.requestGet(URL_TEST_BY_TASK_ID, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Test test = super.mapFromJson(content, Test.class);
        Assert.assertNotNull(test);
        Assert.assertEquals(test.getIdTask(), idTask);
    }

    @org.junit.Test
    public void testFindByUserIdAndTaskId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        UUID idUser = tests.get(0).getIdUser();
        UUID idTask = tests.get(0).getIdTask();

        mvcResult = super.requestGet(URL_TEST_BY_USER_ID_AND_TASK_ID, idUser, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Test test = super.mapFromJson(content, Test.class);
        Assert.assertNotNull(test);
        Assert.assertEquals(test.getIdTask(), idTask);
        Assert.assertEquals(test.getIdUser(), idUser);
    }

    @org.junit.Test
    public void testCreate() throws Exception {
        Test test = new Test();
        test.setCreateDate(new Date());
        test.setDescription("Новый тест");
        test.setIdUser(UUID.randomUUID());
        test.setIdTask(UUID.randomUUID());
        test.setSourceCode("public class Test {}");
        String inputJson = super.mapToJson(test);
        MvcResult mvcResult = super.requestPost(URL_TESTS_GET_CREATE, inputJson);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 201);
        String content = mvcResult.getResponse().getContentAsString();
        Test result = super.mapFromJson(content, Test.class);
        Assert.assertEquals(result.getDescription(), result.getDescription());
    }

    @org.junit.Test
    public void testBadRequestCreate() throws Exception {
        Test test = new Test();
        test.setCreateDate(new Date());
        test.setDescription("Новый тест");
        String inputJson = super.mapToJson(test);
        MvcResult mvcResult = super.requestPost(URL_TESTS_GET_CREATE, inputJson);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 400);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 400);
        Assert.assertEquals(errorResponse.getError(), "Bad Request");
    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        UUID id = tests.get(1).getIdTest();

        mvcResult = super.requestGet(URL_TEST_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        Test test = super.mapFromJson(content, Test.class);
        Assert.assertNotNull(test);

        String inputJson = super.mapToJson(test);
        mvcResult = requestPut(URL_TEST_GET_UPDATE_DELETE, inputJson, id);
        status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
    }

    @org.junit.Test
    public void testDelete() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        UUID id = tests.get(1).getIdTest();

        mvcResult = requestDelete(URL_TEST_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 204);
    }

    @org.junit.Test
    public void testDeleteByTaskId() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE + "?page={page}&size={size}", 0, 20);
        String content = mvcResult.getResponse().getContentAsString();
        Page<Test> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<Test>>() {});
        List<Test> tests = page.getContent();
        UUID idTask = tests.get(2).getIdTask();

        mvcResult = requestDelete(URL_TEST_BY_TASK_ID, idTask);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 204);
    }
}
