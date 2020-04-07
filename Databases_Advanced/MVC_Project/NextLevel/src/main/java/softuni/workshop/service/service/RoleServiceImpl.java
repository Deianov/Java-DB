package softuni.workshop.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.constant.Constants;
import softuni.workshop.data.entity.Role;
import softuni.workshop.data.repository.RoleRepository;
import softuni.workshop.service.model.RoleServiceModel;

import java.util.Collection;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void seedRolesInDb() {
        Role user = new Role(Constants.ROLE_USER);
        Role admin = new Role(Constants.ROLE_ADMINISTRATOR);

        repository.saveAndFlush(admin);
        repository.saveAndFlush(user);
    }
}
