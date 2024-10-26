package org.example.case_modul4.service;

import org.example.case_modul4.model.Role;
import org.example.case_modul4.model.User;
import org.example.case_modul4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
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
    public void registerAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Tạo vai trò ADMIN và USER
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        // Gán vai trò cho người dùng
        user.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
        userRepository.save(user);
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Mã hóa mật khẩu
        user.setRoles(new HashSet<>(Arrays.asList(new Role("ROLE_USER")))); // Gán vai trò USER
        userRepository.save(user);
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