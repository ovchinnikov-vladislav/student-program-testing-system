package rsoi.lab2.uservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.uservice.entity.User;
import rsoi.lab2.uservice.exception.HttpNotFoundException;
import rsoi.lab2.uservice.model.PageCustom;
import rsoi.lab2.uservice.model.SomeUsersModel;
import rsoi.lab2.uservice.repository.UserRepository;
import rsoi.lab2.uservice.service.UserService;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<SomeUsersModel> findAll(Pageable pageable) {
        logger.info("findAll() method called: ");
        Page<SomeUsersModel> result = userRepository.findAllUsers(pageable);
        logger.info("\t" + result.getContent());
        return result;
    }

    @Override
    public SomeUsersModel findById(UUID id) {
        logger.info("findById() method called: ");
        SomeUsersModel result = userRepository.findByIdUser(id)
                .orElseThrow(() -> new HttpNotFoundException(String.format("User could not be found with id: %s", id)));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User findByUserName(String userName) {
        logger.info("findByUserName() method called: ");
        User result = userRepository.findByUserName(userName)
                .orElseThrow(() -> new HttpNotFoundException(String.format("User could not be found with username: %s", userName)));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User findByEmail(String email) {
        logger.info("findByEmail() method called: ");
        User result = userRepository.findByEmail(email)
                .orElseThrow(() -> new HttpNotFoundException(String.format("User could not be found with email: %s", email)));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User create(User user) {
        logger.info("create() method called: ");
        checkUserData(user);
        User result = userRepository.saveAndFlush(user);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public User update(UUID id, User user) {
        logger.info("update() method called: ");
        user.setIdUser(id);
        checkUserData(user);
        User result = userRepository.saveAndFlush(user);
        logger.info("\t" + result);
        return result;
    }

    private void checkUserData(User user) {
        User checkLogin = userRepository.findByUserName(user.getUserName()).orElse(null);
        User checkEmail = userRepository.findByEmail(user.getEmail()).orElse(null);

        if (checkLogin != null && checkEmail != null)
            throw new ConstraintViolationException("(username, email)=("+user.getUserName()+", "+user.getEmail()+")", null);
        else if (checkLogin != null)
            throw new ConstraintViolationException("(username)=("+user.getUserName()+")", null);
        else if (checkEmail != null)
            throw new ConstraintViolationException("(email)=("+user.getEmail()+")", null);
    }

    @Override
    public void delete(UUID id) {
        logger.info("delete() method called.");
        userRepository.deleteById(id);
    }
}
