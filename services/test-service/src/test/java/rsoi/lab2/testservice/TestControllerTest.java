package rsoi.lab2.testservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import rsoi.lab2.testservice.entity.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import rsoi.lab2.testservice.model.ErrorResponse;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestServiceApp.class)
@TestPropertySource("classpath:application-h2.properties")
public class TestControllerTest extends AbstractTest {

    private static final String URL_TESTS_GET_CREATE = "http://localhost:8083/tests";
    private static final String URL_TEST_GET_UPDATE_DELETE = "http://localhost:8083/tests/{id}";
    private static final String URL_TESTS_BY_USER_ID = "http://localhost:8083/users/{id}/tests";
    private static final String URL_TEST_BY_USER_ID_AND_TEST_ID = "http://localhost:8083/users/{idUser}/tests/{idTest}";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_TESTS_GET_CREATE);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Test[] tests = super.mapFromJson(content, Test[].class);
        Assert.assertEquals(tests.length, 2);
    }

    @org.junit.Test
    public void testFindByUserId() throws Exception {
        int idUser = 1;
        MvcResult mvcResult = super.requestGet(URL_TESTS_BY_USER_ID, 1);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Test[] tests = super.mapFromJson(content, Test[].class);
        Assert.assertEquals(tests.length, 2);
    }

    @org.junit.Test
    public void testFindById() throws Exception {
        int id = 2;
        MvcResult mvcResult = super.requestGet(URL_TEST_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Test test = super.mapFromJson(content, Test.class);
        Assert.assertEquals(id, test.getIdTest().longValue());
    }

    @org.junit.Test
    public void testNotFoundById() throws Exception {
        int id = 5;
        MvcResult mvcResult = super.requestGet(URL_TEST_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError(), "404 NOT_FOUND");
        Assert.assertEquals(errorResponse.getMessage(), "Not found Test by id = " + id);
    }

    @org.junit.Test
    public void testFindByUserIdAndTestId() throws Exception {
        int idUser = 1;
        int idTest = 2;
        MvcResult mvcResult = super.requestGet(URL_TEST_BY_USER_ID_AND_TEST_ID, idUser, idTest);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Test test = super.mapFromJson(content, Test.class);
        Assert.assertNotNull(test);
        Assert.assertEquals(test.getIdTest().longValue(), idTest);
        Assert.assertEquals(test.getIdUser().longValue(), idUser);
    }

    @org.junit.Test
    public void testNotFoundByUserIdAndTestId() throws Exception {
        int idUser = 3;
        int idTest = 1;
        MvcResult mvcResult = super.requestGet(URL_TEST_BY_USER_ID_AND_TEST_ID, idUser, idTest);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getError(), "404 NOT_FOUND");
        Assert.assertEquals(errorResponse.getMessage(), "Not found Test by idTest = " + idTest + " and idUser = " + idUser);
    }

    @org.junit.Test
    public void testCreate() throws Exception {
        Test test = new Test();
        test.setCreateDate(new Date());
        test.setDescription("Новый тест");
        test.setIdUser(1L);
        test.setIdTask(3L);
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
        Assert.assertEquals(errorResponse.getError(), "400 BAD_REQUEST");
    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        int id = 2;
        MvcResult mvcResult = super.requestGet(URL_TEST_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Test test = super.mapFromJson(content, Test.class);
        Assert.assertNotNull(test);

        String inputJson = super.mapToJson(test);
        mvcResult = requestPut(URL_TEST_GET_UPDATE_DELETE, inputJson, id);
        status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
    }

    @org.junit.Test
    public void testDelete() throws Exception {
        int id = 1;
        MvcResult mvcResult = requestDelete(URL_TEST_GET_UPDATE_DELETE, id);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 204);
    }
}
