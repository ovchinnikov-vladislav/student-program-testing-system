package rsoi.lab2.gservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.TestClient;
import rsoi.lab2.gservice.conf.WebConfig;
import rsoi.lab2.gservice.entity.test.Test;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.exception.feign.ClientAuthenticationExceptionWrapper;
import rsoi.lab2.gservice.exception.jwt.JwtAuthenticationException;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.TestService;

import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    private static String token = null;

    @Autowired
    private TestClient testClient;

    @Override
    public Test findById(UUID id) {
        try {
            logger.info("findById() method called:");
            Test result = testClient.findById(id, token)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id: " + id));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findById(id);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public Test findByUserIdAndTestId(UUID idUser, UUID idTest) {
        try {
            logger.info("findByUserIdAndTestId() method called:");
            Test result = testClient.findByUserIdAndTestId(idUser, idTest, token)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser = " + idUser + " and idTest: " + idTest));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findByUserIdAndTestId(idUser, idTest);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public PageCustom<Test> findAll(Integer page, Integer size) {
        try {
            logger.info("findAll() method called:");
            PageCustom<Test> results = testClient.findAll(page, size, token);
            if (results == null)
                throw new ServiceAccessException("Test service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findAll(page, size);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public PageCustom<Test> findByUserId(UUID idUser, Integer page, Integer size) {
        try {
            logger.info("findByUserId() method called:");
            PageCustom<Test> results = testClient.findByUserId(idUser, page, size, token);
            if (results == null)
                throw new ServiceAccessException("Test service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findByUserId(idUser, page, size);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public Test findByTaskId(UUID idTask) {
        try {
            logger.info("findByTaskId() method called:");
            Test result = testClient.findByTaskId(idTask, token)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idTask = " + idTask));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findByTaskId(idTask);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public Test findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        try {
            logger.info("findByUserIdAndTaskId() method called:");
            Test result = testClient.findByUserIdAndTaskId(idUser, idTask, token)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser: " + idUser + " and idTask: " + idTask));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findByUserIdAndTaskId(idUser, idTask);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public Test create(UUID idUser, UUID idTask, Test test) {
        try {
            logger.info("create() method called:");
            test.setIdUser(idUser);
            test.setIdTask(idTask);

            Test result = testClient.create(test, token)
                    .orElseThrow(() -> new HttpCanNotCreateException("Test could not be created"));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return create(idUser, idTask, test);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public void update(UUID idUser, UUID idTask, UUID idTest, Test test) {
        try {
            logger.info("update() method called:");
            Test checkTest = testClient.findById(idTest, token)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id: " + idTest));

            UUID zeroUUID = new UUID(0, 0);
            if (checkTest.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            test.setIdUser(idUser);
            test.setIdTask(idTask);
            test.setId(idTest);

            testClient.update(idTest, test, token);
            logger.info("\t" + test);
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                update(idUser, idTask, idTest, test);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            logger.info("delete() method called: " + id);
            Test checkTest = testClient.findById(id, token)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id: " + id));

            UUID zeroUUID = new UUID(0, 0);
            if (checkTest.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            testClient.delete(id, token);
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                delete(id);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public void deleteByTaskId(UUID id) {
        try {
            logger.info("deleteByTaskId() method called: " + id);
            Test checkTest = testClient.findByTaskId(id, token)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idTask: " + id));

            UUID zeroUUID = new UUID(0, 0);
            if (checkTest.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            testClient.deleteByTaskId(id, token);
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                deleteByTaskId(id);
            } else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }
}
