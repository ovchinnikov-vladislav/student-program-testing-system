package rsoi.lab2.rservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.rservice.entity.Result;
import rsoi.lab2.rservice.exception.HttpNotFoundException;
import rsoi.lab2.rservice.repository.ResultRepository;
import rsoi.lab2.rservice.service.ResultService;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public List<Result> getAll() {
        logger.info("getAll() method called:");
        List<Result> result = resultRepository.findAll();
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Result> getAll(Pageable pageable) {
        logger.info("getAll() method called:");
        List<Result> result = resultRepository.findAll(pageable).getContent();
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Result> getByTaskId(Long id) {
        logger.info("getByTaskId() method called:");
        List<Result> result = resultRepository.findByIdTask(id);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Result> getByTaskId(Long id, Pageable pageable) {
        logger.info("getTaskId() method called:");
        List<Result> result = resultRepository.findByIdTask(id, pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Result> getByUserId(Long id) {
        logger.info("getByUserId() method called:");
        List<Result> result = resultRepository.findByIdUser(id);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Result> getByUserId(Long id, Pageable pageable) {
        logger.info("getByUserId() method called:");
        List<Result> result = resultRepository.findByIdUser(id, pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Result getByUserIdAndTaskId(Long idUser, Long idTask) {
        logger.info("getByUserIdAndTaskId() method called:");
        Result result = resultRepository.findByIdUserAndIdTask(idUser, idTask).orElseThrow(
                () -> new HttpNotFoundException("Not found Result by idUser = " + idUser + " and idTask = " + idTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Result add(Result result) {
        logger.info("add() method called:");
        Result res = resultRepository.saveAndFlush(result);
        logger.info("\t" + res);
        return result;
    }

    @Override
    public void update(Result result) {
        logger.info("update() method called:");
        Result res = resultRepository.saveAndFlush(result);
        logger.info("\t" + res);
    }

    @Override
    public void delete(Long idUser, Long idTask) {
        logger.info("delete() method called.");
        resultRepository.deleteByUserIdAndTaskId(idUser, idTask);
    }
}
