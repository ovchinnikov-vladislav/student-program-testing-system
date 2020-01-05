package rsoi.lab2.gservice.client.fallback.factory;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import rsoi.lab2.gservice.client.SessionClient;
import rsoi.lab2.gservice.client.fallback.SessionFeignClientFallback;

@Component
public class SessionFallbackFactory implements FallbackFactory<SessionClient> {
    @Override
    public SessionClient create(Throwable cause) {
        return new SessionFeignClientFallback(cause);
    }
}
