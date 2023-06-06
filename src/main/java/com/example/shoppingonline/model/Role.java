package com.example.shoppingonline.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;

@Entity
@Data
@Table(name="role")
public class Role {
    @Id
    @SequenceGenerator(
            name = "Role sequence",
            sequenceName = "Role sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Role sequence"
    )
    private int idrole;
    private String namerole;

//    @ManyToMany(mappedBy = "roles")
//    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Collection<User> users;
}
