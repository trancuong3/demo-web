package org.example.case_modul4.controller;

import org.example.case_modul4.model.Book;
import org.example.case_modul4.model.Order;
import org.example.case_modul4.model.User;
import org.example.case_modul4.service.BookService;
import org.example.case_modul4.service.OrderService;
import org.example.case_modul4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        List<User> users = userService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("users", users);
        return "Admin/order/orders";
    }
    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrder(@PathVariable("id") int id, Model model) {
        Order order = orderService.findById(id);
        List<User> users = userService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("users", users);

        return "Admin/order/order-edit";
    }


    @PostMapping("/edit")
    public String updateOrder(@ModelAttribute Order order) {
        orderService.updateOrder(order);
        return "redirect:/orders";
    }


    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }



}
