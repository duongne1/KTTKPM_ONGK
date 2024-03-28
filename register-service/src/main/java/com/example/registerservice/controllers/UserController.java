package com.example.registerservice.controllers;

import com.example.registerservice.models.User;
import com.example.registerservice.repositories.UserRepository;
import com.example.registerservice.repositories.UserRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("/v2")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserRedis userRedis;

    @GetMapping("/register/list")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @PostMapping("/register/save")
    public User saveUser(@RequestBody User user) {
        userRedis.save(user);
        userRepository.save(user);
        return user;
    }

    @GetMapping("/register/{id}")
    public User  getUserById(@PathVariable("id") Long id) {
        User userRds = userRedis.findById(id);
        if(userRds == null){
            User user = userRepository.findById(id).orElse(null);
            userRedis.save(user);
            return user;
        }
        return userRds;
    }

    @GetMapping("/register/checkUser")
    public User checkUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userRepository.findByUserNameAndPassword(username, password);
        return user;
    }
}
