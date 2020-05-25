package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotNull(message = "Name cannot be empty")
    @Size(min = 3,message = "Name cannot be less than 2")
    public String name;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8,max = 16,message = "Password should be between 8 to 16 chars")
    public String password;
    @NotNull(message = "Email cannot be empty")
    @Email()
    public String email;
}
