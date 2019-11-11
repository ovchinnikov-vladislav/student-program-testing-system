package rsoi.lab2.uservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.uservice.entity.User;
import rsoi.lab2.uservice.exception.HttpNotFoundException;
import rsoi.lab2.uservice.model.SomeUsersModel;
import rsoi.lab2.uservice.repository.UserRepository;
import rsoi.lab2.uservice.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<SomeUsersModel> getAll() {
        logger.info("getAll() method called:");
        List<SomeUsersModel> result = userRepository.findAllUsers();
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<SomeUsersModel> getAll(Pageable pageable) {
        logger.info("getAll() method called: ");
        List<SomeUsersModel> result = userRepository.findAllUsers(pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User getById(Long id) {
        logger.info("getById() method called: ");
        User result = userRepository.findById(id).orElseThrow(() -> new HttpNotFoundException("Not found User by id = " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User getByUserName(String userName) {
        logger.info("getByUserName() method called: ");
        User result = userRepository.findByUserName(userName).orElseThrow(() -> new HttpNotFoundException("Not found User by userName = " + userName));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User getByEmail(String email) {
        logger.info("getByEmail() method called: ");
        User result = userRepository.findByEmail(email).orElseThrow(() -> new HttpNotFoundException("Not found User by email = " + email));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User add(User user) {
        logger.info("add() method called: ");
        User result = userRepository.saveAndFlush(user);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User update(User user) {
        logger.info("update() method called: ");
        User result = userRepository.saveAndFlush(user);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void delete(Long id) {
        logger.info("delete() method called.");
        userRepository.deleteById(id);
    }
}
