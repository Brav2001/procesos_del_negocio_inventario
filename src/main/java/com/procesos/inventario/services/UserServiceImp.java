package com.procesos.inventario.services;

import com.procesos.inventario.models.User;
import com.procesos.inventario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    public User getUser(Long id){

        return userRepository.findById(id).get();
    }

    @Override
    public Boolean createUser(User user) {
        try{
            userRepository.save(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public Boolean updateUser(Long id, User user) {
        try{
            User userBD = userRepository.findById(id).get();
            userBD.setFirstName(user.getFirstName());
            userBD.setLastName(user.getLastName());
            userBD.setAddress(user.getAddress());
            userBD.setBirthday(user.getBirthday());
            userRepository.save(userBD);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
