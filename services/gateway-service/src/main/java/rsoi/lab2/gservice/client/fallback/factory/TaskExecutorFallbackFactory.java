package rsoi.lab2.gservice.client.fallback.factory;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import rsoi.lab2.gservice.client.TaskExecutorClient;
import rsoi.lab2.gservice.client.fallback.TaskExecutorFeignClientFallback;

@Component
public class TaskExecutorFallbackFactory implements FallbackFactory<TaskExecutorClient> {
    @Override
    public TaskExecutorClient create(Throwable cause) {
        return new TaskExecutorFeignClientFallback(cause);
    }
}
