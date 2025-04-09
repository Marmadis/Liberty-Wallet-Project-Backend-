package com.libertywallet.service;


import com.libertywallet.dto.JwtAuthDto;
import com.libertywallet.dto.RefreshTokenDto;
import com.libertywallet.dto.UserCredentialDto;
import com.libertywallet.dto.UserDto;
import com.libertywallet.exception.AuthenticationException;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.mapper.UserMapper;
import com.libertywallet.entity.User;
import com.libertywallet.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.libertywallet.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ImageService imageService;

    public ResponseEntity<byte[]> getImage(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found:"+userId));
        String filepath = user.getAvatar();
        ResponseEntity<byte[]> image = imageService.getImage(filepath);
        log.info("The user has successfully downloaded the image");
        return image;
    }

    public String saveImage(MultipartFile file,Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found:"+userId));

        String filepath = imageService.saveImage(file);
        user.setAvatar(filepath);
        userRepository.save(user);
        log.info("The user has successfully uploaded the image");
        return "The user has successfully uploaded the image";
    }


    public JwtAuthDto signIn(UserCredentialDto userCredentialDto) throws AuthenticationException {
        log.info("Sign in...");
        User user = findByCredentials(userCredentialDto);
        return jwtService.generateToken(user.getEmail());
    }

    public JwtAuthDto refreshToken(RefreshTokenDto refreshTokenDto){
        log.info("Refreshing refreshToken");
        String refreshToken = refreshTokenDto.getRefreshToken();
        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)){
            User  user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            log.info("Refresh was successfully");
            return jwtService.refreshBaseToken(user.getEmail(),refreshToken);
        }
        throw new AuthenticationException("Invalid auth token");
    }

    @Transactional
    public UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        log.info("Getting user by email");
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Transactional
    public UserDto getUserById(Long userId) throws ChangeSetPersister.NotFoundException {
       log.info("Getting user by id");
        return userMapper.toDto(userRepository.findById(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }


    public String createUser(UserDto userDto){
        log.info("Creating user");
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User added";
    }

    private User findByCredentials(UserCredentialDto userCredentialDto) throws AuthenticationException{
        log.info("Finding by credentials");
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
        log.info("Finding by email");
        return userRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("User with email not found :"+email));
    }


}
