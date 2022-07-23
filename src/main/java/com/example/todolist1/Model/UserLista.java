package com.example.todolist1.Model;

import com.mongodb.lang.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

public class UserLista {
    @Id
    @NonNull
    private String id;
    private List<Lista> lista;

    public UserLista() {
        id = new ObjectId().toString();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public List<Lista> getLista() {
        return lista;
    }

    public void setLista(List<Lista> lista) {
        this.lista = lista;
    }
}
