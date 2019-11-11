package rsoi.lab2.uservice.service;

import org.springframework.data.domain.Pageable;
import rsoi.lab2.uservice.entity.User;
import rsoi.lab2.uservice.model.SomeUsersModel;

import java.util.List;

public interface UserService {
    List<SomeUsersModel> getAll();
    List<SomeUsersModel> getAll(Pageable pageable);
    User getById(Long id);
    User getByUserName(String userName);
    User getByEmail(String email);
    User add(User user);
    User update(User user);
    void delete(Long id);
}
