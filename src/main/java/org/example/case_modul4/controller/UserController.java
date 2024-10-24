package org.example.case_modul4.controller;

import org.example.case_modul4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Phương thức xử lý xóa người dùng
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id); // Xóa người dùng với ID được truyền vào
            redirectAttributes.addFlashAttribute("message", "Xóa người dùng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xóa người dùng thất bại. Người dùng này có thể không tồn tại.");
        }
        return "redirect:/users"; // Điều hướng về danh sách người dùng sau khi xóa
    }
}