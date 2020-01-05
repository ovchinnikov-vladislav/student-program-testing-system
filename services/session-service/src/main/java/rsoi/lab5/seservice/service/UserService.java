package rsoi.lab5.seservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab5.seservice.entity.user.User;
import rsoi.lab5.seservice.model.user.UserModel;

import java.util.UUID;

public interface UserService {

    Page<UserModel> findAll(Pageable pageable);
    UserModel findById(UUID id);
    User findByUsername(String userName);
    User findByEmail(String email);
    User create(User user);
    User update(UUID id, User user);
    void delete(UUID id);

}
