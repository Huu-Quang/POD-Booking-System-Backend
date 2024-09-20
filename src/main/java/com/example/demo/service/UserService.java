package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

//CRUD
    //Create
    @Autowired
    UserRepository userRepository;
    public  User create(User user){
        User newUser = userRepository.save(user);
           return newUser;
    }
    //Read
    public List<User> getAllUser(){
        List<User> users = userRepository.findAll();
        return users;
    }
    //Update
    public User update(long id, User user){
       User oldUser = userRepository.findUserById(id); // tim user theo id
       if (oldUser == null)
           throw new EntityNotFoundException("User not found");

       // cap nhat thong tin cua user

       oldUser.setName(user.getName());
       oldUser.setAge(user.getAge());
       oldUser.setEmail(user.getEmail());
       oldUser.setPhone(user.getPhone());

       // luu xuong database
       return userRepository.save(oldUser);

    }

    //Delete

    public User delete(long id){
        // tim student can duoc update thong qua id
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null)
        throw new EntityNotFoundException("User not found");

        oldUser.setDeleted(true);
        return userRepository.save(oldUser);

    }
}
