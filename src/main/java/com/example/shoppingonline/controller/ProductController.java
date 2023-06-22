package com.example.shoppingonline.controller;

import com.example.shoppingonline.model.Product;
import com.example.shoppingonline.model.Shop;
import com.example.shoppingonline.model.User;
import com.example.shoppingonline.model.UserDetailsImpl;
import com.example.shoppingonline.repository.ProductRepository;
import com.example.shoppingonline.repository.ShopRepository;
import com.example.shoppingonline.repository.UserRepository;
import com.example.shoppingonline.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

//@Controller
@Controller
@RequestMapping(path="/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;
    @GetMapping("")
    public String getProducts(Model model){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        User user = userRepository.findByUsername(((UserDetailsImpl)u).getUsername());
        try{
            List<Product> products = productService.getUserProduct(user.getShop().getIdshop());
            model.addAttribute("products",products);
        }
        catch (Exception e){
            model.addAttribute("products",null);
        }
        return "product";
    }

    @GetMapping("/shop/{id}")
    public String getUserProducts(Model model, @PathVariable int id){
        try{
            Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", ((UserDetailsImpl)u).getFullname());
        }
        catch (Exception e){
            model.addAttribute("username", null);
        }

        Optional<Shop> shop = shopRepository.findById(id);
        if(shop.isPresent()){
            List<Product> products = productService.getUserProduct(id);
            model.addAttribute("shop",shop.get());
            model.addAttribute("products",products);
        }
        return "productshop";
    }

    @GetMapping("/{id}")
    public String getProduct(Model model, @PathVariable int id){
        try{
            Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", ((UserDetailsImpl)u).getFullname());
        }
        catch (Exception e){
            model.addAttribute("username", null);
        }

        model.addAttribute("product",productRepository.findById(id).get());
        return "product-detail";
    }

    @GetMapping("/image/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int productId) throws IOException {
        // Lấy đối tượng Product từ cơ sở dữ liệu dựa trên productId
        Product product = productRepository.findById(productId).get();

        // Lấy mảng byte[] của ảnh từ đối tượng Product
        byte[] imageBytes = product.getImage();

        // Tạo đối tượng ResponseEntity<byte[]> để trả về ảnh và các thông tin liên quan
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Đặt kiểu dữ liệu của ảnh
        headers.setContentLength(imageBytes.length); // Đặt kích thước của ảnh

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/addproduct")
    public String addProduct(Model model){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        User user = userRepository.findByUsername(((UserDetailsImpl)u).getUsername());
        if(user.getShop()==null){
            model.addAttribute("user",user);
            model.addAttribute("errorShop","Please create a name for your shop to start selling");
            return "information";
        }
        Product product = new Product();
        model.addAttribute("product",product);
        return "addproduct";
    }

    @PostMapping("/insert")
    public String insertProduct(Model model,@Valid @ModelAttribute("product") Product product, @RequestParam("imagefile") MultipartFile imagefile,
                                BindingResult bindingResult) throws IOException {
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        productService.createProduct(product,userRepository.findByUsername(((UserDetailsImpl)u).getUsername()), imagefile);
        return "redirect:/product";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable int id){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        Optional<Product> product = productRepository.findById(id);
        model.addAttribute("product",product.get());
        return "infoproduct";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(Model model,@Valid @ModelAttribute("product") Product product, @RequestParam("imagefile") MultipartFile imagefile,
                                BindingResult bindingResult, @PathVariable int id){
        Object u = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", ((UserDetailsImpl)u).getFullname());

        if(bindingResult.hasErrors()){ //có lỗi hay không
            return "infoproduct";
        }
        try{
            productService.updateProduct(product,id,imagefile);
        }catch (Exception e){
            model.addAttribute("error",e.toString());
        }
        return "redirect:/product";
    }

}
