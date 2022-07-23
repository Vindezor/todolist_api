package com.example.todolist1.Repository;

import com.example.todolist1.Model.Lista;
import com.example.todolist1.Model.UserLista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.util.List;

@Repository
public interface ListaRepository extends MongoRepository<UserLista, Serializable> {
/*
    @RestResource(path = "findByData", rel = "findByData")
    public List<Lista> findByData(@Param("data")String data, Pageable pageable);

    @RestResource(path = "getAllData", rel = "getAllData")
    public List<Lista> getAllData();*/
}
