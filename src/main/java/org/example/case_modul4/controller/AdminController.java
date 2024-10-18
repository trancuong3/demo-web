package org.example.case_modul4.controller;

import org.example.case_modul4.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String adminPage() {
        return "Admin/admin";
    }
}
