package org.example.case_modul4.service;

import org.example.case_modul4.model.Order;
import org.example.case_modul4.model.User;
import org.example.case_modul4.repository.OrderRepository;
import org.example.case_modul4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {

            User user = userRepository.findById((int) order.getUserId()).orElse(null);
            if (user != null) {
                order.setUserName(user.getName());
            }
        }
        return orders;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }


    public Order findById(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
    public Order getOrderById(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElse(null);
    }
}
