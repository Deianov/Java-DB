package softuni.workshop.service.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.workshop.service.model.UserServiceModel;
import softuni.workshop.web.model.UserRegisterModel;

public interface UserService extends UserDetailsService {

    // void?
    UserServiceModel registerUser(UserRegisterModel userRegisterModel);
}
