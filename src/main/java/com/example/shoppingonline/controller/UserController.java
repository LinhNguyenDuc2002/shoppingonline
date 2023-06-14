package com.example.shoppingonline.controller;

import com.example.shoppingonline.model.*;
import com.example.shoppingonline.repository.AddressRepository;
import com.example.shoppingonline.repository.ProductRepository;
import com.example.shoppingonline.repository.RoleRepository;
import com.example.shoppingonline.repository.UserRepository;
import com.example.shoppingonline.service.BillService;
import com.example.shoppingonline.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
//@RestController
@RequestMapping(path="/blueshop") //http://localhost:8080/blueshop
public class UserController {
    @Autowired //create only one time and be used throughout app -> dependency injection
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired //create only one time and be used throughout app -> dependency injection
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BillService billService;

    @GetMapping("")
    public String home(Model model) throws ParseException {
        model.addAttribute("products",productRepository.findAll());
        try{
            Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", ((UserDetailsImpl)u).getFullname());
        }
        catch (Exception e){
            model.addAttribute("username", null);
            User user = new User();
            DateFormat f = new SimpleDateFormat("MM-dd-yyyy");
            user.setDob(f.parse("01-01-2023"));
            model.addAttribute("user",user);
            model.addAttribute("error","");
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) throws ParseException {
        model.addAttribute("username", null);
        User user = new User();
        DateFormat f = new SimpleDateFormat("MM-dd-yyyy");
        user.setDob(f.parse("01-01-2023"));
        model.addAttribute("user",user);
        model.addAttribute("error","");
        return "login";
    }
    //ADMIN
    @GetMapping("/info")
    public String info(Model model){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "info";
    }

    @PostMapping("/insert")
    public String addUser(Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "index";
        }
        try{
            if(userRepository.findByUsername(user.getUsername())==null && !user.getUsername().equals("anonymousUser")){
                addressRepository.save(user.getAddress());
                user.addRole(roleRepository.findById(1));
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);
                return "redirect:/blueshop";
            }
            return "index";
        }catch (Exception e){
            return "index";
        }
    }

    @GetMapping("/infor")
    public String information(Model model){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        User user = userRepository.findByUsername(((UserDetailsImpl)u).getUsername());
        model.addAttribute("user",user);
        return "information";
    }

    @PostMapping("/update")
    public String updatePassword(Model model,@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        User userCurrent = userRepository.findByUsername(((UserDetailsImpl)u).getUsername());
//        if(bindingResult.hasErrors()){ //có lỗi hay không
//            model.addAttribute("user",userCurrent);
//            return "information";
//        }
        try{
            userService.updateUser(user, userCurrent);
        }catch (Exception e){
            model.addAttribute("error",e.toString());
        }
        return "redirect:/blueshop/infor";
    }

    @GetMapping("/delete")
    public String delete(Model model){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        User userCurrent = userRepository.findByUsername(((UserDetailsImpl)u).getUsername());
        userRepository.delete(userCurrent);
        addressRepository.deleteById(userCurrent.getAddress().getIdaddress());
        return "redirect:/blueshop/logout";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        Optional<User> userCurrent = userRepository.findById(id);
        if(userCurrent.isPresent()){
            userRepository.deleteById(id);
            addressRepository.deleteById(userCurrent.get().getAddress().getIdaddress());
        }
        return "redirect:/blueshop";
    }

    @GetMapping("/logout")
    public String logout (){
        SecurityContextHolder.clearContext();
        // Xóa phiên làm việc
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/blueshop";
    }

}
