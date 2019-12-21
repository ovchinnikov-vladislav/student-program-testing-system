package rsoi.lab2.gservice.client.fallback.factory;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import rsoi.lab2.gservice.client.ResultClient;
import rsoi.lab2.gservice.client.fallback.ResultFeignClientFallback;

@Component
public class ResultFallbackFactory implements FallbackFactory<ResultClient> {

    @Override
    public ResultClient create(Throwable cause) {
        return new ResultFeignClientFallback(cause);
    }
}
