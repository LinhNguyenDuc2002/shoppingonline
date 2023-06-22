package com.example.shoppingonline.service;

import com.example.shoppingonline.DAO.UserDAO;
import com.example.shoppingonline.model.Address;
import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.model.User;
import com.example.shoppingonline.repository.AddressRepository;
import com.example.shoppingonline.repository.ShopRepository;
import com.example.shoppingonline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    public String getUserByIduser(int id) {
        return userDAO.findUserByIduser(id);
    }

    public void updateUser(User user, User founduser){
        if(user.getUsername().length()>0){
            founduser.setUsername(user.getUsername());
        }
        if(user.getPassword().length()>=4){
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            founduser.setPassword(encodedPassword);
        }
        if(user.getFullname().length()>0){
            founduser.setFullname(user.getFullname());
        }
        if(user.getDob()!=null){
            founduser.setDob(user.getDob());
        }
        if(user.getEmail()!=null){
            founduser.setEmail(user.getEmail());
        }
        if(user.getPhone()!=null){
            founduser.setPhone(user.getPhone());
        }
        if(founduser.getShop()==null && user.getShop()!=null){
            try{
                founduser.setShop(user.getShop());
            }
            catch (Exception e){
            }
        }
        else{
            if(user.getShop()!=null){
                shopService.updateShop(founduser.getShop(),user.getShop());
            }
        }

        if(founduser.getAddress()==null && user.getAddress()!=null){
            try{
                founduser.setAddress(user.getAddress());
            }
            catch (Exception e){
            }
        }
        else{
            if(user.getAddress()!=null){
                addressService.updateAddress(founduser.getAddress(),user.getAddress());
            }
        }
        userRepository.save(founduser);
    }
}
