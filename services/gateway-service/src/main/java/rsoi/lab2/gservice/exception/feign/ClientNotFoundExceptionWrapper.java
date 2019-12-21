package rsoi.lab2.gservice.exception.feign;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class ClientNotFoundExceptionWrapper extends HystrixBadRequestException {
    public ClientNotFoundExceptionWrapper(String message) {
        super(message);
    }
}
