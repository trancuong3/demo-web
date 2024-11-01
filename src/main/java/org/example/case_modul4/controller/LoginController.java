package org.example.case_modul4.controller;

import jakarta.servlet.http.HttpSession;
import org.example.case_modul4.model.User;
import org.example.case_modul4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Trả về trang login
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        // Kiểm tra đăng nhập cho admin
        if ("admin".equals(username) && "admin1234".equals(password)) {
            session.setAttribute("loggedIn", true);
            session.setAttribute("role", "ROLE_ADMIN"); // Thiết lập vai trò cho admin
            return "redirect:/admin";
        }

        // Không hiển thị lỗi cụ thể về tên đăng nhập hoặc mật khẩu
        model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không chính xác."); // Thông báo lỗi chung

        return "login"; // Trả về trang login nếu có lỗi
    }




    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa session khi đăng xuất
        return "redirect:/login";
    }
}