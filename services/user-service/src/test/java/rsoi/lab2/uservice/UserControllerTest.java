package rsoi.lab2.uservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rsoi.lab2.uservice.entity.User;
import rsoi.lab2.uservice.model.ErrorResponse;
import rsoi.lab2.uservice.model.PageCustom;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserServiceApp.class)
@TestPropertySource("classpath:application-h2.properties")
public class UserControllerTest extends AbstractTest {

    private static final String URL_USERS_GET_CREATE = "http://localhost:8084/users";
    private static final String URL_USER_GET_UPDATE_DELETE = "http://localhost:8084/users/{id}";
    private static final String URL_USERS_CHECK = "http://localhost:8084/users/check";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testFindAll() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_USERS_GET_CREATE + "?page={page}&size={size}", 0, 100);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        Page<User> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<User>>() {});
        List<User> users = page.getContent();
        Assert.assertEquals(users.size(), 3);
    }

    @Test
    public void testFindById() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_USERS_GET_CREATE + "?page={page}&size={size}", 0, 100);
        String content = mvcResult.getResponse().getContentAsString();
        Page<User> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<User>>() {});
        List<User> users = page.getContent();
        UUID uuid = users.get(0).getIdUser();

        mvcResult = super.requestGet(URL_USER_GET_UPDATE_DELETE, uuid);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        content = mvcResult.getResponse().getContentAsString();
        User user = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getIdUser(), uuid);
    }

    @Test
    public void testNotFoundById() throws Exception {
        UUID uuid = UUID.randomUUID();
        MvcResult mvcResult = super.requestGet(URL_USER_GET_UPDATE_DELETE, uuid);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "User could not be found with id: " + uuid);
    }

    @Test
    public void testFindByUserName() throws Exception {
        String inputJson = "{\"userName\": \"user1\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        User user = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getUserName(), "user1");
    }

    @Test
    public void testNotFoundByUserName() throws Exception {
        String inputJson = "{\"userName\": \"user6\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "User could not be found with username: user6");
    }

    @Test
    public void testFindByEmail() throws Exception {
        String inputJson = "{\"email\": \"user2@gmail.com\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        User user = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getEmail(), "user2@gmail.com");
    }

    @Test
    public void testNotFoundByEmail() throws Exception {
        String inputJson = "{\"email\": \"user6@gmail.com\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "User could not be found with email: user6@gmail.com");
    }

    @Test
    public void testFindByUserNameEmailPassword() throws Exception {
        String inputJson = "{" +
                "\"userName\": \"user1\"," +
                "\"email\": \"user1@gmail.com\"," +
                "\"password\": \"25D55AD283AA400AF464C76D713C07AD\"" +
                "}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
        String content = mvcResult.getResponse().getContentAsString();
        User user = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getUserName(), "user1");
    }

    @Test
    public void testNotFoundByUserNameEmailPassword() throws Exception {
        String inputJson = "{" +
                "\"userName\": \"user6\"," +
                "\"email\": \"user6@gmail.com\"," +
                "\"password\": \"12345\"" +
                "}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URL_USERS_CHECK).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 404);
        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = super.mapFromJson(content, ErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), 404);
        Assert.assertEquals(errorResponse.getError(), "Not Found");
        Assert.assertEquals(errorResponse.getMessage(), "User could not be found with username: user6");
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User();
        user.setUserName("user4");
        user.setPassword("12345678");
        user.setEmail("user4@gmail.com");
        user.setFirstName("user4fn");
        user.setLastName("user4ln");
        user.setGroup((byte) 1);
        user.setStatus((byte) 1);
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = super.requestPost(URL_USERS_GET_CREATE, inputJson);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 201);
        String content = mvcResult.getResponse().getContentAsString();
        User result = super.mapFromJson(content, User.class);
        Assert.assertEquals(user.getUserName(), result.getUserName());
    }

    @Test
    public void testUpdate() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_USERS_GET_CREATE + "?page={page}&size={size}", 0, 100);
        String content = mvcResult.getResponse().getContentAsString();
        Page<User> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<User>>() {});
        List<User> users = page.getContent();
        UUID uuid = users.get(1).getIdUser();

        mvcResult = super.requestGet(URL_USER_GET_UPDATE_DELETE, uuid);
        int statusGet = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusGet, 200);
        content = mvcResult.getResponse().getContentAsString();
        User user = super.mapFromJson(content, User.class);
        Assert.assertNotNull(user);

        user.setUserName("user5");
        String inputJson = super.mapToJson(user);
        mvcResult = super.requestPut(URL_USER_GET_UPDATE_DELETE, inputJson, uuid);
        int statusPut = mvcResult.getResponse().getStatus();
        Assert.assertEquals(statusPut, 200);
        content = mvcResult.getResponse().getContentAsString();
        User result = mapFromJson(content, User.class);
        Assert.assertEquals(user, result);
    }

    @Test
    public void testDelete() throws Exception {
        MvcResult mvcResult = super.requestGet(URL_USERS_GET_CREATE + "?page={page}&size={size}", 0, 100);
        String content = mvcResult.getResponse().getContentAsString();
        Page<User> page = new ObjectMapper().readValue(content, new TypeReference<PageCustom<User>>() {});
        List<User> users = page.getContent();
        UUID uuid = users.get(2).getIdUser();

        mvcResult = super.requestDelete(URL_USER_GET_UPDATE_DELETE, uuid);
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 204);
    }
}
