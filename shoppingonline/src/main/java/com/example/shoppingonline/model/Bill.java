package com.example.shoppingonline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
@Entity
@Data
@Table(name = "bill")
public class Bill {
    @Id
    @SequenceGenerator(
            name = "Bill sequence",
            sequenceName = "Bill sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Bill sequence"
    )
    private int idbill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iduser")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idproduct")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private Product product;

    private int solg;
    private double thanhtien;
    private Date ngaymua;

    @OneToOne
    @JoinColumn(name="idaddress")
    private Address address;
}
