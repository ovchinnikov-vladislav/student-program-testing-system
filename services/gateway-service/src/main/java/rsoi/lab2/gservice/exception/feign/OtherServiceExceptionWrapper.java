package rsoi.lab2.gservice.exception.feign;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class OtherServiceExceptionWrapper extends HystrixBadRequestException {
    public OtherServiceExceptionWrapper(String message) {
        super(message);
    }
}
