package com.example.todolist1.Service;

import com.example.todolist1.Model.Signup;
import com.example.todolist1.Repository.SignupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignupService {
    private final SignupRepository signupRepository;

    public SignupService(SignupRepository signupRepository) {
        this.signupRepository = signupRepository;
    }

    public void save(Signup signup){
        signupRepository.save(signup);
    }

    public List<Signup> findAll(){
        return signupRepository.findAll();
    }

    public List<Signup> findByUsername(String username){
        return signupRepository.findByUsername(username);
    }

    public List<Signup> findByEmail(String email){
        return signupRepository.findByEmail(email);
    }
}
