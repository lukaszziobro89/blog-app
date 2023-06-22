package com.lukaszziobro.blogapp.service;

import com.lukaszziobro.blogapp.entity.Role;
import com.lukaszziobro.blogapp.entity.User;
import com.lukaszziobro.blogapp.exception.BlogAPIException;
import com.lukaszziobro.blogapp.payload.LoginDto;
import com.lukaszziobro.blogapp.payload.RegisterDto;
import com.lukaszziobro.blogapp.repository.RoleRepository;
import com.lukaszziobro.blogapp.repository.UserRepository;
import com.lukaszziobro.blogapp.security.JwtTokenProvider;
import com.lukaszziobro.blogapp.utils.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private JwtTokenProvider jwtTokenProvider;

    public String login(LoginDto loginDto){
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    public String register(RegisterDto registerDto){

        if(userRepository.existsByUsername(registerDto.getUsername())){
            String message = "Username " + registerDto.getUsername() + " already exists";
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, message);
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            String message = "Email " + registerDto.getEmail() + " already exists";
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, message);
        }

        User user =  userMapper.mapRegisterDtoToUser(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User with username " + registerDto.getUsername() + " successfully registered!";

    }

}
