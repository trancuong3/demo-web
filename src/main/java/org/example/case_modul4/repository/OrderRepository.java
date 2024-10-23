package org.example.case_modul4.repository;

import org.example.case_modul4.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}