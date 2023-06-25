package com.example.shoppingonline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@AllArgsConstructor //tự động sinh constructor
@NoArgsConstructor
@Table(name="address")
public class Address {
    @Id
    @SequenceGenerator(
            name = "Address sequence",
            sequenceName = "Address sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Address sequence"
    )
    private int idaddress;
    private String sonha;
    private String xompho;
    private String lang;
    private String phuongxa;
    private String quanhuyen;
    private String tinhthanh;

}
