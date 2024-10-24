package org.example.case_modul4.repository;

import org.example.case_modul4.model.Order;
import org.example.case_modul4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}