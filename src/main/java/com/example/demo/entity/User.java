package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    long id;

    @JsonIgnore
    boolean isDeleted = false;

    @NotBlank (message = "Name can not be blank!")
    @Column(name = "name")
    String name;


    @Column(name = "Age")
    int age;



    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Phone is not valid!")
    @NotBlank (message = "phone can not be blank!")
    @Column(name = "Phone")
    String phone;


    @NotBlank (message = "email can not be blank!")
    @Email(message = "Email should be valid")
    @Column(name = "Email")
    String email;


}
