package com.example.shoppingonline.service;

import com.example.shoppingonline.DAO.BillDAO;
import com.example.shoppingonline.DAO.UserDAO;
import com.example.shoppingonline.model.Bill;
import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.model.User;
import com.example.shoppingonline.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillDAO billDAO;

    @Autowired
    private BillRepository billRepository;

    public Bill createBill(User user, Product product) throws ParseException {
        Bill bill = new Bill();
        DateFormat f = new SimpleDateFormat("MM-dd-yyyy");
        bill.setNgaymua(f.parse("01-01-2023"));
        bill.setProduct(product);
        bill.setUser(user);
        bill.setAddress(user.getAddress());
        return bill;
    }

    public List<Product> findProductsByIduser(int id){
        return billDAO.findProductsByIduser(id);
    }
}
