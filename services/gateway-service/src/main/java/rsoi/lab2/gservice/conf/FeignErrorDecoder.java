package rsoi.lab2.gservice.conf;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;

import java.io.IOException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                logger.error("Status code " + response.status() + ", methodKey = " + methodKey);
                String message = "";
                try {
                    message = new String(response.body().asInputStream().readAllBytes());
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
                return new HttpCanNotCreateException(message);
            case 404: {
                logger.error("Error took place when using Feign client to send HTTP Request. Status code " + response.status() + ", methodKey = " + methodKey);
                return new HttpNotFoundException("Not found object.");
            }
            default:
                logger.error("Status code " + response.status() + ", methodKey = " + methodKey);
                return new Exception(response.reason());
        }
    }
}
