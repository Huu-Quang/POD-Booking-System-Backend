package com.example.demo.model;

import lombok.Data;

@Data
public class AccountResponse {
    Long Id;
    String Username;
    String Password;
    String Email;
    String Phone;
    String Token;
}
