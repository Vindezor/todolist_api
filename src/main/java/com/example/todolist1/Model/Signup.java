package com.example.todolist1.Model;

import com.mongodb.lang.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "signup")
public class Signup implements Serializable{
    @Id
    @NonNull
    private String id;
    private String username;
    private String email;
    private String password;
    @NonNull
    private List<String> roles;

    public Signup() {
        id = new ObjectId().toString();
        roles = new ArrayList<>(List.of("User"));
    }

    @NonNull
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(@NonNull List<String> roles) {
        this.roles = roles;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
