package rsoi.lab2.testservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.exception.HttpNotFoundException;
import rsoi.lab2.testservice.model.SomeTestsModel;
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
    public List<SomeTestsModel> getAll() {
        logger.info("getAll() method called: ");
        List<SomeTestsModel> result = testRepository.findAllTests();
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<SomeTestsModel> getAll(Pageable pageable) {
        logger.info("getAll() method called: ");
        List<SomeTestsModel> result = testRepository.findAllTests(pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<SomeTestsModel> getByUserId(Long id) {
        logger.info("getByUserId() method called:");
        List<SomeTestsModel> result = testRepository.findByIdUser(id);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<SomeTestsModel> getByUserId(Long id, Pageable pageable) {
        logger.info("getByUserId() method called:");
        List<SomeTestsModel> result = testRepository.findByIdUser(id, pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test getByTaskId(Long id) {
        logger.info("getByTaskId() method called:");
        Test result = testRepository.findByIdTask(id)
                .orElseThrow(() -> new HttpNotFoundException("Not found Test by idTask = " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test getByUserIdAndTaskId(Long idUser, Long idTask) {
        logger.info("getByUserIdAndTaskId() method called:");
        Test result = testRepository.findByIdUserAndIdTask(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException("Not found Test by idUser = " + idUser + " and idTask = " + idTask));
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
