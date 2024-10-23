package org.example.case_modul4.service;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.case_modul4.model.OrderDetail;
import org.example.case_modul4.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    @PersistenceContext
    private EntityManager entityManager;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        String query = "FROM OrderDetail od WHERE od.order.id = :orderId";
        return entityManager.createQuery(query, OrderDetail.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
    public List<OrderDetail> getOrderDetails(int orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }


}
