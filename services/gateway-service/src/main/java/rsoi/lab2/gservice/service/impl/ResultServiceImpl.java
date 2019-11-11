package rsoi.lab2.gservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.ResultClient;
import rsoi.lab2.gservice.conf.FeignErrorDecoder;
import rsoi.lab2.gservice.entity.Result;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.service.ResultService;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);

    @Autowired
    private ResultClient resultClient;

    @Override
    public Result findByUserIdAndTaskId(Long idUser, Long idTask) {
        logger.info("findByUserIdAndTaskId() method called:");
        Result result = resultClient.findByUserIdAndTaskId(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException("Not found Result by IdUser = " + idUser + " and idTask = " + idTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Result[] findAll(Integer page, Integer size) {
        logger.info("findAll() method called:");
        Result[] results = resultClient.findAll(page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public Result[] findByTaskId(Long id, Integer page, Integer size) {
        logger.info("findByTaskId() method called:");
        Result[] results = resultClient.findByTaskId(id, page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public Result[] findByUserId(Long id, Integer page, Integer size) {
        logger.info("findByUserId() method called:");
        Result[] results = resultClient.findByUserId(id, page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public Result create(Result result) {
        logger.info("create() method called:");
        Result res = resultClient.create(result).orElseThrow(() -> new HttpCanNotCreateException("Cannot create Result"));
        logger.info("\t" + res);
        return res;
    }

    @Override
    public void update(Long idUser, Long idTask, Result result) {
        logger.info("update() method called:");
        resultClient.update(idUser, idTask, result);
        logger.info("\t" + result);
    }

    @Override
    public void delete(Long idUser, Long idTask) {
        logger.info("delete() method called:" + idUser + " " + idTask);
        resultClient.delete(idUser, idTask);
    }
}
