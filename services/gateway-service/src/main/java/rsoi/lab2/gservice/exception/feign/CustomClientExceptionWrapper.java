package rsoi.lab2.gservice.exception.feign;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class CustomClientExceptionWrapper extends HystrixBadRequestException {
    public CustomClientExceptionWrapper(String message) {
        super(message);
    }
}
