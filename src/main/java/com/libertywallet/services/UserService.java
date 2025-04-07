package com.libertywallet.services;


import com.libertywallet.dto.JwtAuthDto;
import com.libertywallet.dto.RefreshTokenDto;
import com.libertywallet.dto.UserCredentialDto;
import com.libertywallet.dto.UserDto;
import com.libertywallet.exception.AlreadyExistException;
import com.libertywallet.exception.AuthenticationException;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.mapper.UserMapper;
import com.libertywallet.models.User;
import com.libertywallet.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.libertywallet.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;



    public JwtAuthDto signIn(UserCredentialDto userCredentialDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialDto);
        return jwtService.generateToken(user.getEmail());
    }

    public JwtAuthDto refreshToken(RefreshTokenDto refreshTokenDto){
        String refreshToken = refreshTokenDto.getRefreshToken();
        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)){
            User  user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(),refreshToken);
        }
        throw new AuthenticationException("Invalid auth token");
    }

    @Transactional
    public UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Transactional
    public UserDto getUserById(Long userId) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findById(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }


    public String createUser(UserDto userDto){
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User added";
    }

    private User findByCredentials(UserCredentialDto userCredentialDto) throws AuthenticationException{
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialDto.getEmail());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(passwordEncoder.matches(userCredentialDto.getPassword(),user.getPassword())){
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    private User findByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("User with email not found :"+email));
    }


}
