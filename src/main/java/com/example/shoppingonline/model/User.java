package com.example.shoppingonline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor //tự động sinh constructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {
    @Id
    @SequenceGenerator(
            name = "User sequence",
            sequenceName = "User sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "User sequence"
    )
    private int iduser;

    @NotNull
    @NotBlank(message="Username can not be null") // Bắt buộc nhập
    private String username;

    @Size(min=4, max=100)
    private String password;

    @NotNull
    @NotBlank(message="Fullname can not be null")
    private String fullname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String email;

    private String phone;

    private String shop;

    private String note;

    @OneToOne // Đánh dấu có mỗi quan hệ 1-1 với Address ở phía dưới
    @JoinColumn(name = "idaddress")
    private Address address;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //CascadeType.ALL: tất cả các hoạt động (insert, update, delete) trên một thực thể sẽ được lan truyền (cascade) sang các thực thể liên quan trong mqh

    //FetchType.LAZY: dữ liệu sẽ được lấy từ cơ sở dữ liệu khi nó được yêu cầu một cách rõ ràng, thường thông qua việc gọi các phương thức truy vấn hoặc
    // truy cập trực tiếp vào thuộc tính. Điều này giúp tối ưu hóa hiệu suất của ứng dụng bằng cách trì hoãn việc tải dữ liệu không cần thiết.
//    @EqualsAndHashCode.Exclude //loại bỏ khỏi phương thức equals và hashCode khỏi trường ở dưới => gây lặp vô hạn
//    @ToString.Exclude //loại bỏ khỏi phương thức tostring khỏi trường ở dưới => gây lặp vô hạn
//    @JoinTable(name = "bill",
//            joinColumns = @JoinColumn(name = "iduser"),  //khóa ngoại là iduser trỏ tới class hiện tại (User)
//            inverseJoinColumns = @JoinColumn(name = "idproduct") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Product)
//    )
//    private Collection<Product> products;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude
    @JsonBackReference
//    @JsonIgnore
//    @JsonManagedReference
    private Collection<Bill> bills;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude
    @JsonBackReference
//    @JsonIgnore
//    @JsonManagedReference
    private Collection<Product> products;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "iduser"),
            inverseJoinColumns = @JoinColumn(name = "idrole")
    )
    private Collection<Role> roles;
    public void addProduct(Product product){
        this.products.add(product);
    }

    public void addRole(Role role){
        if (roles == null) {
            roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    public String getStringAddress(){
        String ad = "";
        if(this.address.getSonha()!=null){
            ad += "Số nhà "+this.address.getSonha()+" - ";
        }
        if(this.address.getXompho()!=null){
            ad += "Xóm/phố "+this.address.getXompho()+" - ";
        }
        if(this.address.getLang()!=null){
            ad += "Thôn/làng "+this.address.getLang()+" - ";
        }
        if(this.address.getPhuongxa()!=null){
            ad += "Phường/xã "+this.address.getPhuongxa()+" - ";
        }
        if(this.address.getQuanhuyen()!=null){
            ad += "Quận/huyện "+this.address.getQuanhuyen()+" - ";
        }
        if(this.address.getTinhthanh()!=null){
            ad += "Tỉnh/TP "+this.address.getTinhthanh();
        }
        return ad;
    }
}
