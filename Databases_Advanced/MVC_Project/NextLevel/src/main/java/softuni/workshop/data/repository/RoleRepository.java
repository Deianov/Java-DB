package softuni.workshop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entity.Role;
import softuni.workshop.service.model.RoleServiceModel;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByAuthority(String role);

    @Query("SELECT new softuni.workshop.service.model.RoleServiceModel(r.id, r.authority) from Role r")
    Collection<RoleServiceModel> findAllRoles();
}
