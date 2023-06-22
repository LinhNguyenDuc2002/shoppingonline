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

            User userCurrent = userRepository.findByUsername(((UserDetailsImpl)u).getUsername());
            Address a = addressRepository.findById(2).get();
            userCurrent.setAddress(a);
        }
        catch (Exception e){
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) throws ParseException {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) throws ParseException {
        model.addAttribute("username", null);
        User user = new User();
        DateFormat f = new SimpleDateFormat("MM-dd-yyyy");
        user.setDob(f.parse("01-01-2023"));
        model.addAttribute("user",user);
        return "signup";
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
            return "signup";
        }
        try{
            if(userRepository.findByUsername(user.getUsername())==null && !user.getUsername().equals("anonymousUser")){
                user.addRole(roleRepository.findById(1));
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);
                return "redirect:/blueshop";
            }
            else{
                model.addAttribute("error","The username already exists");
                return "signup";
            }
        }catch (Exception e){
            return "signup";
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
    public String updatePassword(Model model,@ModelAttribute("user") User user){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        User userCurrent = userRepository.findByUsername(((UserDetailsImpl)u).getUsername());
        boolean check = false;
        if(user.getPassword().length()>0 && user.getPassword().length()<4){
            model.addAttribute("errorPassword","Size must be between 4 and 100");
            check = true;
        }
        if(user.getUsername().length()==0){
            model.addAttribute("errorUsername","Username couldn't be null");
            check = true;
        }
        else if ((userRepository.findByUsername(user.getUsername())!=null && !user.getUsername().equals(userCurrent.getUsername())) || user.getUsername().equals("anonymousUser")){
            model.addAttribute("errorUsername","The username already exists");
            check = true;
        }
        if(user.getFullname().length()==0){
            model.addAttribute("errorFullname","Fullname couldn't be null");
            check = true;
        }
        if(check){
            return "information";
        }
        else{
            try{
                userService.updateUser(user, userCurrent);
            }catch (Exception e){
            }
            return "redirect:/blueshop/infor";
        }
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
