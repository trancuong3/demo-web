package org.example.case_modul4.controller;

import org.example.case_modul4.model.User;
import org.example.case_modul4.repository.UserRepository;
import org.example.case_modul4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService; // Tiêm UserService

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("gender") String gender,
            @RequestParam("phone") String phone) {

        ModelAndView modelAndView = new ModelAndView("auth/register");

        // Kiểm tra mật khẩu xác nhận
        if (!password.equals(confirmPassword)) {
            modelAndView.addObject("error", "Mật khẩu xác nhận không khớp");
            return modelAndView;
        }

        // Kiểm tra email hoặc tên người dùng đã tồn tại
        if (userRepository.findUserByEmail(email).isPresent() || userRepository.findUserByUsername(username).isPresent()) {
            modelAndView.addObject("error", "Email hoặc tên người dùng đã tồn tại");
            return modelAndView;
        }

        // Tạo người dùng mới
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // Đặt mật khẩu chưa mã hóa ở đây
        user.setGender(gender);
        user.setPhone(phone);

        // Lưu người dùng vào cơ sở dữ liệu thông qua dịch vụ
        userService.registerUser(user);

        // Điều hướng về trang đăng nhập sau khi đăng ký thành công
        modelAndView.setViewName("redirect:/auth/login");
        return modelAndView;
    }
    @GetMapping("/Users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers(); // Lấy danh sách người dùng từ service
        model.addAttribute("users", users); // Thêm danh sách người dùng vào model
        return "Admin/users/userList"; // Tên của template HTML
    }

}
