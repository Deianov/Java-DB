package softuni.workshop.service.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.workshop.constant.ValidationConstants;
import softuni.workshop.data.entity.User;
import softuni.workshop.data.repository.RoleRepository;
import softuni.workshop.data.repository.UserRepository;
import softuni.workshop.exception.InvalidEntityException;
import softuni.workshop.service.model.UserServiceModel;
import softuni.workshop.util.ValidationUtil;
import softuni.workshop.web.model.UserRegisterModel;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static softuni.workshop.constant.Constants.*;
import static softuni.workshop.constant.ValidationConstants.*;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository, ModelMapper mapper, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, ValidationUtil validationUtil) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.validationUtil = validationUtil;
    }

    @Override
    public UserServiceModel registerUser(UserRegisterModel userRegisterModel) {

        if(!validationUtil.isValid(userRegisterModel)){
            throw new InvalidEntityException(validationUtil.getViolationsString(userRegisterModel));
        }

        if(!userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword())){
            throw new InvalidEntityException(PASSWORD_DONT_MATCH);
        }

        if(repository.existsUserByUsername(userRegisterModel.getUsername())){
            throw new InvalidEntityException(USER_EXISTS);
        }

        if(repository.existsUserByEmail(userRegisterModel.getEmail())){
            throw new InvalidEntityException(EMAIL_EXISTS);
        }

        User user = mapper.map(userRegisterModel, User.class);

        if(this.repository.count() == 0){

            // create roles
            roleService.seedRolesInDb();

            // create administrator
            user.setAuthorities(roleRepository.findAll());

        } else {

            // create user
            user.setAuthorities(new ArrayList<>());
            roleRepository.findByAuthority(ROLE_USER).ifPresent(user.getAuthorities()::add);
        }

        // encrypt password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // save user and return model
        return mapper.map(repository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails = repository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        return userDetails;
    }
}
