package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

//CRUD
    //Create
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;
    public  User create(UserRequest userRequest){
        try {
            User user = modelMapper.map(userRequest, User.class);
            // xac dinh account tao user
            // luu lai account cua user hien tai
            Account accountRequest = authenticationService.getCurrentAccount();
            user.setAccount(accountRequest);
            User newUser = userRepository.save(user);
            return newUser;
        } catch (Exception e){
            throw new EntityNotFoundException("Duplicate phone");
        }


    }
    public User getUser(long id){
        User user = userRepository.findUserById(id);
        if (user == null)
            throw new EntityNotFoundException("User not found");
        return user;
    }
    //Read
    public UserResponse getAllUser(int page, int size, String sortBy, String sortDir){
        Sort sort = Sort.by(sortBy);
        if (sortDir.equals("asc"))
            sort = sort.ascending();
        else
            sort = sort.descending();

        Page users = userRepository.findAll(PageRequest.of(page, size));
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(users.getContent());
        userResponse.setPageNumber(users.getNumber());
        userResponse.setTotalElements(users.getNumberOfElements());
        userResponse.setTotalPages(users.getTotalPages());

        return userResponse;

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


        User user = userRepository.findUserById(id);
        if (user == null)
            throw new EntityNotFoundException("User not found");
        else {

            userRepository.delete(user);
            return user;
        }

    }


}
