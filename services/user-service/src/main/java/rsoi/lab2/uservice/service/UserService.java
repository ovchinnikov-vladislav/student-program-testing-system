package rsoi.lab2.uservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.uservice.entity.User;
import rsoi.lab2.uservice.model.PageCustom;
import rsoi.lab2.uservice.model.SomeUsersModel;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Page<SomeUsersModel> findAll(Pageable pageable);
    SomeUsersModel findById(UUID id);
    User findByUserName(String userName);
    User findByEmail(String email);
    User create(User user);
    User update(UUID id, User user);
    void delete(UUID id);
}
