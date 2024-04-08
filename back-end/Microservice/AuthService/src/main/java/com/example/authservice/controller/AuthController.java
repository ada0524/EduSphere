package com.example.authservice.controller;

import com.example.authservice.domain.request.LoginRequest;
import com.example.authservice.domain.request.RegisterRequest;
import com.example.authservice.domain.response.LoginResponse;
import com.example.authservice.domain.response.RegisterResponse;
import com.example.authservice.entity.User;
import com.example.authservice.exception.InvalidCredentialsException;
import com.example.authservice.security.AuthUserDetail;
import com.example.authservice.security.JwtProvider;
import com.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class AuthController {
    
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws Exception {

        Authentication authentication;

        //Try to authenticate the user using the email and password
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e){
            throw new InvalidCredentialsException(e.getMessage());
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);


        //Returns the token as a response to the frontend/postman
        return LoginResponse.builder()
                .message("Welcome " + authUserDetail.getFirstName())
                .token(token)
                .build();

    }

    @PostMapping("/signup")
    public RegisterResponse registerUser(@RequestBody RegisterRequest request) {
        // Create a new user object
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(new BCryptPasswordEncoder().encode(request.getPassword())) // Encrypt the password before saving
                .dateJoined(LocalDateTime.now())
                .active(true)
                .type("UNVERIFIED_USER") // VISITOR_OR_BANNED, UNVERIFIED_USER, NORMAL_USER, ADMIN_USER, SUPER_ADMIN_USER
                .profileImageUrl("https://edusphere.blob.core.windows.net/avatars/default_avatar.png") // Default profile image, should be a key of s3 bucket
                .build();

        // Call the authService to add the new user, will encrypt the password and add the user to the database
        User addedUser = authService.addUser(user);

        if (addedUser != null) {
            return RegisterResponse.builder()
                    .message("User registered successfully")
                    .status("SUCCESS")
                    .build();
        } else {
            return RegisterResponse.builder()
                    .message("Registration failed: Username already exists")
                    .status("FAILURE")
                    .build();
        }
    }
}
