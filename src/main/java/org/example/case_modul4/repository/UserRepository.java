package org.example.case_modul4.repository;

import org.example.case_modul4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
