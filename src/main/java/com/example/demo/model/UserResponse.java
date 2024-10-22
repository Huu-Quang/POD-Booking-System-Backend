package com.example.demo.model;

import com.example.demo.entity.User;
import lombok.Data;

import java.util.List;
@Data
public class UserResponse {
    List<User> content;
    int pageNumber;
    int totalElements;
    int totalPages;


}
