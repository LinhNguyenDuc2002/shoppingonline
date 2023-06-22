package com.example.shoppingonline.service;

import com.example.shoppingonline.DAO.ProductDAO;
import com.example.shoppingonline.model.Bill;
import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.model.User;
import com.example.shoppingonline.repository.ProductRepository;
import com.example.shoppingonline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public void createProduct(Product product, User user, MultipartFile imagefile) throws IOException {
        byte[] image = imagefile.getBytes();
        product.setImage(image);
        product.setShop(user.getShop());
        user.getShop().addProduct(product);
        userRepository.saveAndFlush(user);
    }

    public List<Product> getUserProduct(int id){
        return productDAO.getUserProducts(id);
    }
    public void updateProduct(Product product,int id,MultipartFile imagefile) throws IOException {
        if(productRepository.findById(id).isPresent()){
            Product foundProduct = productRepository.findById(id).get();
            if(product.getProductname()!=null){
                foundProduct.setProductname(product.getProductname());
            }
            if(product.getGia()>=0){
                foundProduct.setGia(product.getGia());
            }
            if(product.getSolg()>=0){
                foundProduct.setSolg(product.getSolg());
            }
            if(product.getNote()!=null){
                foundProduct.setNote(product.getNote());
            }
            if(!imagefile.isEmpty()){
                byte[] image = imagefile.getBytes();
                foundProduct.setImage(image);
            }
            productRepository.save(foundProduct);
        }
    }
}
