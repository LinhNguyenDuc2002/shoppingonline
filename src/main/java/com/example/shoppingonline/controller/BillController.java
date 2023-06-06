package com.example.shoppingonline.controller;

import com.example.shoppingonline.model.Bill;
import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.repository.BillRepository;
import com.example.shoppingonline.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private BillRepository billRepository;

    @GetMapping("")
    public List<Bill> getBills(){
        List<Bill> bills = billRepository.findAll();
        return bills;
    }

    @GetMapping("/{id}")
    public List<Product> cart(Model model, @PathVariable int id){
        return billService.findProductsByIduser(id);
    }
}
