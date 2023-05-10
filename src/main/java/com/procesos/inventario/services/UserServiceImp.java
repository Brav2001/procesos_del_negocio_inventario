package com.procesos.inventario.services;

import com.procesos.inventario.models.User;
import com.procesos.inventario.repository.UserRepository;
import com.procesos.inventario.utils.JWTUtil;
import org.jetbrains.annotations.NotNull;
import com.procesos.inventario.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

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
    @Override
    public String login( User user) {
        Optional <User> userBD = userRepository.findByEmail(user.getEmail());
        if (userBD.isEmpty()) {
            throw new RuntimeException(Constants.USER_NOT_FOUND);
        }
        if (!userBD.get().getPassword().equals(user.getPassword())) {
            throw new RuntimeException(Constants.PASSWORD_INCORRECT);
        }
        return jwtUtil.create(String.valueOf(userBD.get().getId()), String.valueOf(user.getEmail()));
    }
}
