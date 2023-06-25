package com.example.shoppingonline.service;

import com.example.shoppingonline.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    @Autowired
    private AddressService addressService;
    public void updateShop(Shop old, Shop latest){
        old.setNameshop(latest.getNameshop());
        old.setEmail(latest.getEmail());
        old.setSdt(latest.getSdt());
        if(old.getAddress()==null && latest.getAddress()!=null){
            try{
                old.setAddress(latest.getAddress());
            }
            catch (Exception e){
            }
        }
        else{
            if(old.getAddress()!=null){
                addressService.updateAddress(old.getAddress(),latest.getAddress());
            }
        }
    }
}
