package com.libertywallet.services;


import com.libertywallet.exception.AlreadyExistException;
import com.libertywallet.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.libertywallet.repositories.UserRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



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

}
