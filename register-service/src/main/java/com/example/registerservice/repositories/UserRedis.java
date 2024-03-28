package com.example.registerservice.repositories;

import com.example.registerservice.models.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRedis {
    private HashOperations hashOperations;
    private RedisTemplate redisTemplate;

    public UserRedis(RedisTemplate redisTemplate) {
        super();
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }


    public void save(User user) {
        hashOperations.put("User", user.getUserId(), user);
    }

    public User findById(long id) {
        return (User) hashOperations.get("User", id);
    }

    public List<User> findAll() {
        return hashOperations.values("User");
    }

    public void update(User user) {
        save(user);
    }

    public void delete(int id) {
        hashOperations.delete("User", id);
    }
}
