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
        try {
            String message = new String(response.body().asInputStream().readAllBytes());
            logger.error("Status code " + response.status() + ", methodKey = " + methodKey);
            switch (response.status()) {
                case 400:
                    return new HttpCanNotCreateException(message);
                case 404:
                    return new HttpNotFoundException(message);
                case 500:
                    return new Exception(message);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return new Exception(response.reason());
    }
}
