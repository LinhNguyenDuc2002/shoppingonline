package com.example.shoppingonline.controller;

import com.example.shoppingonline.model.Bill;
import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.model.User;
import com.example.shoppingonline.model.UserDetailsImpl;
import com.example.shoppingonline.repository.BillRepository;
import com.example.shoppingonline.repository.ProductRepository;
import com.example.shoppingonline.repository.UserRepository;
import com.example.shoppingonline.service.BillService;
import jakarta.validation.Valid;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public String buy(Model model, @PathVariable int id) throws ParseException {
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());
        User user = userRepository.findByUsername(((UserDetailsImpl)u).getUsername());

        Product p = productRepository.findById(id).get();

        Bill bill = billService.createBill(user,p);
        model.addAttribute("bill", bill);
        return "buy";
    }
//    @GetMapping("/{id}")
//    public List<Product> cart(Model model, @PathVariable int id){
//
//        return billService.findProductsByIduser(id);
//    }

    @PostMapping("/buyProduct")
    public String buyProduct(Model model, @Valid @ModelAttribute("bill") Bill bill, BindingResult bindingResult) throws ParseException {
        if(bindingResult.hasErrors()){
            return "buy";
        }
        billRepository.save(bill);
        return "buy";
    }
}
