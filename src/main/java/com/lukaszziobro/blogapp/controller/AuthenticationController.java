package com.lukaszziobro.blogapp.controller;

import com.lukaszziobro.blogapp.entity.User;
import com.lukaszziobro.blogapp.entity.VerificationToken;
import com.lukaszziobro.blogapp.event.RegistrationCompleteEvent;
import com.lukaszziobro.blogapp.event.listener.RegistrationCompleteEventListener;
import com.lukaszziobro.blogapp.payload.JWTAuthResponse;
import com.lukaszziobro.blogapp.payload.LoginDto;
import com.lukaszziobro.blogapp.payload.RegisterDto;
import com.lukaszziobro.blogapp.repository.VerificationTokenRepository;
import com.lukaszziobro.blogapp.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@Tag(name = "Authentication")
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final HttpServletRequest httpServletRequest;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RegistrationCompleteEventListener registrationCompleteEventListener;


    @SecurityRequirement(name = "Bear Authentication")
    @PostMapping(value = {"/login", "/sign-in"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PostMapping(value = {"/register", "/sign-up"})
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto, final HttpServletRequest request){
        User user = authService.registerUser(registerDto);
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user, this.applicationUrl(request)));
        String message = "User with username " + registerDto.getUsername() + " successfully registered!";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/register/verifyEmail")
    public ResponseEntity<String> sendVerificationToken(@RequestParam("token") String token){
        String url = applicationUrl(httpServletRequest) + "/api/auth/register/resend-verification-token?token=" + token;
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnabled()){
            return new ResponseEntity<>(
                    "This account has already been verified, please log in!", HttpStatus.BAD_REQUEST
            );
        }
        String verificationResult = authService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            verificationTokenRepository.delete(verificationToken);
            return new ResponseEntity<>(
                    "Email verified successfully. Now you can login to your account",
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                "Invalid verification link, <a href=\"" + url + "\"> Get a new verification link. </a>",
                HttpStatus.BAD_REQUEST
        );
    }

    @GetMapping("/register/resend-verification-token")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException, InterruptedException {
        VerificationToken verificationToken =  authService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendVerificationTokenEmail(theUser, applicationUrl(request), verificationToken);
        return "A new verification link hs been sent to your email," +
                " please, check to complete your registration";
    }

    private void resendVerificationTokenEmail(User theUser, String applicationUrl, VerificationToken token)
            throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl + "/api/auth/register/verifyEmail?token="+token.getToken();
        registrationCompleteEventListener.sendVerificationEmail(url);
    }


    public String applicationUrl(HttpServletRequest request){
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
