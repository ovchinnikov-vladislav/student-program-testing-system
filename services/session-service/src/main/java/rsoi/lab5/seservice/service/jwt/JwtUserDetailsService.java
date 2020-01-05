package rsoi.lab5.seservice.service.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rsoi.lab5.seservice.entity.user.User;
import rsoi.lab5.seservice.exception.HttpNotFoundException;
import rsoi.lab5.seservice.model.jwt.JwtUser;
import rsoi.lab5.seservice.model.jwt.JwtUserFactory;
import rsoi.lab5.seservice.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findByUsername(username);
        } catch (HttpNotFoundException notFoundByUsernameExc) {
            try {
                user = userService.findByEmail(username);
            } catch (HttpNotFoundException notFoundByEmailExc) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }
        }

        if (user == null)
            throw new UsernameNotFoundException("User with username: " + username + " not found");

        JwtUser jwtUser = JwtUserFactory.create(user);
        logger.info("LOAD BY USERNAME - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
