package rsoi.lab2.gservice.client.fallback.factory;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import rsoi.lab2.gservice.client.TestClient;
import rsoi.lab2.gservice.client.fallback.TestFeignClientFallback;

@Component
public class TestFallbackFactory implements FallbackFactory<TestClient> {
    @Override
    public TestClient create(Throwable cause) {
        return new TestFeignClientFallback(cause);
    }
}
