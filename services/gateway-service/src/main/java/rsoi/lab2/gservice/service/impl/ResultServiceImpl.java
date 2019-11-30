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
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.ResultService;

import java.util.*;

@Service
public class ResultServiceImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);

    @Autowired
    private ResultClient resultClient;

    @Override
    public Result findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        logger.info("findByUserIdAndTaskId() method called:");
        Result result = resultClient.findByUserIdAndTaskId(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException("Result could not be found with idUser: " + idUser + " and idTask: " + idTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public PageCustom<Result> findAll(Integer page, Integer size) {
        logger.info("findAll() method called:");
        PageCustom<Result> results = resultClient.findAll(page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public PageCustom<Result> findByTaskId(UUID id, Integer page, Integer size) {
        logger.info("findByTaskId() method called:");
        PageCustom<Result> results = resultClient.findByTaskId(id, page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public PageCustom<Result> findByUserId(UUID id, Integer page, Integer size) {
        logger.info("findByUserId() method called:");
        PageCustom<Result> results = resultClient.findByUserId(id, page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public Result create(Result result) {
        logger.info("create() method called:");
        Result res = resultClient.create(result).orElseThrow(() -> new HttpCanNotCreateException("Result could not be created"));
        logger.info("\t" + res);
        return res;
    }

    @Override
    public void update(Result result) {
        logger.info("update() method called:");
        resultClient.update(result.getIdUser(), result.getIdTask(), result);
        logger.info("\t" + result);
    }

    @Override
    public void delete(UUID idUser, UUID idTask) {
        logger.info("delete() method called:" + idUser + " " + idTask);
        resultClient.delete(idUser, idTask);
    }
}
