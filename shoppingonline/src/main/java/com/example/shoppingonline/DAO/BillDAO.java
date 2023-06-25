package com.example.shoppingonline.DAO;

import com.example.shoppingonline.model.Bill;
import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class BillDAO {
    @Autowired
    private EntityManager entityManager;

    public void createBill(User user, Product product){

    }
    public List<Product> findProductsByIduser(int id){
        //hibernate
        String jpql = "SELECT u.product FROM Bill u WHERE u.user.iduser=:id";
        TypedQuery<Product> query = entityManager.createQuery(jpql,Product.class);
        query.setParameter("id",id);
        List<Product> products = query.getResultList();
        return products;
    }
}
