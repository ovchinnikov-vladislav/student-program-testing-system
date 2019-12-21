package rsoi.lab2.gservice.exception.feign;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class ClientBadResponseExceptionWrapper extends HystrixBadRequestException {
    public ClientBadResponseExceptionWrapper(String message) {
        super(message);
    }
}
