package rsoi.lab2.testservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.exception.HttpNotFoundException;
import rsoi.lab2.testservice.repository.TestRepository;
import rsoi.lab2.testservice.service.TestService;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private TestRepository testRepository;

    @Override
    public Test getById(Long id) {
        logger.info("getById() method called: ");
        Test result = testRepository.findById(id).orElseThrow(() -> new HttpNotFoundException("Not found Test by id = " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test getByUserIdAndTestId(Long idUser, Long idTest) {
        logger.info("getByUserIdAndTestId() method called:");
        Test result = testRepository.findByIdUserAndIdTest(idUser, idTest).orElseThrow(() -> new HttpNotFoundException("Not found Test by idTest = " + idTest + " and idUser = " + idUser));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Test> getAll() {
        logger.info("getAll() method called: ");
        List<Test> result = testRepository.findAll();
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Test> getAll(Pageable pageable) {
        logger.info("getAll() method called: ");
        List<Test> result = testRepository.findAll(pageable).getContent();
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Test> getByUserId(Long id) {
        logger.info("getByUserId() method called:");
        List<Test> result = testRepository.findByIdUser(id);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Test> getByUserId(Long id, Pageable pageable) {
        logger.info("getByUserId() method called:");
        List<Test> result = testRepository.findByIdUser(id, pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Test> getByTaskId(Long id) {
        logger.info("getByTaskId() method called:");
        List<Test> result = testRepository.findByIdTask(id);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Test> getByTaskId(Long id, Pageable pageable) {
        logger.info("getByTaskId() method called:");
        List<Test> result = testRepository.findByIdTask(id, pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Test> getByUserIdAndTaskId(Long idUser, Long idTask) {
        logger.info("getByUserIdAndTaskId() method called:");
        List<Test> result = testRepository.findByIdUserAndIdTask(idUser, idTask);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<Test> getByUserIdAndTaskId(Long idUser, Long idTask, Pageable pageable) {
        logger.info("getByUserIdAndTaskId() method called:");
        List<Test> result = testRepository.findByIdUserAndIdTask(idUser, idTask, pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test create(Test test) {
        logger.info("create() method called: ");
        Test result = testRepository.saveAndFlush(test);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void update(Test test) {
        logger.info("update() method called: ");
        Test result = testRepository.saveAndFlush(test);
        logger.info("\t" + result);
    }

    @Override
    public void delete(Long id) {
        logger.info("delete() method called.");
        testRepository.deleteById(id);
    }

    @Override
    public void deleteByTaskId(Long id) {
        logger.info("deleteByTaskId() method called.");
        testRepository.deleteByTaskId(id);
    }
}
