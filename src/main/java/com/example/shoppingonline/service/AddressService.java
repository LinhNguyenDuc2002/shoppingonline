package com.example.shoppingonline.service;

import com.example.shoppingonline.model.Address;
import com.example.shoppingonline.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    public void updateAddress(Address old, Address latest){
        old.setTinhthanh(latest.getTinhthanh());
        old.setPhuongxa(latest.getPhuongxa());
        old.setQuanhuyen(latest.getQuanhuyen());
        old.setLang(latest.getLang());
        old.setXompho(latest.getXompho());
        old.setSonha(latest.getSonha());
    }
}
