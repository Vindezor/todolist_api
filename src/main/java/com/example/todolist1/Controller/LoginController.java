package com.example.todolist1.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.todolist1.Model.Login;
import com.example.todolist1.Model.LoginResponse;
import com.example.todolist1.Model.Message;
import com.example.todolist1.Model.Signup;
import com.example.todolist1.Service.SignupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
    private final SignupService signupService;
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    public LoginController(SignupService signupService) {
        this.signupService = signupService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/refreshToken")
    public Message refreshToken(HttpServletRequest request){
        String refreshToken = request.getHeader("Authorization");
        if(refreshToken != null){
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT;
            try {
                decodedJWT = verifier.verify(refreshToken);
            } catch (TokenExpiredException e) {
                logger.error("post/refreshToken \"Token expired\"");
                return new Message(
                    "ERROR",
                    "Token expired",
                    null
                );
            } catch (SignatureVerificationException e) {
                logger.error("post/refreshToken \"Invalid token\"");
                return new Message(
                        "ERROR",
                        "Invalid token",
                        null
                );
            }
            if(decodedJWT.getIssuer() == null){
                logger.error("post/refreshToken \"Not refresh token\"");
                return new Message(
                        "ERROR",
                        "Not refresh token",
                        null
                );
            }
            List<Signup> accounts = signupService.findByUsername(decodedJWT.getSubject());
            if(accounts.isEmpty()){
                logger.error("post/refreshToken \"Account doesn't exists\"");
                return new Message(
                        "ERROR",
                        "Account doesn't exists",
                        null
                );
            }
            Signup account = accounts.get(0);
            String newToken = JWT.create()
                    .withSubject(account.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                    .withJWTId(account.getId())
                    .withClaim("role", account.getRoles())
                    .sign(algorithm);
            String newRefreshToken = JWT.create()
                    .withSubject(account.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                    .withIssuer("refreshToken")
                    .sign(algorithm);
            logger.info("post/refreshToken \"Logged in successfully\"");
            return new Message(
                    "SUCCESS",
                    "Logged in successfully",
                    new LoginResponse(newToken, newRefreshToken, account.getId())
            );
        }
        logger.error("post/refreshToken \"Not authenticated\"");
        return new Message(
                "ERROR",
                "Not authenticated",
                null
        );
    }

    @PostMapping("/login")
    public Message login(@RequestBody Login login){
        List<Signup> accounts = signupService.findByUsername(login.getUsername());
        if(accounts.isEmpty()){
            logger.error("post/login \"Account doesn't exists\"");
            return new Message(
                    "ERROR",
                    "Account doesn't exists",
                    null
            );
        }
        else{
            Signup account = accounts.get(0);
            if(passwordEncoder.matches(login.getPassword(), account.getPassword())){
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String token = JWT.create()
                        .withSubject(login.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                        .withJWTId(account.getId())
                        .withClaim("roles", account.getRoles())
                        .sign(algorithm);
                String refreshToken = JWT.create()
                        .withSubject(login.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                        .withIssuer("refreshToken")
                        .sign(algorithm);
                logger.info("post/login \"Logged in successfully\"");
                return new Message(
                        "SUCCESS",
                        "Logged in successfully",
                        new LoginResponse(token, refreshToken, account.getId())
                );
            }
            else{
                logger.error("post/login \"Incorrect password\"");
                return new Message(
                        "ERROR",
                        "Incorrect password",
                        null
                );
            }
        }
    }
}
