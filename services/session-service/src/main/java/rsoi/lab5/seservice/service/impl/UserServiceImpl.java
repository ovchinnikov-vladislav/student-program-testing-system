package rsoi.lab5.seservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rsoi.lab5.seservice.entity.user.Role;
import rsoi.lab5.seservice.entity.Status;
import rsoi.lab5.seservice.entity.user.User;
import rsoi.lab5.seservice.exception.HttpNotFoundException;
import rsoi.lab5.seservice.model.user.UserModel;
import rsoi.lab5.seservice.repository.RoleRepository;
import rsoi.lab5.seservice.repository.UserRepository;
import rsoi.lab5.seservice.service.UserService;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        logger.info("findAll() method called: ");
        Page<UserModel> result = userRepository.findAllUsers(pageable);
        logger.info("FIND ALL Users: {}", result.getContent());
        return result;
    }

    @Override
    public UserModel findById(UUID id) {
        logger.info("findById() method called: ");
        UserModel result = userRepository.findByIdUser(id)
                .orElseThrow(() -> new HttpNotFoundException(String.format("User could not be found with id: %s", id)));
        logger.info("FIND BY ID User: {} - found by id: {}", result, id);
        return result;
    }

    @Override
    public User findByUsername(String userName) {
        logger.info("findByUsername() method called: ");
        User result = userRepository.findByUsername(userName)
                .orElseThrow(() -> new HttpNotFoundException(String.format("User could not be found with username: %s", userName)));
        logger.info("FIND BY USERNAME User: {} - found by username: {}", result, userName);
        return result;
    }

    @Override
    public User findByEmail(String email) {
        logger.info("findByEmail() method called: ");
        User result = userRepository.findByEmail(email)
                .orElseThrow(() -> new HttpNotFoundException(String.format("User could not be found with email: %s", email)));
        logger.info("FIND BY EMAIL User: {} - found by email: {}", result, email);
        return result;
    }

    @Override
    @Transactional
    public User create(User user) {
        logger.info("create() method called: ");

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        // TODO: Шифрование временно в связи с тем, что пароль придется шифровать на клиенте, передача по сети небезопасна
        // TODO: Убрать следующую строку в будущем
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        checkUserData(user);

        User result = userRepository.saveAndFlush(user);

        logger.info("CREATE User: {} - successfully", result);
        return result;
    }

    @Override
    public User update(UUID id, User user) {
        logger.info("update() method called: ");
        user.setId(id);
        checkUserData(user);
        User result = userRepository.saveAndFlush(user);
        logger.info("UPDATE User: {} - successfully", result);
        return result;
    }

    private void checkUserData(User user) {
        User checkLogin = userRepository.findByUsername(user.getUsername()).orElse(null);
        User checkEmail = userRepository.findByEmail(user.getEmail()).orElse(null);

        if (checkLogin != null && checkEmail != null)
            throw new ConstraintViolationException("(username, email)=("+user.getUsername()+", "+user.getEmail()+")", null);
        else if (checkLogin != null)
            throw new ConstraintViolationException("(username)=("+user.getUsername()+")", null);
        else if (checkEmail != null)
            throw new ConstraintViolationException("(email)=("+user.getEmail()+")", null);
    }

    @Override
    public void delete(UUID id) {
        logger.info("delete() method called.");
        userRepository.deleteById(id);
    }
}
