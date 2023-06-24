package com.example.shoppingonline.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //tự động sinh constructor
@NoArgsConstructor
public class AdressDTO {
    private String sonha;
    private String xompho;
    private String lang;
    private String phuongxa;
    private String quanhuyen;
    private String tinhthanh;
}
