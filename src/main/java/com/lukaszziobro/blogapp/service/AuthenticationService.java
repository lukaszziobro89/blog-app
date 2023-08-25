package com.lukaszziobro.blogapp.service;

import com.lukaszziobro.blogapp.entity.Role;
import com.lukaszziobro.blogapp.entity.User;
import com.lukaszziobro.blogapp.entity.VerificationToken;
import com.lukaszziobro.blogapp.exception.EmailAlreadyExistsException;
import com.lukaszziobro.blogapp.exception.UserAlreadyExistsException;
import com.lukaszziobro.blogapp.payload.LoginDto;
import com.lukaszziobro.blogapp.payload.RegisterDto;
import com.lukaszziobro.blogapp.repository.RoleRepository;
import com.lukaszziobro.blogapp.repository.UserRepository;
import com.lukaszziobro.blogapp.repository.VerificationTokenRepository;
import com.lukaszziobro.blogapp.security.JwtTokenProvider;
import com.lukaszziobro.blogapp.utils.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private JwtTokenProvider jwtTokenProvider;
    private VerificationTokenRepository verificationTokenRepository;

    public String login(LoginDto loginDto){
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    public User registerUser(RegisterDto registerDto){

        if(userRepository.existsByUsername(registerDto.getUsername())){
            String message = "Username " + registerDto.getUsername() + " already exists";
            throw new UserAlreadyExistsException(message);
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            String message = "Email " + registerDto.getEmail() + " already exists";
            throw new EmailAlreadyExistsException(message);
        }

        User user =  userMapper.mapRegisterDtoToUser(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public void saveUserVerificationToken(User user, String token) {
        var verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    public String validateToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null){
            return "Invalid verification token";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if(verificationToken.getExpirationTime().before(calendar.getTime())){
            return "Verification link already expired," +
                    " Please, click the link below to receive a new verification link";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    public VerificationToken generateNewVerificationToken(String oldToken) throws InterruptedException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        VerificationToken newToken = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(newToken.getTokenExpirationTime());
        return verificationTokenRepository.save(verificationToken);
    }
}
