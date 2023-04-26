package com.procesos.inventario.controllers;


import com.procesos.inventario.models.User;
import com.procesos.inventario.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity findUserById(@PathVariable Long id){
        Map response = new HashMap();
        try{
            return new ResponseEntity(userServiceImp.getUser(id), HttpStatus.OK) ;
        }catch(Exception e) {
            response.put("status", "404");
            response.put("message", "No se encontro el usuario");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/user" )
    public ResponseEntity savedUser(@RequestBody User user){
        Boolean res = userServiceImp.createUser(user);
        Map response = new HashMap();
        if(res==true){
            return new ResponseEntity(userServiceImp.createUser(user), HttpStatus.CREATED) ;
        }else{
            response.put("status", "400");
            response.put("message", "No se guardo el usuario");
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/users" )
    public ResponseEntity Users(){
        Map response = new HashMap();
        try{
            return new ResponseEntity(userServiceImp.allUsers(), HttpStatus.OK) ;
        }catch(Exception e) {
            response.put("status", "404");
            response.put("message", "No hay usuarios");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/updateUser/{id}")
    public ResponseEntity updateUser(@PathVariable Long id,@RequestBody User user){
        Boolean res = userServiceImp.updateUser(id,user);
        Map response = new HashMap();
        if(res==true){
            return new ResponseEntity(userServiceImp.updateUser(id,user), HttpStatus.OK) ;
        }else{
            response.put("status", "400");
            response.put("message", "No se actualizo el usuario");
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user){
        Map response = new HashMap();
        try{
            return new ResponseEntity(userServiceImp.login(user), HttpStatus.OK);
        }catch (Exception e){
            response.put("status", "404");
            response.put("message", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
