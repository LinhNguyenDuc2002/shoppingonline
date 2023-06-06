package com.example.shoppingonline.service;

import com.example.shoppingonline.DAO.UserDAO;
import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.model.User;
import com.example.shoppingonline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getUserByIduser(int id) {
        return userDAO.findUserByIduser(id);
    }

    public void updateUser(User user, User founduser){
        if(user.getUsername()!=null){
            founduser.setUsername(user.getUsername());
        }
        if(user.getPassword()!=null && user.getPassword().length()>=4){
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            founduser.setPassword(encodedPassword);
        }
        if(user.getFullname()!=null){
            founduser.setFullname(user.getFullname());
        }
        if(user.getDob()!=null){
            founduser.setDob(user.getDob());
        }
        if(user.getEmail()!=null){
            founduser.setEmail(user.getEmail());
        }
        if(user.getPhone()!=null){
            founduser.setPhone(user.getPhone());
        }
        userRepository.save(founduser);
    }

}
