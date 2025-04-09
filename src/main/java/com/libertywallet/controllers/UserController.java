package com.libertywallet.controllers;

import com.libertywallet.dto.UserDto;
import com.libertywallet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaTypeFactory.getMediaType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public String createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/get/{userId}")
    public UserDto getUserById(@PathVariable Long userId) throws ChangeSetPersister.NotFoundException {
        return userService.getUserById(userId);
    }

    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) throws ChangeSetPersister.NotFoundException {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/avatar/download/{usedId}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long userId){
        return userService.getImage(userId);
    }

    @PostMapping("/avatar/upload/{userId}")
    public ResponseEntity<String> saveAvatar(@PathVariable Long userId, @RequestBody MultipartFile file){
        return ResponseEntity.ok(userService.saveImage(file,userId));
    }
}
