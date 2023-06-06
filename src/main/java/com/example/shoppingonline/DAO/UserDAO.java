package com.example.shoppingonline.DAO;

import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class UserDAO {
    @Autowired
    private EntityManager entityManager;
//    private SessionFactory sessionFactory;

    public String findUserByIduser(int iduser) {
        String sql = "SELECT up.username FROM User up WHERE up.iduser = :iduser";
//        String sql = "Select max(o.orderNum) from " + Order.class.getName() + " o ";
        TypedQuery<String> query = entityManager.createQuery(sql,String.class);
        query.setParameter("iduser",iduser);
        String value = query.getSingleResult();
//        Session session = this.sessionFactory.getCurrentSession();
//        String query = String.valueOf(session.createQuery(sql, Integer.class));
//        String value = query.toString();
        return value;
    }
}
