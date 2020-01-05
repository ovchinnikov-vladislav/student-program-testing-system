package rsoi.lab2.testservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.exception.HttpNotFoundException;
import rsoi.lab2.testservice.model.SomeTestsModel;
import rsoi.lab2.testservice.repository.TestRepository;
import rsoi.lab2.testservice.service.TestService;

import java.util.List;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private TestRepository testRepository;

    @Override
    public Test findById(UUID id) {
        logger.info("findById() method called: ");
        Test result = testRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id: " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test findByUserIdAndTestId(UUID idUser, UUID idTest) {
        logger.info("findByUserIdAndTestId() method called:");
        Test result = testRepository.findByIdUserAndId(idUser, idTest)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser: " + idUser + " and idTest: " + idTest));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Page<SomeTestsModel> findAll(Pageable pageable) {
        logger.info("findAll() method called: ");
        Page<SomeTestsModel> result = testRepository.findAllTests(pageable);
        logger.info("\t" + result.getContent());
        return result;
    }

    @Override
    public Page<SomeTestsModel> findByUserId(UUID id, Pageable pageable) {
        logger.info("findByUserId() method called:");
        Page<SomeTestsModel> result = testRepository.findByIdUser(id, pageable);
        logger.info("\t" + result.getContent());
        return result;
    }

    @Override
    public Test findByTaskId(UUID id) {
        logger.info("findByTaskId() method called:");
        Test result = testRepository.findByIdTask(id)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idTask: " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        logger.info("findByUserIdAndTaskId() method called:");
        Test result = testRepository.findByIdUserAndIdTask(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser: " + idUser + " and idTask: " + idTask));
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
    public void update(UUID id, Test test) {
        logger.info("update() method called: ");
        test.setId(id);
        Test result = testRepository.saveAndFlush(test);
        logger.info("\t" + result);
    }

    @Override
    public void delete(UUID id) {
        logger.info("delete() method called.");
        testRepository.deleteById(id);
    }

    @Override
    public void deleteByTaskId(UUID id) {
        logger.info("deleteByTaskId() method called.");
        testRepository.deleteByIdTask(id);
    }
}
