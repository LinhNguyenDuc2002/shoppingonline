package com.example.shoppingonline.repository;

import com.example.shoppingonline.model.Address;
import org.springframework.data.repository.CrudRepository; //thêm bớt sửa xóa

public interface AddressRepository extends CrudRepository<Address, Integer> {

}
