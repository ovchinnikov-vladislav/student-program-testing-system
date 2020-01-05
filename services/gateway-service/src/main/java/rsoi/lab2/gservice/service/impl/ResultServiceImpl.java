package rsoi.lab2.gservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.ResultClient;
import rsoi.lab2.gservice.conf.WebConfig;
import rsoi.lab2.gservice.entity.result.Result;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.exception.feign.ClientAuthenticationExceptionWrapper;
import rsoi.lab2.gservice.exception.jwt.JwtAuthenticationException;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.ResultService;

import java.util.*;

@Service
public class ResultServiceImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);

    private static String token = null;

    @Autowired
    private ResultClient resultClient;

    @Override
    public Result findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        try {
            logger.info("findByUserIdAndTaskId() method called:");
            Result result = resultClient.findByUserIdAndTaskId(idUser, idTask, token)
                    .orElseThrow(() -> new HttpNotFoundException("Result could not be found with idUser: " + idUser + " and idTask: " + idTask));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getIdTask().equals(zeroUUID) || result.getIdUser().equals(zeroUUID))
                throw new ServiceAccessException("Result service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findByUserIdAndTaskId(idUser, idTask);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }

    @Override
    public PageCustom<Result> findAll(Integer page, Integer size) {
        try {
            logger.info("findAll() method called:");
            PageCustom<Result> results = resultClient.findAll(page, size, token);

            if (results == null)
                throw new ServiceAccessException("Result service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findAll(page, size);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }

    @Override
    public PageCustom<Result> findByTaskId(UUID id, Integer page, Integer size) {
        try {
            logger.info("findByTaskId() method called:");
            PageCustom<Result> results = resultClient.findByTaskId(id, page, size, token);

            if (results == null)
                throw new ServiceAccessException("Result service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findByTaskId(id, page, size);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }

    @Override
    public PageCustom<Result> findByUserId(UUID id, Integer page, Integer size) {
        try {
            logger.info("findByUserId() method called:");
            PageCustom<Result> results = resultClient.findByUserId(id, page, size, token);

            if (results == null)
                throw new ServiceAccessException("Result service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return findByUserId(id, page, size);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }

    @Override
    public Result create(Result result) {
        try {
            logger.info("create() method called:");
            Result res = resultClient.create(result, token)
                    .orElseThrow(() -> new HttpCanNotCreateException("Result could not be created"));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getIdTask().equals(zeroUUID) || result.getIdUser().equals(zeroUUID))
                throw new ServiceAccessException("Result service unavailable.");

            logger.info("\t" + res);
            return res;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                return create(result);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }

    @Override
    public void update(Result result) {
        try {
            logger.info("update() method called:");
            Result checkResult = resultClient.findByUserIdAndTaskId(result.getIdUser(), result.getIdTask(), token)
                    .orElseThrow(() -> new HttpNotFoundException("Result could not be found with idUser: " + result.getIdUser()
                            + " and idTask: " + result.getIdTask()));

            UUID zeroUUID = new UUID(0, 0);
            if (checkResult.getIdTask().equals(zeroUUID) || checkResult.getIdUser().equals(zeroUUID))
                throw new ServiceAccessException("Result service unavailable.");

            resultClient.update(result.getIdUser(), result.getIdTask(), result, token);
            logger.info("\t" + result);
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                update(result);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }

    @Override
    public void delete(UUID idUser, UUID idTask) {
        try {
            logger.info("delete() method called:" + idUser + " " + idTask);
            Result checkResult = resultClient.findByUserIdAndTaskId(idUser, idTask, token)
                    .orElseThrow(() -> new HttpNotFoundException("Result could not be found with idUser: " + idUser
                            + " and idTask: " + idTask));

            UUID zeroUUID = new UUID(0, 0);
            if (checkResult.getIdTask().equals(zeroUUID) || checkResult.getIdUser().equals(zeroUUID))
                throw new ServiceAccessException("Result service unavailable.");

            resultClient.delete(idUser, idTask, token);
        } catch (ClientAuthenticationExceptionWrapper exc) {
            token = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (token != null) {
                token = "Bearer " + token;
                delete(idUser, idTask);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }
}
