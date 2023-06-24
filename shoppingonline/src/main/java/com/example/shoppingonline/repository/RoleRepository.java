package com.example.shoppingonline.repository;

import com.example.shoppingonline.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findById(int id);
}
