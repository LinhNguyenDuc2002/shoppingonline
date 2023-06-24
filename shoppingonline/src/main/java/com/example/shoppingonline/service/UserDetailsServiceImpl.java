package com.example.shoppingonline.service;

import com.example.shoppingonline.model.User;
import com.example.shoppingonline.model.UserDetailsImpl;
import com.example.shoppingonline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user);
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getRoles().stream() //lấy ra danh sách role của user
//                        .map(role -> new SimpleGrantedAuthority(role.getNamerole())) //mỗi role được gán kiểu SimpleGrantedAuthority
//                        .collect(Collectors.toList())
//        );
    }
}
