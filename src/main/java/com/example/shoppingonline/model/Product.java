package com.example.shoppingonline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.DecimalFormat;
import java.util.Collection;

@Entity
@Data
@Table(name="product")
public class Product {
    @Id
    @SequenceGenerator(
            name = "Product sequence",
            sequenceName = "Product sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Product sequence"
    )
    private int idproduct;

    @NotBlank(message="Product name can not be null")
    private String productname;

    private double gia;
    @Lob // kiểu longblob trong mysql: kiểu binary
    private byte[] image;
    private int solg;
    private int daban;
    private String note;

//    @ManyToMany(mappedBy = "products") //trỏ tới tên biến products ở trong User
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Collection<User> users;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude
    @JsonBackReference
//    @JsonIgnore
//    @JsonManagedReference
    private Collection<Bill> bills;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iduser")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private User user;

    public String getStringGia() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.###");
        String price = decimalFormat.format(this.gia);
        return price;
    }

}
