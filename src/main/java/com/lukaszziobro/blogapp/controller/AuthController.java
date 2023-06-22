package com.lukaszziobro.blogapp.controller;

import com.lukaszziobro.blogapp.payload.JWTAuthResponse;
import com.lukaszziobro.blogapp.payload.LoginDto;
import com.lukaszziobro.blogapp.payload.RegisterDto;
import com.lukaszziobro.blogapp.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping(value = {"/login", "/sign-in"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, OK);
    }

    @PostMapping(value = {"/register", "/sign-up"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
