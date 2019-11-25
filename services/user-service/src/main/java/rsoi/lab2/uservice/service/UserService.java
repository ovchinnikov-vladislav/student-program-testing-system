package rsoi.lab2.uservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.uservice.entity.User;
import rsoi.lab2.uservice.model.PageCustom;
import rsoi.lab2.uservice.model.SomeUsersModel;

import java.util.List;

public interface UserService {
    Page<SomeUsersModel> findAll(Pageable pageable);
    User findById(Long id);
    User findByUserName(String userName);
    User findByEmail(String email);
    User create(User user);
    User update(User user);
    void delete(Long id);
}
