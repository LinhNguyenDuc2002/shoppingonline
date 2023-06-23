package com.example.shoppingonline.DAO;

import com.example.shoppingonline.model.Bill;
import com.example.shoppingonline.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductDAO {
    @Autowired
    private EntityManager entityManager;

    public List<Bill> findProductsById(int iduser) {
        String sql = "SELECT up.bills FROM Product up WHERE up.idproduct = :iduser";
        TypedQuery<Bill> query = entityManager.createQuery(sql,Bill.class);
        query.setParameter("iduser",iduser);
        List<Bill> value = query.getResultList();
        return value;
    }

    public List<Product> getUserProducts(int id){
        String sql = "SELECT p FROM Product p WHERE p.shop.idshop = :idshop";
        TypedQuery<Product> query = entityManager.createQuery(sql,Product.class);
        query.setParameter("idshop",id);
        List<Product> value = query.getResultList();
        return value;
    }

//    public List<Product> getNotChooseProduct(int idshop,int id){
//    }
}
