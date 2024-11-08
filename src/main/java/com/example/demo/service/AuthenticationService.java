package com.example.demo.service;


import com.example.demo.entity.Account;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.AccountResponse;
import com.example.demo.model.EmailDetail;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import com.example.demo.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.ResetPassword;


import java.security.DigestException;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    EmailService emailService;
    public AccountResponse register(RegisterRequest registerRequest) {
        Account account = modelMapper.map(registerRequest, Account.class);

        try {

           String originPassword = account.getPassword();

            account.setPassword(passwordEncoder.encode(originPassword));
           Account newAccount = accountRepository.save(account);

           // send email ve cho user
             EmailDetail emailDetail = new EmailDetail();
             emailDetail.setReceiver(newAccount);
             emailDetail.setSubject("Welcome to my website");
             emailDetail.setLink("https://www.google.com");
             emailService.sendEmail(emailDetail);
           return modelMapper.map(newAccount, AccountResponse.class);
        } catch(Exception e){
            if ( e.getMessage().contains(account.getUsername())){
                throw new DuplicateEntity("Duplicate Username!");

            } else if (e.getMessage().contains(account.getEmail())){
                throw  new DuplicateEntity("Duplicate email!");
            } else {
                throw  new DuplicateEntity("Duplicate phone!");
            }
        }

    }
    public AccountResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(

                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
            // => account is already exist
            Account account = (Account) authentication.getPrincipal();
            AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
            accountResponse.setToken(tokenService.generateToken(account));
            return accountResponse;
        }catch (Exception e){
            e.printStackTrace();
            throw new EntityNotFoundException("Username or password invalid");
        }

    }
    public List<Account> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }



    public Account getAccountById(long Id) {
        return accountRepository.findById(Id).orElse(null);
    }

    public boolean changePassword(long Id, String oldPassword, String newPassword) {
        Account account = getAccountById(Id);
        if (account != null && account.getPassword().equals(oldPassword)) {
            account.setPassword(newPassword);
            return true;
        } else {
            return false;
        }
    }

    public void forgotPassword(String email) {
        Account account = accountRepository.findAccountByEmail(email);
        if(account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        String token = tokenService.generateToken(account);
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setReceiver(account);//set receiver
        emailDetail.setSubject("Reset password");
        emailDetail.setLink("https://www.google.com/?token=" + tokenService.generateToken(account));
        emailService.sendEmail(emailDetail);

    }

    public Account resetPassword(ResetPassword resetPassword) {
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        try{
            accountRepository.save(account);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return account;
    }

    public Account login(){
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findAccountByUsername(username);

    }
    public Account getCurrentAccount(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return account;
    }
}


