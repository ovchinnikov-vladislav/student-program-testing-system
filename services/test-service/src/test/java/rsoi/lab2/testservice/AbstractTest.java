package rsoi.lab2.testservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@WebAppConfiguration
public class AbstractTest {
    MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    MvcResult requestGet(String url, Object... param) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url, param).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
    }

    MvcResult requestPost(String url, String json, Object... param) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(url, param).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json)).andReturn();
    }

    MvcResult requestPut(String url, String json, Object... param) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.put(url, param).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json)).andReturn();
    }

    MvcResult requestDelete(String url, Object... param) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.delete(url, param).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();
    }

    String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    <T> T mapFromJson(String json, Class<T> clazz) throws JsonProcessingException, IOException {
        return new ObjectMapper().readValue(json, clazz);
    }
}
