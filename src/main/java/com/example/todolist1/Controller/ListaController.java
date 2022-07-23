package com.example.todolist1.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.todolist1.Model.Lista;
import com.example.todolist1.Model.Message;
import com.example.todolist1.Model.UserLista;
import com.example.todolist1.Service.ListaService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ListaController {

    private final ListaService listaService;
    Logger logger = LoggerFactory.getLogger(ListaController.class);
    public ListaController(ListaService listaService) {
        this.listaService = listaService;
    }

    @PostMapping("/lista")
    public Message save(HttpServletRequest request, @RequestBody UserLista lista) {
        String token = request.getHeader("Authorization");
        if(token != null) {
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT;
            try {
                decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                String id = decodedJWT.getId();
                if(id == null){
                    logger.error("post/lista \"Token inválido\"");
                    return new Message(
                            "ERROR",
                            "Token inválido",
                            null
                    );
                }
                lista.setId(id);
                listaService.save(lista);
                logger.info("post/lista \"List Saved\"");
                return new Message(
                        "SUCCESS",
                        "List Saved",
                        null

                );
            } catch (TokenExpiredException e) {
                logger.error("post/lista \"Token expired\"");
                return new Message(
                        "ERROR",
                        "Token expired",
                        null
                );
            }
        }
        else{
            logger.error("post/lista \"Not authorized\"");
            return new Message(
                    "ERROR",
                    "Not authorized",
                    null
            );
        }
    }

    @GetMapping("/lista")
    public Message findById(HttpServletRequest request){
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        String token = request.getHeader("Authorization");
        if(token != null) {
            DecodedJWT decodedJWT;
            try {
                decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                String id = decodedJWT.getId();
                if(id == null){
                    logger.error("get/lista \"Token inválido\"");
                    return new Message(
                            "ERROR",
                            "Token inválido",
                            null
                    );
                }
                Optional<UserLista> lista = listaService.findById(id);
                if(lista.isEmpty()){
                    logger.error("get/lista \"List not found\"");
                    return new Message(
                            "ERROR",
                            "List not found",
                            null
                    );
                }
                logger.info("get/lista \"List Found\"");
                return new Message(
                        "SUCCESS",
                        "List Found",
                        lista
                );

            } catch (TokenExpiredException e) {
                logger.error("get/lista \"Token expired\"");
                return new Message(
                        "ERROR",
                        "Token expired",
                        null
                );
            }
        }
        else{
            logger.error("get/lista \"Not authorized\"");
            return new Message(
                    "ERROR",
                    "Not authorized",
                    null
            );
        }
    }

    /*@DeleteMapping("/lista/{id}")
    public void deleteById(@PathVariable String id){
        listaService.deleteById(id);
    }*/

    @PutMapping("/lista")
    public Message update(HttpServletRequest request, @RequestBody UserLista lista){
        String token = request.getHeader("Authorization");
        if(token != null) {
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT;
            try {
                decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                String id = decodedJWT.getId();
                if(id == null){
                    logger.error("put/lista \"Token inválido\"");
                    return new Message(
                            "ERROR",
                            "Token inválido",
                            null
                    );
                }
                lista.setId(id);
                listaService.save(lista);
                logger.info("put/lista \"List Saved\"");
                return new Message(
                        "SUCCESS",
                        "List Saved",
                        null

                );
            } catch (TokenExpiredException e) {
                logger.error("put/lista \"Token expired\"");
                return new Message(
                        "ERROR",
                        "Token expired",
                        null
                );
            }
        }
        else{
            logger.error("put/lista \"Not authorized\"");
            return new Message(
                    "ERROR",
                    "Not authorized",
                    null
            );
        }
    }
}
