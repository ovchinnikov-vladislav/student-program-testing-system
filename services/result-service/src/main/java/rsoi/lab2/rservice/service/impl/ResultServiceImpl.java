package rsoi.lab2.rservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.rservice.entity.Result;
import rsoi.lab2.rservice.exception.HttpNotFoundException;
import rsoi.lab2.rservice.repository.ResultRepository;
import rsoi.lab2.rservice.service.ResultService;

import java.util.List;
import java.util.UUID;

@Service
public class ResultServiceImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public Page<Result> findAll(Pageable pageable) {
        logger.info("findAll() method called:");
        Page<Result> result = resultRepository.findAll(pageable);
        logger.info("\t" + result.getContent());
        return result;
    }

    @Override
    public Page<Result> findByTaskId(UUID id, Pageable pageable) {
        logger.info("findTaskId() method called:");
        Page<Result> result = resultRepository.findByIdTask(id, pageable);
        logger.info("\t" + result.getContent());
        return result;
    }

    @Override
    public Page<Result> findByUserId(UUID id, Pageable pageable) {
        logger.info("findByUserId() method called:");
        Page<Result> result = resultRepository.findByIdUser(id, pageable);
        logger.info("\t" + result.getContent());
        return result;
    }

    @Override
    public Result findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        logger.info("findByUserIdAndTaskId() method called:");
        Result result = resultRepository.findByIdUserAndIdTask(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException("Result could not be found with idUser: " + idUser + " and idTask: " + idTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Result create(Result result) {
        logger.info("create() method called:");
        Result res = resultRepository.saveAndFlush(result);
        logger.info("\t" + res);
        return result;
    }

    @Override
    public void update(UUID idUser, UUID idTask, Result result) {
        logger.info("update() method called:");
        result.setIdUser(idUser);
        result.setIdTask(idTask);
        Result res = resultRepository.saveAndFlush(result);
        logger.info("\t" + res);
    }

    @Override
    public void delete(UUID idUser, UUID idTask) {
        logger.info("delete() method called.");
        resultRepository.deleteByIdUserAndIdTask(idUser, idTask);
    }
}
