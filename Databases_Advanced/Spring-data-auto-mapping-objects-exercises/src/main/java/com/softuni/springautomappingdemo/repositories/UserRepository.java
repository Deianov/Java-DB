package com.softuni.springautomappingdemo.repositories;

import com.softuni.springautomappingdemo.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserById(Long id);
}
