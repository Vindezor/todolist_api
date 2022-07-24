package com.example.todolist1.Repository;
import com.example.todolist1.Model.UserLista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;

@Repository
public interface ListaRepository extends MongoRepository<UserLista, Serializable> {
/*
    @RestResource(path = "findByData", rel = "findByData")
    public List<Lista> findByData(@Param("data")String data, Pageable pageable);

    @RestResource(path = "getAllData", rel = "getAllData")
    public List<Lista> getAllData();*/
}
