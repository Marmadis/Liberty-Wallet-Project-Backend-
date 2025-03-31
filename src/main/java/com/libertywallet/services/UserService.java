package com.libertywallet.services;


import com.libertywallet.exception.AlreadyExistException;
import com.libertywallet.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.libertywallet.repositories.UserRepository;

import java.rmi.AlreadyBoundException;
import java.util.Optional;
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User userRegister(String email,String password,String username){
        if(userRepository.findByEmail(email).isPresent()){
            log.error("User already exist: {}",email);
            throw new AlreadyExistException("User already exist:"+email);
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }


}
