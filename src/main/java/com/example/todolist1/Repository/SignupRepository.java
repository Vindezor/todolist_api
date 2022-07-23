package com.example.todolist1.Repository;

import com.example.todolist1.Model.Signup;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface SignupRepository extends MongoRepository<Signup, Serializable> {
    List<Signup> findByEmail(String email);

    List<Signup> findByUsername(String username);
}
