package org.example.case_modul4.repository;

import org.example.case_modul4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
