package com.example.todolist1.Service;

import com.example.todolist1.Model.Lista;
import com.example.todolist1.Model.UserLista;
import com.example.todolist1.Repository.ListaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaService {
    private final ListaRepository listaRepository;

    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    public void save(UserLista lista){
        listaRepository.save(lista);
    }

    public List<UserLista> findAll(){
        return listaRepository.findAll();
    }

    public Optional<UserLista> findById(String id){
        return listaRepository.findById(id);
    }

    public void deleteById(String id){
        listaRepository.deleteById(id);
    }

}
