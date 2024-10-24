package org.example.case_modul4.service;

import org.example.case_modul4.model.User;
import org.example.case_modul4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public void registerUser(User user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(); // Lấy danh sách tất cả người dùng
    }
    public void deleteUserById(Integer id) {
        // Kiểm tra xem người dùng có tồn tại hay không
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id); // Xóa người dùng theo ID
        } else {
            throw new IllegalArgumentException("Người dùng không tồn tại với ID: " + id);
        }
    }
}