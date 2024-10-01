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


    @Column(name = "name")
    String name;


    @Column(name = "Age")
    int age;



    @Column(name = "Phone")
    String phone;



    @Column(name = "Email")
    String email;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

}
