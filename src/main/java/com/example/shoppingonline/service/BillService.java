package com.example.shoppingonline.service;

import com.example.shoppingonline.DAO.BillDAO;
import com.example.shoppingonline.DAO.UserDAO;
import com.example.shoppingonline.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillDAO billDAO;

    public List<Product> findProductsByIduser(int id){
        return billDAO.findProductsByIduser(id);
    }
}
