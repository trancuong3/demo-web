package org.example.case_modul4.service;

import org.example.case_modul4.model.Role;
import org.example.case_modul4.model.User;
import org.example.case_modul4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
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
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(Integer id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User không tồn tại với ID: " + id);
        }
    }

}