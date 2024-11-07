package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountResponse {
    Long Id;
    String Username;
    String Password;
    String name;
    String Email;
    String Phone;
    String Token;
}
