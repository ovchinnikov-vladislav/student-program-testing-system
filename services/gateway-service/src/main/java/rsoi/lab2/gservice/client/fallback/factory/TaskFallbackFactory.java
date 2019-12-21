package rsoi.lab2.gservice.client.fallback.factory;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import rsoi.lab2.gservice.client.TaskClient;
import rsoi.lab2.gservice.client.fallback.TaskFeignClientFallback;

@Component
public class TaskFallbackFactory implements FallbackFactory<TaskClient> {
    @Override
    public TaskClient create(Throwable cause) {
        return new TaskFeignClientFallback(cause);
    }
}
