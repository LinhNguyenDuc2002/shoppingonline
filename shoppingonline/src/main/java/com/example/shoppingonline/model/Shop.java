package com.example.shoppingonline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor //tự động sinh constructor
@NoArgsConstructor
@Entity
@Table(name="shop")
public class Shop {
    @Id
    @SequenceGenerator(
            name = "Shop sequence",
            sequenceName = "Shop sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Shop sequence"
    )
    private int idshop;

    private String nameshop;
    private String email;
    private String sdt;

    @OneToOne(cascade = CascadeType.ALL) // Đánh dấu có mỗi quan hệ 1-1 với Address ở phía dưới
    @JoinColumn(name = "idaddress")
    private Address address;

    @OneToMany(mappedBy = "shop",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude
    @JsonBackReference
//    @JsonIgnore
//    @JsonManagedReference
    private Collection<Product> products;

    public void addProduct(Product product){
        this.products.add(product);
    }

    public String getStringAddress(){
        String ad = "";
        if(this.address.getSonha().length()>0){
            ad += "Số nhà "+this.address.getSonha()+" - ";
        }
        if(this.address.getXompho().length()>0){
            ad += "Xóm/phố "+this.address.getXompho()+" - ";
        }
        if(this.address.getLang().length()>0){
            ad += "Thôn/làng "+this.address.getLang()+" - ";
        }
        if(this.address.getPhuongxa().length()>0){
            ad += "Phường/xã "+this.address.getPhuongxa()+" - ";
        }
        if(this.address.getQuanhuyen().length()>0){
            ad += "Quận/huyện "+this.address.getQuanhuyen()+" - ";
        }
        if(this.address.getTinhthanh().length()>0){
            ad += "Tỉnh/TP "+this.address.getTinhthanh();
        }
        return ad;
    }

}
