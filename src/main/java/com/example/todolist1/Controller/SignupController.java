package com.example.todolist1.Controller;

import com.example.todolist1.Model.Message;
import com.example.todolist1.Model.Signup;
import com.example.todolist1.Service.SignupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SignupController {
    private final SignupService signupService;
    Logger logger = LoggerFactory.getLogger(SignupController.class);
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public Message save(@RequestBody Signup signup){
        signup.setRoles(new ArrayList<>(List.of("User")));
        if(signup.getEmail() == null || signup.getUsername() == null || signup.getPassword() == null){
            logger.error("post/signup \"Some fields are null\"");
            return new Message(
                    "ERROR",
                    "Some fields are null",
                    null
            );
        }
        boolean userExist = !signupService.findByUsername(signup.getUsername()).isEmpty();
        if(userExist) {
            logger.error("post/signup \"Username already exists\"");
            return new Message(
                    "ERROR",
                    "Username already exists",
                    null
            );
        }
        if(!signupService.findByEmail(signup.getEmail()).isEmpty()){
            logger.error("post/signup \"Email already exists\"");
            return new Message(
                    "ERROR",
                    "Email already exists",
                    null
            );
        }
        signup.setPassword(passwordEncoder.encode(signup.getPassword()));
        signupService.save(signup);
        logger.info("post/signup \"User registered\"");
        return new Message(
                "SUCCESS",
                "User registered",
                null
        );

    }

    // @GetMapping("/signup")
    // public List<Signup> findAll(){
    //     logger.info("get/signup");
    //     return signupService.findAll();
    // }
}
