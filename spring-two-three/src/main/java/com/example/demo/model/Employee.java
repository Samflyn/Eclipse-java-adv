package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;

    public Employee(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
