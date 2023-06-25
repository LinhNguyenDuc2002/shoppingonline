package com.example.shoppingonline.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor //tự động sinh constructor
@NoArgsConstructor
public class UserDTO {
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

    private String note;
}
